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
public class DashboardAccountInfoBankBalanceView extends DefaultModelTable
{	

	public final String BANK_NAME	= "bankName";
	public final String BANK_BALANCE	= "bankBalance";


	
	public DashboardAccountInfoBankBalanceView()
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
		this.addColumn( this.BANK_NAME,				"name1",					DatabaseDataType.CHAR,				50,		"BankName",					"Bank Name" 							);
		this.addColumn( this.BANK_BALANCE,			"bal1",						DatabaseDataType.DECIMAL_AMOUNT,	0,		"Balance",					"Balance" 								); 
	}	

}


