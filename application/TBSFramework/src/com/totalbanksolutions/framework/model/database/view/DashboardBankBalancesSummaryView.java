package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	17 Oct 2012 NM #    : dataservice for all dashboard related items
 * 	Modified:   20 Oct 2012 DJM
 * =================================================================================================
 */
public class DashboardBankBalancesSummaryView extends DefaultModelTable
{	

	public final String BANK_NAME					= "bankName";
	public final String BANK_ACTIVITY				= "bankActivity";
	public final String BANK_BALANCE				= "bankBalance";

	public DashboardBankBalancesSummaryView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_BankBalancesSummary" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"BankName",					DatabaseDataType.CHAR,				30,		"Name",						"Bank Name" 	);
		this.addColumn( this.BANK_ACTIVITY,			"Activity",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Activity",					"Bank Activity" );
		this.addColumn( this.BANK_BALANCE,			"Balance",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Balance",					"Bank Balance" 	); 
	}

}


