package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewAccountRegistrationError extends AbstractDatabaseBean<ViewAccountRegistrationError.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 ACCOUNT_NUMBER	
		,INVALID_REASON_DESC
		,RECORD_ID
		,SOURCE_PRODUCT_CODE
		;
	}

	public ViewAccountRegistrationError()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.INVALID_REASON_DESC,				"InvalidReasonDesc",			DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceProductCode",			DatabaseDataType.CHAR,				50,		false);	
	}

	// Getters
	
	
}
