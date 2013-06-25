package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 * Modified: Oct 4,2011 NAM: #932 - add TransactionExceptionType & RequestedAmount for transaction deletion functionality
 */
public class ViewCustomerAccountTransaction extends AbstractDatabaseBean<ViewCustomerAccountTransaction.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccount_Transaction";
	
	public enum Field
	{
		  ACCOUNT_NUMBER
		, REQUESTED_AMOUNT
		, CONFIRMED_AMOUNT
		, DEBIT_CREDIT_IND
		, ENTRY_DATE
		, IS_MANUAL
		, IS_SEND_BACK_CONFIRM
		, PRODUCT_CODE
		, PRODUCT_ID
		, TRANSACTION_ID 
		, TRANSACTION_TYPE
		, TRANSACTION_EXCEPTION_TYPE
		, CODE
		, FUND_NUM
		, TIER_CODE
		;
	}

	public ViewCustomerAccountTransaction()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.REQUESTED_AMOUNT,					"RequestedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.CONFIRMED_AMOUNT,					"ConfirmedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.DEBIT_CREDIT_IND,					"DebitCreditInd",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.ENTRY_DATE,						"EntryDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.IS_MANUAL,							"IsManual",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SEND_BACK_CONFIRM,				"IsSendBackConfirm",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PRODUCT_ID,						"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.TRANSACTION_ID,					"Transaction_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true); 
		super.addField(Field.TRANSACTION_TYPE,					"TransactionType",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TRANSACTION_EXCEPTION_TYPE,		"TransactionExceptionType_FK",	DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.CODE,								"Code",							DatabaseDataType.CHAR,				4,		false);
		super.addField(Field.FUND_NUM,							"FundNum",						DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.TIER_CODE,							"TierCode",						DatabaseDataType.CHAR,				10,		false);		
		
	}

	// Getters

}
