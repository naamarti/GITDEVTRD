package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class SettlementAccount extends AbstractDatabaseBean<SettlementAccount.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "SettlementAccounts";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public static enum Values
	{
		
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public static enum Field
	{
		 ID
		,PROGRAM_ID
		,ACCOUNT_TYPE_ID
		,ACCOUNT_NUMBER
		,DESCRIPTION
		,WIRE_TEMPLATE_NAME
		,ABA_NUMBER
		,SETTLEMENT_INSTITUTION_ID
		;
	}

	public SettlementAccount()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName							Database_FieldName          		Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,									"SourceAccount_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.PROGRAM_ID,							"Program_FK",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.ACCOUNT_TYPE_ID,						"AccountType_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.ACCOUNT_NUMBER,						"AccountNumber",					DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.DESCRIPTION,							"Description",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.WIRE_TEMPLATE_NAME,					"WireTemplateName",					DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.ABA_NUMBER,							"ABANumber",						DatabaseDataType.CHAR,				9,		false);
		super.addField(Field.SETTLEMENT_INSTITUTION_ID,				"SettlementInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}


	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
}
