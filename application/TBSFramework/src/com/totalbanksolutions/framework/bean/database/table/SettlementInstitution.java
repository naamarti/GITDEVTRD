package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class SettlementInstitution extends AbstractDatabaseBean<SettlementInstitution.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "SettlementInstitutions";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public static enum Values
	{
		DEUTSCHE_BANK		( "Deutsche Bank" )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public static enum Field
	{
		 ID
		,NAME
		,SETTLEMENT_WIRE_OUTPUT_DIR
		;
	}

	public SettlementInstitution()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName							Database_FieldName          		Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,									"SettlementInstitution_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.NAME,									"Name",								DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SETTLEMENT_WIRE_OUTPUT_DIR,			"SettlementWireOutputDir",			DatabaseDataType.CHAR,				200,	false);
	}


	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
}
