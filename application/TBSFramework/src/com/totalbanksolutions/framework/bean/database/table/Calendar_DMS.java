package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
//import com.totalbanksolutions.framework.bean.database.table.Calendar_DMS.Field;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;


public class Calendar_DMS extends AbstractDatabaseBean<Calendar_DMS.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Calendar_DMS";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	


	public enum Field
	{
		 CALENDAR_ID
		,BUSINESS_DATE
		,WELCOME_MESSAGE
		;
	}

	public Calendar_DMS()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName				Database_FieldName            	Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.CALENDAR_ID,			"Calendar_DMS_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.BUSINESS_DATE,			"BusinessDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.WELCOME_MESSAGE,		"WelcomeMessage",				DatabaseDataType.CHAR,				255,	false);
	}
	
	// Getters
	public String getBusinessDate()
	{
		return this.getField(Field.WELCOME_MESSAGE).toString();
	}

	// Setters

}
