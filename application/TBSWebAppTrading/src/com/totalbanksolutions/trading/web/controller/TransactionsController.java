package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.transactions.TransactionsDisplayAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Marek Komza
 */
public class TransactionsController extends AbstractControllerNew 
{
	protected final Log log = LogFactory.getLog( TransactionsController.class );
	
	public TransactionsController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
    	ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("transactions");
		h.setScreenName("Transactions");

		log.debug("### TransactionsController sessionUserAction = " + h.getSessionUserAction() + " ###");
		
		new TransactionsDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
