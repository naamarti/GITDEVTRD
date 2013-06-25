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
public class DashboardAccountInfoBalanceView extends DefaultModelTable
{	

	public final String ACCOUNT_NUMBER	= "acctNumber";
	public final String OPENING_BALANCE	= "openingBalance";
	public final String INTEREST		= "interest";
	public final String PENDING_BALANCE	= "pendingBalance";
	public final String TOTAL_BALANCE	= "totalBalance";
	public final String PRODUCT			= "product";
	public final String ACCOUNT_TYPE	= "acctType";
	
	public DashboardAccountInfoBalanceView()
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
		this.addColumn( this.ACCOUNT_NUMBER,		"AccountNo",				DatabaseDataType.CHAR,				50,		"Account Number",			"Account Number" 							);
		this.addColumn( this.OPENING_BALANCE,		"PrincBal",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Opening Balance",			"Opening Balance"			 	);
		this.addColumn( this.INTEREST,				"AccrInt",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Interest",					"Interest"					); 
		this.addColumn( this.PENDING_BALANCE,		"CurrBal",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Pending Balance",			"Pending Balance" 			); 
		this.addColumn( this.TOTAL_BALANCE,		    "TotalBal",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Total Balance",			"Total Balance" 			); 
		this.addColumn( this.PRODUCT,				"Product",					DatabaseDataType.CHAR,				50,		"Product",					"Product" 			); 
		this.addColumn( this.ACCOUNT_TYPE,			"AcctType",					DatabaseDataType.CHAR,				50,		"Account Type",					"Account Type" 			); 
		
	
	}

}


