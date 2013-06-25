package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerStatementReport extends AbstractDatabaseBean<ViewCustomerStatementReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER
		, ACTIVITY_AMOUNT
		, APY		
		, AVERAGE_BALANCE_MTD
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, BANK_ADDRESS
		, BANK_CITY
		, BANK_CODE
		, BANK_NAME
		, BANK_STATE_CODE
		, BANK_ZIP_CODE
		, CASH_INTEREST_PAID
		, DAYS_IN_MONTH
		, DAYS_IN_PERIOD
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, INTEREST_PAID_YTD
		//, INTEREST_REINVESTED
		, MAILING_ADDRESS_ID
		, OFFICE_CODE
		, OPT_OUT_INDICATOR
		, PRODUCT_CODE
		, RATE_APY_MRA_PTD
		, RATE_PAID_APR_STMT
		, RATE_PAID_APY_STMT		
		, RATE_PAID_IN_LAST_ACCRUAL
		, REP_CODE
		, RECORD_ID
		, RECORD_ID2
		, SOURCE_PRODUCT_CODE
		, TOTAL_AMOUNT
		, TOTAL_FEE_ACCRUED
		, TOTAL_FEE_ACCRUED_PTD
		;
	}

	public ViewCustomerStatementReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.ACTIVITY_AMOUNT,					"Activity",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.APY,								"APY",							DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AVERAGE_BALANCE_MTD,				"AverageBalance_MTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.BANK_ADDRESS,						"BankAddress",					DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.BANK_CITY,							"BankCity",						DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.BANK_CODE,							"BankCode",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				200,	false);	
		super.addField(Field.BANK_STATE_CODE,					"BankStateCode",				DatabaseDataType.CHAR,				2,		false);	
		super.addField(Field.BANK_ZIP_CODE,						"BankZipCode",					DatabaseDataType.CHAR,				5,		false);	
		super.addField(Field.CASH_INTEREST_PAID,				"InterestPaid",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.DAYS_IN_MONTH,						"DaysInMonth",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.DAYS_IN_PERIOD,					"DaysInPeriod",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_PAID_YTD,					"InterestPaid_YTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.MAILING_ADDRESS_ID,				"MailingAddress_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.OFFICE_CODE,						"OfficeCode",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.OPT_OUT_INDICATOR,					"OptOutIndicator",				DatabaseDataType.CHAR,				40,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.RATE_APY_MRA_PTD,					"RateAPY_MRA_PTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RATE_PAID_APR_STMT,				"Rate_MTD",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RATE_PAID_APY_STMT,				"RateAPY_MTD",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);		
		super.addField(Field.RATE_PAID_IN_LAST_ACCRUAL,			"RatePaidInLastAccrual",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RECORD_ID,							"RecordType",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.RECORD_ID2,						"RecordID",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.REP_CODE,							"RepCode",						DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceInstitutionProductCode",	DatabaseDataType.CHAR,				50,		false);		
		super.addField(Field.TOTAL_AMOUNT,						"TotalAmount",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFeeAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED_PTD,				"TotalFeeAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		
	}
	
}
