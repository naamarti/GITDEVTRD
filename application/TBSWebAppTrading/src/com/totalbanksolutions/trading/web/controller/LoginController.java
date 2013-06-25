package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;
import com.totalbanksolutions.trading.web.action.accounthome.AccountHomeDisplayAction;
import com.totalbanksolutions.trading.web.action.challengequestion.ChallengeQuestionDisplayAction;
import com.totalbanksolutions.trading.web.action.challengequestion.ChallengeQuestionSaveAction;
import com.totalbanksolutions.trading.web.action.changepassword.ChangePasswordDisplayAction;
import com.totalbanksolutions.trading.web.action.changepassword.ChangePasswordSaveAction;
import com.totalbanksolutions.trading.web.action.login.LoginCreateTempSessionAction;
import com.totalbanksolutions.trading.web.action.login.LoginDetermineNextStepAction;
import com.totalbanksolutions.trading.web.action.login.LoginDisplayChallengeQuestionAction;
import com.totalbanksolutions.trading.web.action.login.LoginDisplayUserAndPasswordAction;
import com.totalbanksolutions.trading.web.action.login.LoginValidateChallengeQuestionAction;
import com.totalbanksolutions.trading.web.action.login.LoginValidateTempSessionAction;
import com.totalbanksolutions.trading.web.action.login.LoginValidateUserAndPasswordAction;
import com.totalbanksolutions.trading.web.action.siteagreement.SiteAgreementContinueAction;
import com.totalbanksolutions.trading.web.action.siteagreement.SiteAgreementDisplayAction;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

/**
 * @author Val Catrini
 */
public class LoginController extends AbstractControllerNew
{
	protected final Log log = LogFactory.getLog( LoginController.class );
	
	public LoginController()
	{
		super(false, false); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		LoginModelHelper h = new LoginModelHelper(modelAndView);
		
		h.setViewName("login");
		h.setScreenName("TBS Units Trading Login");
		boolean status;
		
		h.setLoginStep( WebUtils.getStringParameterValue(request, "loginStep", "") );
		log.debug("loginStep=" + h.getLoginStep());
		
		/******************************************************************
		 * Process incoming request:
		 ******************************************************************/
		if( h.getLoginStep().equalsIgnoreCase("login") )
		{
			status = new LoginValidateUserAndPasswordAction( this.ds ).processAction( request, modelAndView );
			if( status == true )
			{
				status = new LoginCreateTempSessionAction( this.ds ).processAction( request, modelAndView );
				status = new LoginDetermineNextStepAction( this.ds ).processAction( request, modelAndView );
			}
		}
		else if ( h.getLoginStep().equalsIgnoreCase("saveSiteAgreement") )
		{
			log.debug("### in saveSiteAgreement top processing ###");

			status = new LoginValidateTempSessionAction( this.ds ).processAction( request, modelAndView );
			if( status == true )
			{
				status = new SiteAgreementContinueAction( this.ds ).processAction( request, modelAndView );
				if( status == true )
				{
					h.setLoginStep("welcome");
//					status = new LoginDetermineNextStepAction( this.ds ).processAction( request, modelAndView );
				}
			}
			else
			{
				h.setLoginStep("timeout");
			}
		}
		else if ( h.getLoginStep().equalsIgnoreCase("changePassword") )
		{
			status = new LoginValidateTempSessionAction( this.ds ).processAction( request, modelAndView );
			if( status == true )
			{
				status = new ChangePasswordSaveAction( this.ds ).processAction( request, modelAndView );
				if( status == true )
				{
					status = new LoginDetermineNextStepAction( this.ds ).processAction( request, modelAndView );
				}
//				else
//				{
//					loginModelHelper.setLoginStep("changePassword");
//				}
			}
			else
			{
				h.setLoginStep("timeout");
			}
		}
		else if ( h.getLoginStep().equalsIgnoreCase("challengeQuestionSetup") )
		{
			status = new LoginValidateTempSessionAction( this.ds ).processAction( request, modelAndView );
			if( status == true )
			{
				status = new ChallengeQuestionSaveAction( this.ds ).processAction( request, modelAndView );
				if( status == true )
				{
					status = new LoginDetermineNextStepAction( this.ds ).processAction( request, modelAndView );
				}
//				else
//				{
//					loginModelHelper.setLoginStep("challengeQuestionSetup");
//				}
			}
			else
			{
				h.setLoginStep("timeout");
			}
		}
		else if ( h.getLoginStep().equalsIgnoreCase("challengeQuestion") )
		{
			status = new LoginValidateTempSessionAction( this.ds ).processAction( request, modelAndView );
			if( status == true )
			{
				status = new LoginValidateChallengeQuestionAction( this.ds ).processAction( request, modelAndView );
				if ( status == true )
				{
					if( ds.trading.isDisclaimerApproved( h.getUser().getUserId() ))
						h.setLoginStep("welcome");
					else
						h.setLoginStep("showSiteAgreement");
				}
			}
			else
			{
				h.setLoginStep("timeout");
			}
		}
		else
		{
			h.setLoginStep("login");
		}
		
		
		/******************************************************************
		 * Process based on Next Step (derived from actions above) :
		 ******************************************************************/
		if ( h.getLoginStep().equalsIgnoreCase("login") )
		{
			status = new LoginDisplayUserAndPasswordAction( this.ds ).processAction( request, modelAndView );
		}
		else if ( h.getLoginStep().equalsIgnoreCase("changePassword") )
		{
			status = new ChangePasswordDisplayAction( this.ds ).processAction( request, modelAndView );
		}
		else if ( h.getLoginStep().equalsIgnoreCase("challengeQuestionSetup") )
		{
			status = new ChallengeQuestionDisplayAction( this.ds ).processAction( request, modelAndView );
		}
		else if ( h.getLoginStep().equalsIgnoreCase("challengeQuestion") )
		{
			status = new LoginDisplayChallengeQuestionAction( this.ds ).processAction( request, modelAndView );
		}
		else if ( h.getLoginStep().equalsIgnoreCase("showSiteAgreement") )
		{
			new SiteAgreementDisplayAction( this.ds ).processAction( request, modelAndView );
		}
		else if ( h.getLoginStep().equalsIgnoreCase("welcome") )
		{
			//TO DO... set user session ONLINE, IsDeleted = 0
			log.debug("activating AppSession...");
			this.ds.trading.activateAppSession( h.getUser().getUserId(), h.getUser().getSessionKey() );

			new AccountHomeDisplayAction( this.ds ).processAction( request, modelAndView );
			this.setMenuList(true);
		}
		else if ( h.getLoginStep().equalsIgnoreCase("timeout") )
		{
			h.setViewName("sessionTimeout");
		}
		
	}
	
}
