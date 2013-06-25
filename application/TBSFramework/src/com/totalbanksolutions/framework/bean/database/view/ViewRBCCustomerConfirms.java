package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewRBCCustomerConfirms extends AbstractDatabaseBean<ViewRBCCustomerConfirms.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, APYE
		, CONFIRMED_AMOUNT
		, DEBIT_CREDIT_IND
		, END_MRA_DATE
		, ENTRY_DATE
		, IS_MANUAL
		, IS_SEND_BACK_CONFIRM
		, PRODUCT_CODE
		, PRODUCT_ID
		, START_MRA_DATE
		, TRANSACTION_ID 
		, TRANSACTION_TYPE
		;
	}

	public ViewRBCCustomerConfirms()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.APYE,								"RateAPY_MRA_PTD",				DatabaseDataType.DECIMAL_AMOUNT,	1,		false);
		super.addField(Field.CONFIRMED_AMOUNT,					"ConfirmedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.DEBIT_CREDIT_IND,					"DebitCreditInd",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.END_MRA_DATE,						"EndDate_MRA",					DatabaseDataType.DATETIME,			1,		false);
		super.addField(Field.ENTRY_DATE,						"EntryDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.IS_MANUAL,							"IsManual",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SEND_BACK_CONFIRM,				"IsSendBackConfirm",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PRODUCT_ID,						"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.START_MRA_DATE,					"StartDate_MRA",				DatabaseDataType.DATETIME,			1,		false);
		super.addField(Field.TRANSACTION_ID,					"Transaction_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true); 
		super.addField(Field.TRANSACTION_TYPE,					"TransactionType",				DatabaseDataType.CHAR,				30,		false);
	}
}
