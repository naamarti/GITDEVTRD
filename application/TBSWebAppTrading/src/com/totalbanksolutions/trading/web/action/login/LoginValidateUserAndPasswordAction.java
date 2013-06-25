package com.totalbanksolutions.trading.web.action.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginData;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class LoginValidateUserAndPasswordAction implements Action
{
	protected final Log log = LogFactory.getLog( LoginValidateUserAndPasswordAction.class );
	private DataService ds;
	private int numLoginAttemptsBeforeLockout = 0;
    
    public LoginValidateUserAndPasswordAction(DataService ds)
    {
    	this.ds = ds;
    	this.numLoginAttemptsBeforeLockout = this.ds.trading.getAppConfigLoginAttemptsBeforeLockout();
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Validate User and Password...");
		LoginModelHelper h = new LoginModelHelper(modelAndView);

		LoginData loginData = h.getLoginData();
		WebForm loginForm = h.getLoginForm();
		
		loginData.addRow( request.getParameterMap() );
		log.debug( loginData.toString() );
		
		h.getUser().setUserName( loginData.getRow().getColumn(loginData.USER_NAME).getValueAsString() );
		h.getUser().setUserPassword( loginData.getRow().getColumn(loginData.PASSWORD).getValueAsString() );
		ViewAppUserSession appUser = this.ds.trading.validateLogin( h.getUser().getUserName(), h.getUser().getUserPassword() );
		if( appUser == null )
		{
			loginForm.setStateInvalid( loginData.USER_NAME );
			h.setMessageError( "Invalid User Name. Sign on failed." );
			return false;
		}
		else
		{
			h.setUser(appUser);
			// First check, is the user locked?
			if( h.getUser().isLocked() )
			{
				h.setMessageError( "For security purposes, this account has been locked. Please contact your administrator to unlock your account." );
				return false;
			}
			else
			{
				if( h.getUser().isPasswordMatch() )
				{
					//successful login
					loginData.getRow().getColumn( loginData.PASSWORD ).setValue("");
	    		}
	    		else // Invalid Password
	    		{
		    		int numInvalidAttempts = h.getUser().getLoginFailedAttempts();
		    		if( numInvalidAttempts >= this.numLoginAttemptsBeforeLockout )
		    		{
		    			h.setMessageError( "Invalid Password. You have exceeded the allowable invalid login attempts.<br>For security purposes, this user is now LOCKED. Please contact your administrator." );
						return false;
		    		}
		    		else
		    		{
//		    			loginForm.setStateValid( loginData.USER_NAME );
		    			loginForm.setStateInvalid( loginData.PASSWORD );
		    			h.setMessageError( "Invalid Password. Sign on failed.<br>NOTE: User is locked out after " + this.numLoginAttemptsBeforeLockout + " consecutive failed login attempts." );
						return false;
		    		}
	    		} // passwords match
			} // account locked
		} // validate username
		return true;
	}

}
