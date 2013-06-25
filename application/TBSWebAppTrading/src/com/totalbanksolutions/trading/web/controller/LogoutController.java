package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;
import com.totalbanksolutions.trading.web.util.ControllerUtility;

/**
 * @author Val Catrini
 */
public class LogoutController extends AbstractControllerNew
{
	protected final Log log = LogFactory.getLog( LogoutController.class );
	
	public LogoutController()
	{
		super(false, false); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		LoginModelHelper h = new LoginModelHelper(modelAndView);
		
		log.debug("Logout...");
		
		h.setViewName("logout");
		h.setScreenName("TBS Units Trading Logout");
		h.setLoginStep("logout");
		
		this.ds.security.deactivateAppSession( h.getUser().getUserId(), h.getUser().getSessionKey() );
		this.ds.security.clearUserCache( h.getUser().getUserId() );
		ControllerUtility.removeUserSession(request);
		h.getUser().setUserId(0);
		h.getUser().setSessionKey("");
	}

}
