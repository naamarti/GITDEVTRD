package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  14 Dec 2012 VC
 * Modified: AW, Mar 21, 2013, GEM# 2283
 * =================================================================================================
 */
public class TradingUserPositionView extends DefaultModelTable
{	
	public final String SECURITY				= "security";
	public final String QUANTITY				= "quantity";
	public final String PENDING_QUANTITY		= "pending_quantity";

	public TradingUserPositionView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.SECURITY,				"SecurityName",				DatabaseDataType.CHAR,				50,		"Security",					"Security");
		this.addColumn( this.QUANTITY,				"Quantity",					DatabaseDataType.DECIMAL_AMOUNT,	 0,	    "Available Quantity",		"Quantity");
		this.addColumn( this.PENDING_QUANTITY,		"Pending_Quantity",			DatabaseDataType.DECIMAL_AMOUNT,	 0,	    "Pending Quantity",			"Pending Quantity");
	}

}


