package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.accounthome.AccountHomeCancelOrderAction;
import com.totalbanksolutions.trading.web.action.accounthome.AccountHomeDisplayAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Val Catrini
 */
public class AccountHomeController extends AbstractControllerNew 
{
	protected final Log log = LogFactory.getLog( AccountHomeController.class );
	
	public AccountHomeController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
    	ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("accountHome");
		h.setScreenName("Account Home");

		log.debug("### AccountHomeController sessionUserAction = " + h.getSessionUserAction() + " ###");
		
		if ( h.getSessionUserAction().equalsIgnoreCase("cancelOrder") )
		{
			new AccountHomeCancelOrderAction( this.ds ).processAction( request, modelAndView );
		}
		
		new AccountHomeDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
