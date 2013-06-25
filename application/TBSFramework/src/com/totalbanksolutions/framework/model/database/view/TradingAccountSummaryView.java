package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Created:  17 Dec 2012 DJM
 * =================================================================================================
 */
public class TradingAccountSummaryView extends DefaultModelTable
{	
	public final String USER_NAME				= "userName";
	public final String QUANTITY				= "quantity";

	public TradingAccountSummaryView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.USER_NAME,				"UserName",					DatabaseDataType.CHAR,				50,		"Account",					"Account Name"							);
		this.addColumn( this.QUANTITY,				"Quantity",					DatabaseDataType.DECIMAL_AMOUNT,	 0,	    "Quantity",					"Total Share Quantity "					);
	}

}


