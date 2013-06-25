package com.totalbanksolutions.trading.web.action.tradeexecution;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;
import com.totalbanksolutions.trading.web.action.market.MarketDisplayAction;
import com.totalbanksolutions.trading.web.model.TradeExecutionModelHelper;

/**
 * Modified: 24 Apr 2013 VAC #2420: TBS Units Trading System - Bug Updating Trade Status
 */
public class TradeExecutionUpdateStatusAction implements Action
{
	protected final Log log = LogFactory.getLog( MarketDisplayAction.class );
	private DataService ds;

	public TradeExecutionUpdateStatusAction(DataService ds)
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
			String transactionId = WebUtils.getStringParameterValue(request, "transactionId");
			long statusId = WebUtils.getLongParameterValue(request, "statusId");
			
			ds.trading.updateTradingUserTransaction(transactionId, statusId);

			h.setMessageSuccess( "Status successfuly updated." );
		}
		catch( Exception e )
		{
			h.setMessageError( "Unexpected error while updating Status. Please try again.");
			h.setMessageDebug( ExceptionUtils.getMessage(e) );
			log.error( h.getMessageError() + "\n" + h.getMessageDebug() + "\n" + ExceptionUtils.getStackTrace(e) );
			status = false;
		}
		
		return status;
	}

}
