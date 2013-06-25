package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all Trading order related items
 * =================================================================================================
 */
public class TradingOrderView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	

	public final String ORDER_ID				= "OrderId";
	public final String USER_ID					= "UserId";	
	public final String ENTRYDATE			    = "EntryDate";
	public final String SECURITY_ID		     	= "Security_Id";	
	public final String TYPE_ID					= "Type_Id";
	public final String QUANTITY				= "Quantity";
	public final String PRICE					= "Price";
	public final String STATUS_CODE				= "StatusCode";
	public final String USERNAME 				= "UserName";
	public final String SECURITYNAME			= "SecurityName";
	public final String TRANSACTIONTYPE			= "TransactionType";
	

	public TradingOrderView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.ORDER_ID,				"Order_PK",					DatabaseDataType.INT,	 0,		"Order ID",					    "");
		this.addColumn( this.USER_ID,				"User_FK",					DatabaseDataType.INT,     0,		"User ID",					        "");
		this.addColumn( this.ENTRYDATE,				"EntryDate",				DatabaseDataType.DATETIME,			 0,		"Entry Date",						"");
		this.addColumn( this.SECURITY_ID,			"Security_FK",				DatabaseDataType.INT,	 0,		"Security ID",				        "");
		this.addColumn( this.TYPE_ID,				"Type_FK",					DatabaseDataType.INT,     0,		"Type ID",					        "");
		this.addColumn( this.QUANTITY,				"Quantity",					DatabaseDataType.DECIMAL_AMOUNT,	 0,	    "Quantity",						"");
		this.addColumn( this.PRICE,					"Price",					DatabaseDataType.DECIMAL_AMOUNT,     0,    	"Price",						"");
		this.addColumn( this.STATUS_CODE,			"Code",						DatabaseDataType.CHAR,               40,    "Status",						"");
		this.addColumn( this.USERNAME,				"UserName",		   		    DatabaseDataType.CHAR,				 40,     "Iser",						    	"");	
		this.addColumn( this.SECURITYNAME,			"Name",		    			DatabaseDataType.CHAR,				 40,     "Security Name",						    	"");	
		this.addColumn( this.TRANSACTIONTYPE,		"TransactionType",		    DatabaseDataType.CHAR,				 40,     "TransType",						    	"");	
		
	}

}


