package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all Trading order related items
 * =================================================================================================
 */
public class TradingExecutionTransactionView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String TRANSACTION_ID				= "transactionId";
	public final String QUANTITY					= "quantity";	
	public final String PRICE			    		= "price";
	public final String TOTAL			    		= "total";
	public final String EFFECTIVEDATE				= "effectiveDate";
	public final String TRANSACTION_STATUS			= "transactionStatus";
	public final String BUY_ORDER_ID				= "buyOrderId";
	public final String SELL_ORDER_ID				= "sellOrderId";
	public final String BUY_USERNAME 				= "buyUsername";
	public final String SELL_USERNAME				= "sellUsername";
	

	public TradingExecutionTransactionView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.TRANSACTION_ID,			"TransactionId",			DatabaseDataType.CHAR,	 		 7,		"Transaction ID",			"Transaction ID");
		this.addColumn( this.QUANTITY,					"Quantity",					DatabaseDataType.INT,    		 0,		"Quantity",					"Quantity");
		this.addColumn( this.PRICE,				   	 	"Price",					DatabaseDataType.DECIMAL_AMOUNT, 0,		"Price",					"Price");
		this.addColumn( this.TOTAL,				   	 	"Total",					DatabaseDataType.DECIMAL_AMOUNT, 0,		"Total",					"Total");
		this.addColumn( this.EFFECTIVEDATE,				"EffectiveDate",			DatabaseDataType.DATETIME,       0,    	"Effective Date",			"Effective Date");
		this.addColumn( this.TRANSACTION_STATUS,		"TransactionStatus",		DatabaseDataType.CHAR,			 40,    "Status",					"Status");	
		this.addColumn( this.BUY_ORDER_ID,	     		"BuyOrderNumber",			DatabaseDataType.CHAR,     		 10,	"Buy Order ID",				"Buy Order ID");
		this.addColumn( this.SELL_ORDER_ID,				"SellOrderNumber",			DatabaseDataType.CHAR,           10,    "Sell Order ID",			"Sell Order ID");
		this.addColumn( this.BUY_USERNAME,				"BuyUsername",		    	DatabaseDataType.CHAR,			 40,    "Buyer",					"Buyer");	
		this.addColumn( this.SELL_USERNAME,	   	    	"SellUsername",		    	DatabaseDataType.CHAR,			 40,    "Seller",					"Seller");	
		
	}

}


