package com.totalbanksolutions.trading.web.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.model.ModelAndView;

public class AccountInfoModelHelper extends ModelHelper {

	protected final Log log = LogFactory.getLog( AccountInfoModelHelper.class );

	private final String TABLE_OPEN_ORDERS				= "tableOpenOrders";
	private final String TABLE_POSITIONS				= "tablePositions";
	private final String TABLE_TRANSACTIONS				= "tableTransactions";
	private final String TABLE_ACCOUNT_SUMMARY			= "tableAccountSummary";
	
	public AccountInfoModelHelper( ModelAndView mv )
	{
		super(mv);
		if( !mv.getModel().containsKey("AccountInfoModelHelper") )
		{
			init();
			mv.getModel().put("AccountInfoModelHelper", true);
		}
	}

	public void init()
	{
		this.setOpenOrdersTable(null);
		this.setPositionsTable(null);
		this.setTransactionsTable(null);
		this.setAccountSummaryTable(null);
	}
	
	// Getters
	public WebTable getOpenOrdersTable() {
		return (WebTable)get( this.TABLE_OPEN_ORDERS );
	}

	public WebTable getPositionsTable() {
		return (WebTable)get( this.TABLE_POSITIONS );
	}

	public WebTable getTransactionsTable() {
		return (WebTable)get( this.TABLE_TRANSACTIONS );
	}
	
	public WebTable getAccountSummaryTable() {
		return (WebTable)get( this.TABLE_ACCOUNT_SUMMARY );
	}
	
	// Setters
	public void setOpenOrdersTable( WebTable t ) {
		put( this.TABLE_OPEN_ORDERS, t);
	}

	public void setPositionsTable( WebTable t ) {
		put( this.TABLE_POSITIONS, t);
	}

	public void setTransactionsTable( WebTable t ) {
		put( this.TABLE_TRANSACTIONS, t);
	}

	public void setAccountSummaryTable( WebTable t ) {
		put( this.TABLE_ACCOUNT_SUMMARY, t);
	}
	
}
