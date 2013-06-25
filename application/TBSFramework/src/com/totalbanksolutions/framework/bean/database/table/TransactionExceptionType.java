package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class TransactionExceptionType extends AbstractDatabaseBean<TransactionExceptionType.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "TransactionExceptionType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 DEPOSIT_OVER_FDIC_LIMIT	(1)
		,WITHDRAWAL_OVER_BALANCE	(2)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}
	
	public enum Field
	{
		 DESCRIPTION
		,EXCEPTION_TYPE
		,EXCEPTION_TYPE_ID
		;
	}

	public TransactionExceptionType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DESCRIPTION,			"Description",				DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.EXCEPTION_TYPE,		"ExceptionType",			DatabaseDataType.CHAR,					100,	false);
		super.addField(Field.EXCEPTION_TYPE_ID,		"ExceptionType_PK",			DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

}
