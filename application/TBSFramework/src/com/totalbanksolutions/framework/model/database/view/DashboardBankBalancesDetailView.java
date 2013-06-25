package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	14 Nov 2012 DJM 
 *  
 * =================================================================================================
 */
public class DashboardBankBalancesDetailView extends DefaultModelTable
{	

	public final String BANK_NAME			= "bankName";
	public final String BALANCE_NOW			= "balanceNOW";
	public final String BALANCE_MMDA		= "balanceMMDA";
	public final String BALANCE_TOTAL		= "balanceTotal";
	public final String IPE_PTD				= "interestPaidEarlyPTD";
	public final String INT_ACCRUED_PTD		= "interestAccruedPTD";
	
	public DashboardBankBalancesDetailView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_BankBalancesDetail" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"PDA_Name",					DatabaseDataType.CHAR,				50,		"Bank Name",				"Bank Name" 					);
		this.addColumn( this.BALANCE_NOW,			"Balance_NOW",				DatabaseDataType.DECIMAL_AMOUNT,	0,		"Balance - Trans Acct",		"Balance Transaction Account" 	);
		this.addColumn( this.BALANCE_MMDA,			"Balance_MMDA",				DatabaseDataType.DECIMAL_AMOUNT,	0,		"Balance - MMDA",			"Balance MMDA" 					); 
		this.addColumn( this.BALANCE_TOTAL,			"BalanceTotal",				DatabaseDataType.DECIMAL_AMOUNT,	0,		"Balance Total",			"Balance Total" 				); 
		this.addColumn( this.IPE_PTD,				"InterestPaidEarly_PTD",	DatabaseDataType.DECIMAL_AMOUNT,	0,		"Prepaid Int. PTD",			"Prepaid Interest PTD" 			); 
		this.addColumn( this.INT_ACCRUED_PTD,		"InterestAccrued_PTD",		DatabaseDataType.DECIMAL_AMOUNT,	0,		"Int. Accrued PTD",			"Interest Accrued PTD" 			); 
	}

}


