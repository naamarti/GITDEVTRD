package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class MailingState extends AbstractDatabaseBean<MailingState.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "MailingStates";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 ID
		,POSTAL_CODE
		,STATE_NAME
		;
	}

	public MailingState()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName			Database_FieldName            	Database_DataType                   Size    IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,					"State_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.POSTAL_CODE,			"PostalCode",					DatabaseDataType.CHAR,				2,		false);
		super.addField(Field.STATE_NAME,			"State",						DatabaseDataType.CHAR,				50,		false);
	}

	public String getPostalCode()
	{
		return this.getField(Field.POSTAL_CODE).getStringValue();
	}

	public String getStateName()
	{
		return this.getField(Field.STATE_NAME).getStringValue();
	}


}
