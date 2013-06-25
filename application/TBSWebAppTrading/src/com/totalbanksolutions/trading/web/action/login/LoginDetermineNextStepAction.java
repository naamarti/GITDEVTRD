package com.totalbanksolutions.trading.web.action.login;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class LoginDetermineNextStepAction implements Action
{
	protected final Log log = LogFactory.getLog( LoginDetermineNextStepAction.class );
	private DataService ds;
//    private boolean isMandatoryContactInfoEnabled = false;
    
    public LoginDetermineNextStepAction(DataService ds)
    {
    	this.ds = ds;
//		this.isMandatoryContactInfoEnabled = this.ds.security.isAppConfigMandatoryContactInfoEnabled();
//		log.debug( "isMandatoryContactInfoEnabled = " + isMandatoryContactInfoEnabled ); 
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Determine next login step...");
		LoginModelHelper h = new LoginModelHelper(modelAndView);
		
		String loginStep = "";

		Date lastPasswordChangeDate = h.getUser().getPasswordChangeDateTime();
		Date dateToChange = DateUtils.addDays( lastPasswordChangeDate, this.ds.trading.getAppConfigPasswordValidPeriodInDays() );
		log.debug( "lastPasswordChangeDate=" + lastPasswordChangeDate + "; dateToChange=" + dateToChange );
		if( dateToChange.before( new Date() ) )
		{
			h.setMessageWarning( "Password has expired. Please change password now." );
			loginStep = "changePassword";
		}
		else 
		{
			if( h.getUser().isPasswordReset() )
			{
				h.setMessageWarning( "Password has expired. Please change password now." );
				loginStep = "changePassword";
			}
			else if( !h.getUser().isSecretQuestionsSet() )
			{
				h.setMessageInfo( "Please enter your login security questions." );
				loginStep = "challengeQuestionSetup";
			}
			else
			{
				loginStep = "challengeQuestion";
			}
		}
			
		h.setLoginStep(loginStep);
		return true;
	}

}
