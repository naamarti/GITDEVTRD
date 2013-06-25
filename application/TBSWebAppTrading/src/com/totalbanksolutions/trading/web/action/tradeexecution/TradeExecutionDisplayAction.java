package com.totalbanksolutions.trading.web.action.tradeexecution;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.table.TradingTransactionStatusTypesTable;
import com.totalbanksolutions.framework.model.database.view.TradingExecutionStatusView;
import com.totalbanksolutions.framework.model.database.view.TradingExecutionTransactionView;
import com.totalbanksolutions.framework.util.DateUtility;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.bean.WebTableColumn;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.market.MarketDisplayAction;
import com.totalbanksolutions.trading.web.model.TradeExecutionModelHelper;

public class TradeExecutionDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( MarketDisplayAction.class );
	private DataService ds;

	public TradeExecutionDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		TradeExecutionModelHelper h = new TradeExecutionModelHelper(modelAndView);

		h.setViewName("tradeExecution");
		h.setScreenName("TradeExecution");

		Date nextOrderTradingDate = this.ds.trading.getNextOrderTradingDate();
		DateUtility dtUtil = new DateUtility();
		h.setNextOrderTradingDate(dtUtil.convertDateToString(nextOrderTradingDate, "MMMM d, yyyy") );

		h.setStatusTypeTable(getStatusTypeTable() );
		log.debug(h.getStatusTypeTable());
		h.setTradeExecutionTable(getTradeExecutionTable());
		h.setExecutionStatusTable(getExecutionStatusTable());

		return true;
	}

	private WebTable getExecutionStatusTable()
	{
		TradingExecutionStatusView t =  ds.trading.getTradingExecutionStatus();
		
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		w.setDisplayRowCount( ( w.getRowCount() > 4 ? 4 : w.getRowCount() ) );

		w.setShowRowNumbers(false);
		w.setHasTotalRow(false);
		
		c = w.addColumn( t.PROCESS_DATE, 100, Align.LEFT );		
		c.setCellFormatType(WebTableCellFormatType.DATE);
		
		c = w.addColumn( t.PROCESS_STATUS, 100, Align.LEFT );
		
		c = w.addColumn( t.MODIFIED_DATETIME, 100, Align.LEFT );
		c.setCellFormatType(WebTableCellFormatType.DATE);
		
		c = w.addColumn( t.USER_NAME, 100, Align.LEFT );

		c = w.addColumn( t.DETAILS, 400, Align.LEFT );
		c.setZoomable(true);
		
		return w;
	}
	
	private WebTable getStatusTypeTable()
	{
		TradingTransactionStatusTypesTable t =  ds.trading.getTransactionStatusTypeList();
		
		log.debug(t);
		
		
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		w.setDisplayRowCount( ( w.getRowCount() > 9 ? 9 : w.getRowCount() ) );

		w.setShowRowNumbers(false);
		w.setHasTotalRow(false);
		
		
		c = w.addColumn( t.TRANSACTIONSTATUSTYPES_CODE, 110, Align.LEFT );
		
		c = w.addColumn( t.TRANSACTIONSTATUSTYPES_ID, 150, Align.LEFT );
		
		log.debug("tester" + t.TRANSACTIONSTATUSTYPES_CODE);
		return w;
	}
	
	private WebTable getTradeExecutionTable()
	{
		TradingExecutionTransactionView t = this.ds.trading.getTradeExecutionSummary();
		log.debug(t.TRANSACTION_ID);
		
		
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		w.setDisplayRowCount( ( w.getRowCount() > 9 ? 9 : w.getRowCount() ) );

		w.setShowRowNumbers(false);
		w.setHasTotalRow(false);
		
		c = w.addColumn( t.TRANSACTION_ID, 100, Align.LEFT );
		c = w.addColumnActionEdit();
		c = w.addColumn( t.BUY_ORDER_ID, 90, Align.LEFT );
		c = w.addColumn( t.BUY_USERNAME, 100, Align.LEFT );
		c = w.addColumn( t.SELL_ORDER_ID, 90, Align.LEFT );
		c = w.addColumn( t.SELL_USERNAME, 100, Align.LEFT );
		
		c = w.addColumn( t.QUANTITY, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		
		c = w.addColumn( t.PRICE, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);
		
		c = w.addColumn( t.TOTAL, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);

		c = w.addColumn( t.TRANSACTION_STATUS, 90, Align.LEFT );
		
		return w;
	}}
