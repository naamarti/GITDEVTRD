package com.totalbanksolutions.framework.model.database.table;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class TradingTransactionStatusTypesTable extends DefaultModelTable
{
	public final String TRANSACTIONSTATUSTYPES_ID						= "transactionStatusTypeId";
	public final String TRANSACTIONSTATUSTYPES_CODE						= "transactionStatusTypeCode";
	public final String TRANSACTIONSTATUSTYPES_DESC						= "transactionStatusTypeDesc";
	
	public TradingTransactionStatusTypesTable()
	{
		this.setDatabaseName( Databases.TRADING );
		this.setTableName( "TransactionStatus_Types" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.TRANSACTIONSTATUSTYPES_ID,			"TransactionStatus_PK",	DatabaseDataType.DECIMAL_LONGINT,	0,		"Status Type ID",				"" );
		this.addColumn( this.TRANSACTIONSTATUSTYPES_CODE,			"TransactionStatus",							DatabaseDataType.CHAR,				100,	"Code",			"" );
		this.addColumn( this.TRANSACTIONSTATUSTYPES_DESC,				"Description",					DatabaseDataType.CHAR,				200,	"Description",					"" ); 
	}

}

