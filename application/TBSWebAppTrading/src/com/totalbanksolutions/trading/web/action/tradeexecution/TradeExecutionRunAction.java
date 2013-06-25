package com.totalbanksolutions.trading.web.action.tradeexecution;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.market.MarketDisplayAction;
import com.totalbanksolutions.trading.web.model.TradeExecutionModelHelper;

public class TradeExecutionRunAction implements Action
{
	protected final Log log = LogFactory.getLog( MarketDisplayAction.class );
	private DataService ds;

	public TradeExecutionRunAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		TradeExecutionModelHelper h = new TradeExecutionModelHelper(modelAndView);
		boolean status = true;

		try
		{
			int statusCode = ds.trading.executeTradeMatch();
			
			if(statusCode == 1)
			{
				h.setMessageSuccess( "Process completed successfuly." );
			}
			else if(statusCode == 2)
			{
				h.setMessageWarning("You must wait until this Month's Trade Date and Cutoff Time to run the process!");
			}
			else if(statusCode == 3)
			{
				h.setMessageWarning("It appears the process did not complete successfully during the last run. You must first run the UNDO process before re-running.");
			}
		}
		catch( Exception e )
		{
			h.setMessageError( "Unexpected error while executing Trades. Please try again.");
			h.setMessageDebug( ExceptionUtils.getMessage(e) );
			log.error( h.getMessageError() + "\n" + h.getMessageDebug() + "\n" + ExceptionUtils.getStackTrace(e) );
			status = false;
		}
		
		return status;
	}

}
