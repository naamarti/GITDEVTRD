package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Created:  14 Dec 2012 DJM
 * =================================================================================================
 */
public class TradingUserTransactionView extends DefaultModelTable
{	
	public final String TRANSACTION_ID			= "transactionId";
	public final String SECURITY				= "security";
	public final String TRANSACTION_TYPE		= "transactionType";
	public final String QUANTITY				= "quantity";
	public final String PRICE					= "price";
	public final String EFFECTIVE_DATE			= "effectiveDate";

	public TradingUserTransactionView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.TRANSACTION_ID,		"TransactionId",			DatabaseDataType.CHAR,				7,		"Transaction ID",			"Transaction Id"		);
		this.addColumn( this.SECURITY,				"SecurityName",				DatabaseDataType.CHAR,				50,		"Security",					"Security"				);
		this.addColumn( this.TRANSACTION_TYPE,		"TransactionType",			DatabaseDataType.CHAR,	 			10,	    "Type",						"Transaction Type"		);
		this.addColumn( this.QUANTITY,				"Quantity",					DatabaseDataType.DECIMAL_AMOUNT,	0,	    "Quantity",					"Quantity"				);
		this.addColumn( this.PRICE,					"Price",					DatabaseDataType.DECIMAL_AMOUNT,	0,	    "Price",					"Price"					);
		this.addColumn( this.EFFECTIVE_DATE,		"EffectiveDate",			DatabaseDataType.DATETIME,	 		0,	    "Effective Date",			"Effective Date"		);
	}

}


