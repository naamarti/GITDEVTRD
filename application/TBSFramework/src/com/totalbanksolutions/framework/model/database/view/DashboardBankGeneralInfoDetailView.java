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
public class DashboardBankGeneralInfoDetailView extends DefaultModelTable
{	

	public final String BANK_NAME			= "bankName";
	public final String CODE				= "code";
	public final String CITY				= "city";
	public final String STATE				= "state";
	public final String TARGET				= "target";
	public final String MAX					= "max";
	public final String FDIC_CERT_NUMBER	= "fdicCertNumber";
	public final String ABA_NUMBER			= "abaNumber";
	public final String PERIOD_WD_TYPE		= "periodWithdrawalType";
	public final String DEPOSIT_TYPE		= "depositType";
	public final String SHORT_NAME			= "shortName"; 
	public final String FORMAL_NAME			= "formalName";
	
	public DashboardBankGeneralInfoDetailView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_BankGeneralInfoDetail" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               			Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"Name",						DatabaseDataType.CHAR,				50,		"Name",							"Bank Name" 		);
		this.addColumn( this.CODE,					"Code",						DatabaseDataType.CHAR,				4,		"Code",							"Bank Code" 		);
		this.addColumn( this.CITY,					"City",						DatabaseDataType.CHAR,				20,		"City",							"City" 				);
		this.addColumn( this.STATE,					"State",					DatabaseDataType.CHAR,				20,		"State",						"State" 			);
		this.addColumn( this.TARGET,				"Target",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Target",						"Target" 			);
		this.addColumn( this.MAX,					"Max",						DatabaseDataType.DECIMAL_AMOUNT,	0,		"Max",							"Max" 				); 
		this.addColumn( this.FDIC_CERT_NUMBER,		"FDICCertificateNumber",	DatabaseDataType.CHAR,				20,		"FDIC Cert #",					"FDIC Cert Number" 	);
		this.addColumn( this.ABA_NUMBER,			"ABANumber",				DatabaseDataType.CHAR,				20,		"ABA #",						"ABA Number" 		);
		this.addColumn( this.PERIOD_WD_TYPE,		"PeriodWDType",				DatabaseDataType.CHAR,				20,		"Per WD Type",					"Period WD Type" 	);
		this.addColumn( this.DEPOSIT_TYPE,			"DepositType",				DatabaseDataType.CHAR,				20,		"Deposit Type",					"Deposit Type" 		);
		this.addColumn( this.SHORT_NAME,			"ShortName",				DatabaseDataType.CHAR,				20,		"Short Name",					"Short Name" 		);
		this.addColumn( this.FORMAL_NAME,			"FormalName",				DatabaseDataType.CHAR,				30,		"Formal Name",					"Formal Name" 		);
	}

}


