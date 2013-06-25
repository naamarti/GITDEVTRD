package com.totalbanksolutions.trading.web.action.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class LoginValidateTempSessionAction implements Action
{
	protected final Log log = LogFactory.getLog( LoginValidateTempSessionAction.class );
	private DataService ds;
    
    public LoginValidateTempSessionAction(DataService ds)
    {
    	this.ds = ds;
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Validate Temp Pre-Login Session...");
		LoginModelHelper h = new LoginModelHelper(modelAndView);
		
		boolean status = true;
		ViewAppUserSession appUser = this.ds.trading.validateAppUserSessionPreLogin( h.getUser() );
		if( appUser == null )
		{
			h.setMessageError( "Your session has expired. Please login again." );
			status = false;
		}
		else
		{
			h.setUser(appUser);
		}
		return status;
	}

}
