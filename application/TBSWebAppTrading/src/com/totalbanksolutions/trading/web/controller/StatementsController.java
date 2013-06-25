package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.statements.StatementsDisplayAction;

/**
 * @author Val Catrini
 */
public class StatementsController extends AbstractControllerNew 
{
	public StatementsController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		new StatementsDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
