package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	14 Nov 2012 SLP 
 *  
 * =================================================================================================
 */
public class DashboardAccountInfoTransactionView extends DefaultModelTable
{	

	public final String TRANSACTION_DATE	= "transactionDate";
	public final String TRANSACTION_TYPE	= "transactionType";
	public final String TRANSACTION_AMOUNT	= "transactionAmount";
	public final String TRANSACTION_BALANCE	= "transactionBalance";

	
	public DashboardAccountInfoTransactionView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		//this.setTableName( "View_AccountInfoTransaction" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.TRANSACTION_DATE,		"Date",						DatabaseDataType.CHAR,				50,		"Date",						"Date" 							);
		this.addColumn( this.TRANSACTION_TYPE,		"Trans",					DatabaseDataType.CHAR,				50,		"Type",						"Transaction Type"			 	);
		this.addColumn( this.TRANSACTION_AMOUNT,	"Amount",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Amount",					"Transaction Amount"			); 
		this.addColumn( this.TRANSACTION_BALANCE,	"Balance",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Balance",					"Transaction Balance" 			); 
	}

}


