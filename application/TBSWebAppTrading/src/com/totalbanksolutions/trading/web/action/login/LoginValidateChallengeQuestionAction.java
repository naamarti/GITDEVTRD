package com.totalbanksolutions.trading.web.action.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginData;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class LoginValidateChallengeQuestionAction implements Action
{
	protected final Log log = LogFactory.getLog( LoginValidateChallengeQuestionAction.class );
	private DataService ds;
    
    public LoginValidateChallengeQuestionAction(DataService ds)
    {
    	this.ds = ds;
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Validate Challenge Question...");
		boolean status = true;
		
		LoginModelHelper h = new LoginModelHelper(modelAndView);

		LoginData loginData = h.getLoginData();
		
		loginData.addRow( request.getParameterMap() );
		log.debug( loginData.toString() );

		long questionId = loginData.getRow().getColumn( loginData.CHALLENGE_QUESTION_ID ).getValueAsLong();
		String questionResponse = loginData.getRow().getColumn( loginData.CHALLENGE_QUESTION ).getValueAsString();
		if( this.ds.trading.validateChallengeReponse( h.getUser().getUserId(), questionId, questionResponse) )
		{
			//loginStep = "welcome";
		}
		else
		{
			h.setMessageError( "Invalid security question answer. Sign on failed." );
			loginData.getRow().getColumn( loginData.PASSWORD ).setValue("");
			status = false;
		}
			
		return status;
	}

}
