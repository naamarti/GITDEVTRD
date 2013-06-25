package com.totalbanksolutions.framework.model.database.table;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class DeveloperExamplesConfigurationTable extends DefaultModelTable
{
	public final String APPROVAL_CODE				= "approvalCode";
	public final String COMPANY_NAME				= "companyName";
	public final String CONFIG_ID					= "configurationId";
	public final String DEBUG_LEVEL					= "debugLevel";
	public final String IS_DISABLE_LOGINS			= "isDisableLogins";
	public final String MAX_TRANS_AMOUNT			= "maximumTransactionAmount";
	public final String SESSION_TIMEOUT				= "sessionTimeoutMinutes";
	public final String SETTLE_INST_ID				= "settleInstitutionId";

	public DeveloperExamplesConfigurationTable()
	{
		this.setDatabaseName( Databases.COMMON );
		this.setTableName( "DeveloperExamples_Configuration" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.APPROVAL_CODE,			"ApprovalCode",				DatabaseDataType.CHAR,				4,		"Approval Code",			"Approval Code for the TBS wire system." ); 
		this.addColumn( this.COMPANY_NAME,			"CompanyName",				DatabaseDataType.CHAR,				50,		"Company Name",				"The Company Name that will display on the web site." );
		this.addColumn( this.CONFIG_ID,				"Configuration_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		"Config ID",				"Config ID" );
		this.addColumn( this.DEBUG_LEVEL,			"DebugLevel",				DatabaseDataType.INT,				0,		"Debug Level",				"Controls level of details written to the application log files." );
		this.addColumn( this.IS_DISABLE_LOGINS,		"IsDisableLogins",			DatabaseDataType.BIT,				0,		"Disable Logins",			"Disable client logins to perform releases and sanity testing." );
		this.addColumn( this.MAX_TRANS_AMOUNT,		"MaximumTransactionAmount",	DatabaseDataType.DECIMAL_AMOUNT,	0,		"Max Trans Amount",			"The maximum transaction amount accepted by the system." );
		this.addColumn( this.SESSION_TIMEOUT,		"SessionTimeoutMinutes",	DatabaseDataType.INT,				0,		"Session Timeout",			"The number of minutes a web session will timeout due to inactivity." );
		this.addColumn( this.SETTLE_INST_ID,		"SourceInstitution_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		"Settlement Institution",	"The Settlement Institution for the wire system." );
	}

}

