package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.market.MarketDisplayAction;

/**
 * @author Val Catrini
 */
public class MarketController extends AbstractControllerNew 
{
	public MarketController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		new MarketDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
