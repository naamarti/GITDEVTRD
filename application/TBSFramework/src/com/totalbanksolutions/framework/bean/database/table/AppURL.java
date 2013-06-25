package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 */
public class AppURL extends AbstractDatabaseBean<AppURL.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppURL";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 APP_URL
		,ENVIRONMENT_NAME
		,APP
		,APP_URL_PREFIX
		,APP_SECURE_URL_PREFIX
		;
	}

	public AppURL()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName               		Database_FieldName            Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.APP_URL,					"AppUrl_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ENVIRONMENT_NAME,			"EnvironmentName",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.APP,						"App_FK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.APP_URL_PREFIX,			"AppURLPrefix",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.APP_SECURE_URL_PREFIX,		"AppSecureURLPrefix",			DatabaseDataType.CHAR,				255,	false);
	}
	
}
