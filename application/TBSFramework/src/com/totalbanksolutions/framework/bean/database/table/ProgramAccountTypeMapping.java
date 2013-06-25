package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ProgramAccountTypeMapping extends AbstractDatabaseBean<ProgramAccountTypeMapping.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Program_AccountType_Mapping";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 ACCOUNT_TYPE
		,ACCOUNT_TYPE_ID
		,DESCRIPTION
		,MAPPING_ID
		,PROGRAM_ID
		,SOURCE_ACCOUNT_TYPE
		,SOURCE_INST_ID
		;
	}

	public ProgramAccountTypeMapping()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_TYPE,				"AccountType",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCOUNT_TYPE_ID,			"CustomerAccountType_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.DESCRIPTION,				"Description",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.MAPPING_ID,				"Mapping_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.PROGRAM_ID,				"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_ACCOUNT_TYPE,		"SourceAccountType",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.SOURCE_INST_ID,			"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

}
