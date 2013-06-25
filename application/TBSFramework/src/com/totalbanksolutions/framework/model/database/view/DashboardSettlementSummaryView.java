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
public class DashboardSettlementSummaryView extends DefaultModelTable
{	

	public final String BANK_NAME	= "bankName";
	public final String WIRE_IN		= "wireIn";
	public final String WIRE_OUT	= "wireOut";

	public DashboardSettlementSummaryView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_SettlementSummary" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"BankName",					DatabaseDataType.CHAR,				30,		"Name",						"Bank Name" 		);
		this.addColumn( this.WIRE_IN,				"WireIn",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Wire In",					"Wire In Amount" 	);
		this.addColumn( this.WIRE_OUT,				"WireOut",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Wire Out",					"Wire Out Amount" 	); 
	}

}


