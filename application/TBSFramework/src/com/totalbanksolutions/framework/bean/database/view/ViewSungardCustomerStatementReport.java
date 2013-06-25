package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewSungardCustomerStatementReport extends AbstractDatabaseBean<ViewSungardCustomerStatementReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 ACCOUNT_NUMBER	
		,BALANCE_AMOUNT
		,BANK_ADDRESS
		,BANK_CITY
		,BANK_CODE
		,BANK_NAME
		,BANK_STATE_CODE
		,BANK_ZIP_CODE
		,RECORD_ID
		;
	}

	public ViewSungardCustomerStatementReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BANK_ADDRESS,						"BankAddress",					DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.BANK_CITY,							"BankCity",						DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.BANK_CODE,							"BankCode",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				200,	false);	
		super.addField(Field.BANK_STATE_CODE,					"BankStateCode",				DatabaseDataType.CHAR,				2,		false);	
		super.addField(Field.BANK_ZIP_CODE,						"BankZipCode",					DatabaseDataType.CHAR,				5,		false);	
		super.addField(Field.RECORD_ID,							"RecordType",					DatabaseDataType.INT,				0,		false);	
	}
	
}
