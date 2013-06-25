package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewProgramAccountTypeMapping extends AbstractDatabaseBean<ViewProgramAccountTypeMapping.Field> {

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_TYPE
		, ACCOUNT_TYPE_ID
		, DESCRIPTION
		, MAPPING_ID
		, PROGRAM_ID
		, SOURCE_ACCOUNT_TYPE
		, SOURCE_INST_ID
		;
		
	}

	public ViewProgramAccountTypeMapping()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_TYPE,				"AccountType",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCOUNT_TYPE_ID,			"CustomerAccountType_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.DESCRIPTION,				"Description",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.MAPPING_ID,				"Mapping_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.PROGRAM_ID,				"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_ACCOUNT_TYPE,		"SourceAccountType",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.SOURCE_INST_ID,			"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

	// Getters
	public String getAccountType()
	{
		return this.getField(Field.ACCOUNT_TYPE).getStringValue();
	}

	public String getSourceAccountType()
	{
		return this.getField(Field.SOURCE_ACCOUNT_TYPE).getStringValue();
	}
	
}
