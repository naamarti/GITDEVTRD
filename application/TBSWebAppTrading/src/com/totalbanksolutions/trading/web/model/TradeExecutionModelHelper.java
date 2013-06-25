package com.totalbanksolutions.trading.web.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.Program;
import com.totalbanksolutions.framework.model.database.table.TradingTransactionStatusTypesTable;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.model.ModelAndView;

public class TradeExecutionModelHelper extends ModelHelper {
	private final String TABLE_TRADE_EXECUTION				= "tableTradeExecution";
	private final String STATUS_TYPES_LIST					= "tableStatusTypes";
	private final String TABLE_EXECUTION_STATUS				= "tableExecutionStatus";
	private final String TEST								= "test";
	private final String STATUS_ID							= "statusId";
	private final String TRANSACTION_ID						= "transactionId";

	public TradeExecutionModelHelper( ModelAndView mv )
	{
		super(mv);
		if( !mv.getModel().containsKey("TradeExecutionModelHelper") )
		{
			init();
			mv.getModel().put("TradeExecutionModelHelper", true);
		}
	}

	public void init()
	{
		this.setTradeExecutionTable(null);
		this.setTest("wtf");
		this.setStatusTypeTable(null);
		this.setExecutionStatusTable(null);
		this.setStatusId(0);
		this.setTransactionId(0);
	}
	
	// Getters
	public WebTable getTradeExecutionTable() {
		return (WebTable)this.get( this.TABLE_TRADE_EXECUTION );
	}
	public WebTable getStatusTypeTable() {
		return (WebTable)this.get( this.STATUS_TYPES_LIST );
	}
	public WebTable getExecutionStatusTable() {
		return (WebTable)this.get( this.TABLE_EXECUTION_STATUS );
	}
	public long getStatusId() {
		return (long)mv.getModel().get( this.STATUS_ID );
	}
	public long getTransactionId() {
		return (long)mv.getModel().get( this.TRANSACTION_ID );
	}
	
	// Setters
	public void setTradeExecutionTable( WebTable t ) {
		this.put( this.TABLE_TRADE_EXECUTION, t);
	}	
	public void setStatusTypeTable( WebTable t ) {
		this.put( this.STATUS_TYPES_LIST, t);
	}
	public void setExecutionStatusTable( WebTable t ) {
		this.put( this.TABLE_EXECUTION_STATUS, t);
	}
	public void setTest( String test ) {
		mv.getModel().put( this.TEST, test );
	}
	public void setStatusId( long statusId ) {
		mv.getModel().put( this.STATUS_ID, statusId );
	}
	public void setTransactionId( long transactionId ) {
		mv.getModel().put( this.TRANSACTION_ID, transactionId );
	}

}
