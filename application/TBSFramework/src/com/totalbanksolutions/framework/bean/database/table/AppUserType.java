package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;

public class AppUserType extends AbstractDatabaseBean<AppUserType.Field> 
{
	public static String 	DATABASE_NAME = Databases.COMMON;
	public static String 	TABLE_NAME = "AppUserType";
	public static String	DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 BANK		(2)
		,BROKER		(3)
		,TBS		(4)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}
	
	public enum Field
	{
		 DESCRIPTION
		,USER_TYPE
		,USER_TYPE_ID
		;
	}

	public AppUserType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DESCRIPTION,			"Description",				DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.USER_TYPE,				"UserType",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.USER_TYPE_ID,			"UserType_PK",				DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

	// Getters
	public String getUserType()
	{
		return this.getField(Field.USER_TYPE).getStringValue();
	}
	public long getUserTypeId()
	{
		return this.getField(Field.USER_TYPE_ID).getLongValue();
	}

}
