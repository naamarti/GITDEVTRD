package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.orderdetails.OrderDetailsDisplayAction;

/**
 * @author Val Catrini
 */
public class OrderDetailsController extends AbstractControllerNew 
{
	public OrderDetailsController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		new OrderDetailsDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
