package com.totalbanksolutions.trading.web.action.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class LoginCreateTempSessionAction implements Action
{
	protected final Log log = LogFactory.getLog( LoginCreateTempSessionAction.class );
	private DataService ds;
    
    public LoginCreateTempSessionAction(DataService ds)
    {
    	this.ds = ds;
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Create Temp Session...");
		LoginModelHelper h = new LoginModelHelper(modelAndView);

		this.ds.security.clearUserCache( h.getUser().getUserId() );
		String sessionKey = this.ds.trading.addAppSession( h.getUser().getUserId() );
		// OFFLINE user session should now be added with IsDeleted = 1
		h.getUser().setSessionKey(sessionKey);

		return true;
	}

}
