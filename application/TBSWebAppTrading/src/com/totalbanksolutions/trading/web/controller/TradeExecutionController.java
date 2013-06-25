package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.tradeexecution.TradeExecutionDisplayAction;
import com.totalbanksolutions.trading.web.action.tradeexecution.TradeExecutionRunAction;
import com.totalbanksolutions.trading.web.action.tradeexecution.TradeExecutionUpdateStatusAction;
import com.totalbanksolutions.trading.web.model.TradeExecutionModelHelper;

/**
 * @author Val Catrini
 */
public class TradeExecutionController extends AbstractControllerNew 
{
	public TradeExecutionController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		TradeExecutionModelHelper h = new TradeExecutionModelHelper(modelAndView);
		boolean status = true;

		if ( h.getSessionUserAction().equalsIgnoreCase("ExecuteTradeMatch") )
		{
			status = new TradeExecutionRunAction( this.ds ).processAction( request, modelAndView );
		}
		else if ( h.getSessionUserAction().equalsIgnoreCase("UpdateStatus") )
		{
			status = new TradeExecutionUpdateStatusAction( this.ds ).processAction( request, modelAndView );
		}

		status = new TradeExecutionDisplayAction( this.ds ).processAction( request, modelAndView );
	
	}
	
}
