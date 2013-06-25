package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	09 Nov 2012 DJM 
 *  
 * =================================================================================================
 */
public class DashboardMoneyMovementDetailView extends DefaultModelTable
{	

	public final String BANK_NAME			= "bankName";
	public final String ACTIVITY_NOW		= "activityNOW";
	public final String ACTIVITY_MMDA		= "activityMMDA";
	public final String ACTIVITY_TOTAL		= "activityTotal";
	public final String INTEREST_PAID_EARLY	= "interestPaidEarly";
	public final String INTEREST_ACCRUED	= "interestAccrued";
	
	public DashboardMoneyMovementDetailView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_MoneyMovementDetail" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               			Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"PDA_Name",					DatabaseDataType.CHAR,				50,		"Bank Name",					"Bank Name" 		);
		this.addColumn( this.ACTIVITY_NOW,			"Activity_NOW",				DatabaseDataType.DECIMAL_AMOUNT,	0,		"Activity - Trans Acct",		"Activity NOW" 		);
		this.addColumn( this.ACTIVITY_MMDA,			"Activity_MMDA",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"Activity - MMDA",				"Activity MMDA" 	); 
		this.addColumn( this.ACTIVITY_TOTAL,		"ActivityTotal",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"Activity Total",				"Activity Total" 	); 
		this.addColumn( this.INTEREST_PAID_EARLY,	"InterestPaidEarly",		DatabaseDataType.DECIMAL_AMOUNT,	0,		"Prepaid Int.",					"Prepaid Interest" 	); 
		this.addColumn( this.INTEREST_ACCRUED,		"InterestAccrued",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"Int. Accrued ",				"Interest Accrued" 	); 
	}

}


