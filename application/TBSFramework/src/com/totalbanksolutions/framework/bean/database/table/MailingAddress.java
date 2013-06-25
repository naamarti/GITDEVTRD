package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class MailingAddress extends AbstractDatabaseBean<MailingAddress.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "MailingAddresses";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 CITY_NAME
		,COUNTRY_ID		
		,ID
		,LINE1
		,LINE2
		,LINE3
		,LINE4
		,STATE_ID
		,ZIP_CODE
		;
	}

	public MailingAddress()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.CITY_NAME,					"City",						DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.COUNTRY_ID,				"Country_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		false);		
		super.addField(Field.ID,						"MailingAddress_PK",		DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.LINE1,						"Line1",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.LINE2,						"Line2",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.LINE3,						"Line3",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.LINE4,						"Line4",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.STATE_ID,					"State_FK",					DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.ZIP_CODE,					"ZipCode",					DatabaseDataType.CHAR,					5,		false);		
	}

	// Getters
	public String getCityName()
	{
		return this.getField(Field.CITY_NAME).getStringValue();
	}
	
	public long getCountryId()
	{
		return this.getField(Field.COUNTRY_ID).getLongValue();
	}
	
	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
	public String getLine1()
	{
		return this.getField(Field.LINE1).getStringValue();
	}
	
	public String getLine2()
	{
		return this.getField(Field.LINE2).getStringValue();
	}

	public String getLine3()
	{
		return this.getField(Field.LINE3).getStringValue();
	}

	public String getLine4()
	{
		return this.getField(Field.LINE4).getStringValue();
	}

	public long getStateId()
	{
		return this.getField(Field.STATE_ID).getLongValue();
	}
	
	public String getZipCode()
	{
		return this.getField(Field.ZIP_CODE).getStringValue();
	}
	
	// Setters
	public void setCountryId(long n)
	{
		this.getField(Field.COUNTRY_ID).setValue(n);
	}

	public void setStateId(long n)
	{
		this.getField(Field.STATE_ID).setValue(n);
	}

}
