package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerConfirmReport extends AbstractDatabaseBean<ViewCustomerConfirmReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, CONFIRMED_AMOUNT
		, DEBIT_CREDIT_IND
		, ENTRY_DATE
		, EFFECTIVE_DATE
		, FUND_NUM
		, IS_DELETED
		, INDICATOR_CODE
		, INTEREST_PAID_APR
		, IS_FULL_WITHDRAWAL
		, IS_MANUAL
		, PRODUCT_CODE
		, RECORD_ID
		, REJECT_CODE
		, REQUESTED_AMOUNT
		, SOURCE_PRODUCT_CODE
		, SOURCE_REF_NUMBER
		, TRANSACTION_CODE
		, TRANSACTION_STATUS
		, TRANSACTION_TYPE_ID
		, TRANSACTION_TYPE
		, UPDATE_IND
		;
		  

		  
		  
	}

	public ViewCustomerConfirmReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.CONFIRMED_AMOUNT,					"ConfirmedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.DEBIT_CREDIT_IND,					"DebitCreditInd",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.ENTRY_DATE,						"EntryDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.EFFECTIVE_DATE,					"EffectiveDate",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.FUND_NUM,							"FundNum",						DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.INTEREST_PAID_APR,					"InterestPaidAPR",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INDICATOR_CODE,					"IndicatorCode",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.IS_DELETED,						"IsDeleted",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_FULL_WITHDRAWAL,				"IsFullWithdrawal",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_MANUAL,							"IsManual",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.REJECT_CODE,						"RejectCode",					DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.PRODUCT_CODE,						"Product",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.CHAR,   			10,		false);	
		super.addField(Field.REQUESTED_AMOUNT,					"RequestedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceInstitutionProductCode",	DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SOURCE_REF_NUMBER,					"SourceReferenceNumber",		DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.TRANSACTION_CODE,					"TransactionCode",				DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.TRANSACTION_STATUS,				"TransactionStatus",			DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.TRANSACTION_TYPE_ID,				"TransactionType_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.TRANSACTION_TYPE,					"TransactionType",				DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.UPDATE_IND,						"UpdateInd",					DatabaseDataType.CHAR,				1,		false);
	}
	
}
