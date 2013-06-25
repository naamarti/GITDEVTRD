package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	26 Nov 2012 DJM 
 *  
 * =================================================================================================
 */
public class DashboardBankGeneralInfoSummaryView extends DefaultModelTable
{	

	public final String BANK_NAME			= "bankName";
	public final String CODE				= "code";
	public final String TARGET				= "target";
	public final String MAX					= "max";
	
	public DashboardBankGeneralInfoSummaryView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_BankGeneralInfoSummary" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               			Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"Name",						DatabaseDataType.CHAR,				50,		"Name",							"Bank Name" 		);
		this.addColumn( this.CODE,					"Code",						DatabaseDataType.CHAR,				4,		"Code",							"Bank Code" 		);
		this.addColumn( this.TARGET,				"Target",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Target",						"Target" 			);
		this.addColumn( this.MAX,					"Max",						DatabaseDataType.DECIMAL_AMOUNT,	0,		"Max",							"Max" 				); 
	}

}


