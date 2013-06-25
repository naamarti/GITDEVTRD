package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	27 Nov 2012 DJM
 * =================================================================================================
 */
public class DashboardBankPDARatesView extends DefaultModelTable
{	

	public final String BANK_NAME			= "bankName";
	public final String ACCOUNT_NUMBER		= "accountNumber";
	public final String BASE_INDEX_TYPE		= "baseIndexType";
	public final String BASE_INDEX_RATE		= "baseIndexRate";
	public final String PEG_AMOUNT			= "pegAmount"; 
	public final String EFFECTIVE_RATE		= "effectiveRate";
	public final String START_DATE			= "startDate";
	public final String TERMINATION_DATE	= "termDate";
	public final String INTEREST_TYPE		= "interestType";
	
	public DashboardBankPDARatesView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_PDARates" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               				Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"BankName",					DatabaseDataType.CHAR,				30,		"Name",								"Bank Name" 								);
		this.addColumn( this.ACCOUNT_NUMBER,		"AccountNumber",			DatabaseDataType.CHAR,				20,		"Account #",						"Account Number" 							);
		this.addColumn( this.BASE_INDEX_TYPE,		"BaseIndexType",			DatabaseDataType.CHAR,				3,		"Index",							"Base Index Type" 							);
		this.addColumn( this.BASE_INDEX_RATE,		"BaseIndexRate",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"Index",							"Base Index Rate" 							);
		this.addColumn( this.PEG_AMOUNT,			"PegAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		"Peg",								"Peg Amount" 								);
		this.addColumn( this.EFFECTIVE_RATE,		"EffectiveRate",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"Total",							"Effective Rate" 							);
		this.addColumn( this.START_DATE,			"StartDate",				DatabaseDataType.DATETIME,			0,		"Start Date",						"Start Date" 								);
		this.addColumn( this.TERMINATION_DATE,		"TermDate",					DatabaseDataType.DATETIME,			0,		"Termination Date",					"Termination Date" 							);
		this.addColumn( this.INTEREST_TYPE,			"InterestType",				DatabaseDataType.CHAR,				20,		"Interest Type",					"Interest Type" 							);
	}

}


