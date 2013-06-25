package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all Trading order related items
 * =================================================================================================
 */
public class TradingUserOrderView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String ORDER_ID				= "orderId";
	public final String ENTRY_DATE				= "entryDate";
	public final String SECURITY				= "security";
	public final String SECURITY_ID				= "securityId";
	public final String ORDER_TYPE				= "orderType";
	public final String ORDER_TYPE_ID			= "orderTypeId";
	public final String ORIGINAL_QUANTITY		= "originalQuantity";
	public final String QUANTITY				= "quantity";
	public final String PRICE					= "price";
	public final String STATUS 				    = "status";
	public final String STATUS_ID 				= "statusId";
	public final String ESTIMATED_VALUE			= "estimatedValue";
	
	public TradingUserOrderView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.ORDER_ID,			"OrderId",						DatabaseDataType.CHAR,	 			7,		"Order ID",						"Order ID"				);
		this.addColumn( this.ENTRY_DATE,		"EntryDate",					DatabaseDataType.DATETIME2,	        0,		"Entry Date",					"Entry Date"			);
		this.addColumn( this.SECURITY,			"Security",						DatabaseDataType.CHAR,				50,		"Security",						"Security"				);
		this.addColumn( this.SECURITY_ID,		"Security_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		"Security",						"Security"				);
		this.addColumn( this.ORDER_TYPE,		"Order_Type",				    DatabaseDataType.CHAR,				50,		"Action",						"Buy or Sell"			);
		this.addColumn( this.ORDER_TYPE_ID,		"Order_Type_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		"Action",						"Buy or Sell"			);
		this.addColumn( this.ORIGINAL_QUANTITY,	"Original_Quantity",			DatabaseDataType.DECIMAL_AMOUNT,	0,	    "Original Quantity",			"Original Quantity"		);
		this.addColumn( this.QUANTITY,			"Quantity",						DatabaseDataType.DECIMAL_AMOUNT,	0,	    "Quantity",						"Quantity"				);
		this.addColumn( this.PRICE,				"Price",						DatabaseDataType.DECIMAL_AMOUNT,    0,	    "Price",						"Price $0.00"			);
		this.addColumn( this.STATUS,			"Status",		    			DatabaseDataType.CHAR,				50,     "Status",						"Status"				);	
		this.addColumn( this.STATUS_ID,			"Status_FK",		    		DatabaseDataType.DECIMAL_LONGINT,	 0,     "Status",						"Status"				);	
		this.addColumn( this.ESTIMATED_VALUE,	"EstimatedValue",		    	DatabaseDataType.DECIMAL_AMOUNT,	 0,     "Estimated Value",				"Estimated Value"		);	
	}

}


