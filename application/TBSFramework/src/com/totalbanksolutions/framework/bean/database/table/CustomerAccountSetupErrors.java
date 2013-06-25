package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class CustomerAccountSetupErrors extends AbstractDatabaseBean<CustomerAccountSetupErrors.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccount_SetupErrors";

	public enum Field
	{
		 ACCOUNT_ERRORS_ID
		,ACCOUNT_NUMBER
		,FILE_DATE
		,INVALID_FIELD_NAME
		,INVALID_ORIGINAL_VALUE
		,INVALID_REASON_DESC
		,LAST_MODIFIED_DATETIME
		,PRODUCT_ID
		,PROGRAM_ID
		,SOURCE_INSTITUTION_ID
		;
	}

	public CustomerAccountSetupErrors()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_ERRORS_ID,					"AccountErrors_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.FILE_DATE,							"FileDate",						DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.INVALID_FIELD_NAME,				"InvalidFieldName",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.INVALID_ORIGINAL_VALUE,			"InvalidOriginalValue",			DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.INVALID_REASON_DESC,				"InvalidReasonDesc",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.LAST_MODIFIED_DATETIME,			"LastModifiedDateTime",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.PRODUCT_ID,						"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROGRAM_ID,						"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,				"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

	// Getters
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}
	
}
