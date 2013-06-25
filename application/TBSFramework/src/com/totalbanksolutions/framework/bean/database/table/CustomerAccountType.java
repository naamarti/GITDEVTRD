package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramAccountTypeMapping.Field;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class CustomerAccountType extends AbstractDatabaseBean<CustomerAccountType.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "CustomerAccount_Types";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 _401K						(8)
		,CORP						(1)
		,GOVT						(2)
		,INDIV						(3)
		,IRA						(4)
		,JOINT						(5)
		,OTHER						(6)
		,PARTNER					(9)
		,PUBLIC						(10)
		,TRUST						(7)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}

	public enum Field
	{
		 ACCOUNT_TYPE
		,ACCOUNT_TYPE_ID
		,FDIC_LIMIT
		;
	}

	public CustomerAccountType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName				Database_FieldName            	Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_TYPE,			"AccountType",					DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.ACCOUNT_TYPE_ID,		"CustomerAccount_Type_PK",		DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.FDIC_LIMIT,			"FDICLimit",					DatabaseDataType.CHAR,				10,		false);
	}
	
	// Getters
	public String getAccountType()
	{
		return this.getField(Field.ACCOUNT_TYPE).getStringValue();
	}

	public long getAccountTypeId()
	{
		return this.getField(Field.ACCOUNT_TYPE_ID).getLongValue();
	}

}
