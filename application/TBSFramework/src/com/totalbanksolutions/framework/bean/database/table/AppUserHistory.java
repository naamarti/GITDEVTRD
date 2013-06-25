package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class AppUserHistory extends AbstractDatabaseBean<AppUserHistory.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppUser_History";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 APPUSER_HISTORY_ID
		,APPUSER_ID
		,DATE_ADDED
		,USER_PASSWORD
		;
	}

	public AppUserHistory()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName								Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.APPUSER_HISTORY_ID,					"AppUser_History_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.APPUSER_ID,							"AppUser_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.DATE_ADDED,							"DateAdded",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.USER_PASSWORD,							"UserPassword",					DatabaseDataType.CHAR,				40,		false);
	}

	// Getters
	public long getAppUserId()
	{
		return this.getField(Field.APPUSER_ID).getLongValue();
	}
	
	public Date getDateAdded()
	{
		return this.getField(Field.DATE_ADDED).getDateValue();
	}
	
	public String getUserPassword()
	{
		return this.getField(Field.USER_PASSWORD).getStringValue();
	}
	
}
