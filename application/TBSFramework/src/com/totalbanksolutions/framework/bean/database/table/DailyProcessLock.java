package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class DailyProcessLock extends AbstractDatabaseBean<DailyProcessLock.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessLock";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		  DETAILS
		, IS_LOCKED
		, LOCK_NUMBER
		, MODIFIED_BY
		, MODIFIED_DATE
		, PROGRAM_ID
		, VERSION_NUMBER
		;
	}
	
	public DailyProcessLock()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName				Database_FieldName			Database_DataType                   Size    IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.DETAILS,					"Details",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.IS_LOCKED,					"IsLocked",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LOCK_NUMBER,				"LockNumber",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.MODIFIED_BY,				"LastModifiedByUserName",	DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.MODIFIED_DATE,				"LastModifiedDateTime",		DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.PROGRAM_ID,				"Program_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.VERSION_NUMBER,			"VersionNumber",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

	// Getters
	public String getDetails()
	{
		return this.getField(Field.DETAILS).getStringValue();		
	}

	public boolean isLocked()
	{
		return this.getField(Field.IS_LOCKED).getBooleanValue();
	}

	public long getVersionNumber()
	{
		return this.getField(Field.VERSION_NUMBER).getLongValue();
	}
}
