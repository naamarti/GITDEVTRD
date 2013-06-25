package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class MailingCountry extends AbstractDatabaseBean<MailingCountry.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "MailingCountries";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 COUNTRY_NAME
		,ID
		,POSTAL_CODE
		;
	}

	public MailingCountry()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName			Database_FieldName            	Database_DataType                   Size    IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.COUNTRY_NAME,			"Country",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.ID,					"Country_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.POSTAL_CODE,			"PostalCode",					DatabaseDataType.CHAR,				2,		false);
	}

}
