package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class EnvelopeTypes extends AbstractDatabaseBean<EnvelopeTypes.Field> 
{
	public static String 	DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String 	TABLE_NAME = "EnvelopeTypes";
	public static String	DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 DEPOSIT_INSTITUTION	(1)
		,SOURCE_INSTITUTION		(2)
		,TBS					(3)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}

	public enum Field
	{
		 ENVELOPE_TYPE_ID
		,ENVELOPE_TYPE
		;
	}

	public EnvelopeTypes()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ENVELOPE_TYPE_ID,			"EnvelopeType_PK",			DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.ENVELOPE_TYPE,				"Type",						DatabaseDataType.CHAR,					50,		false);
	}

}
