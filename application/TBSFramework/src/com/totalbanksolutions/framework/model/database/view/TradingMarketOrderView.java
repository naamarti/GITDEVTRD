package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all Trading order related items
 * =================================================================================================
 */
public class TradingMarketOrderView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	

	public final String BID_ORDER_ID			= "MarketBidOrderId";
	public final String ASK_ORDER_ID			= "MarketAskOrderId";	
	public final String BID_TIME			    = "MarketBidTime";
	public final String ASK_TIME		     	= "MarketAskTime";	
	public final String BID_PRICE				= "MarketBidPrice";
	public final String BID_QTY					= "MarketBidQty";
	public final String ASK_PRICE				= "MarketAskPrice";
	public final String ASK_QTY					= "MarketAskQty";
	public final String CONVERGENCE_ROW 		= "HighlightRow";
	public final String BLANK			 		= "Blank";

	public TradingMarketOrderView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BID_PRICE,				"Bid_Price",				DatabaseDataType.DECIMAL_AMOUNT,	 0,		"Price",					    "");
		this.addColumn( this.BID_TIME,				"Bid_Time",					DatabaseDataType.CHAR,	 		     40,	"Date Time",					"");
		this.addColumn( this.BID_QTY,				"Bid_Qty",					DatabaseDataType.DECIMAL_AMOUNT,	 0,		"Quantity",						"");
		this.addColumn( this.ASK_PRICE,				"Ask_Price",				DatabaseDataType.DECIMAL_AMOUNT,	 0,		"Price",				        "");
		this.addColumn( this.ASK_TIME,				"Ask_Time",					DatabaseDataType.CHAR,	 		     40,	"Date Time",					"");
		this.addColumn( this.ASK_QTY,				"Ask_Qty",					DatabaseDataType.DECIMAL_AMOUNT,	 0,	    "Quantity",						"");
		this.addColumn( this.BID_ORDER_ID,			"Bid_OrderId",				DatabaseDataType.CHAR,               30,    "Order ID",						"");
		this.addColumn( this.ASK_ORDER_ID,			"Ask_OrderId",				DatabaseDataType.CHAR,               30,    "Order ID",						"");
		this.addColumn( this.CONVERGENCE_ROW,		"Convergence_Row",		    DatabaseDataType.BIT,				 0,     "",						    	"");	
		this.addColumn( this.BLANK,					"blank",		    		DatabaseDataType.CHAR,				 1,     "",						    	"");	
	}

}


