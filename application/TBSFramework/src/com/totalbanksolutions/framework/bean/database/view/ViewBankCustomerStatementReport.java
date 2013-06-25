package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 * Modified: 04 May 2011 VC #866: Statement file for Androscoggin producing incorrect address lines
 *           30 Dec 2011 NM	#1131: dynamic registration lines for dbtca statements
 */
public class ViewBankCustomerStatementReport extends AbstractDatabaseBean<ViewBankCustomerStatementReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 ACCOUNT_ADDRESS1
		,ACCOUNT_ADDRESS2
	/*	,ACCOUNT_ADDRESS3
		,ACCOUNT_ADDRESS4
		,ACCOUNT_ADDRESS5
		,ACCOUNT_ADDRESS6
		,ACCOUNT_ADDRESS7
		,ACCOUNT_ADDRESS8 */
		,ACCOUNT_NUMBER	
		,AVERAGE_BALANCE_MTD
		,BALANCE_AMOUNT
		,BALANCE_DATE
		,BANK_ADDRESS
		,BANK_CODE
		,BANK_NAME
		,DAYS_IN_STATEMENT
		,INTEREST_ACCRUED_MTD
		,INTEREST_ACCRUED_PTD
		,INTEREST_ACCRUED_YTD
		,INTEREST_REINVESTED_MTD
		,INTEREST_REINVESTED_YTD
		,MONTH_START_DATE		
		,PREVIOUS_BALANCE_AMOUNT
		,PREVIOUS_MONTHEND_DATE
		,PRODUCT_CODE
		,RATE_APY_MTD
		,RATE_APY_PTD
		,RATE_MTD
		,RECORD_ID
		,SI_NAME
		,TOTAL_AMOUNT
		,TRANSACTION_AMOUNT
		,TRANSACTION_DATE
		,TRANSACTION_TYPE
		;
	}

	public ViewBankCustomerStatementReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_ADDRESS1,					"AccountAddress1",				DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.ACCOUNT_ADDRESS2,					"AccountAddress2",				DatabaseDataType.CHAR,				50,		false);	
		super.addField("ACCOUNT_ADDRESS3",					"AccountAddress3",				DatabaseDataType.CHAR,				50,		false);	
		super.addField("ACCOUNT_ADDRESS4",					"AccountAddress4",				DatabaseDataType.CHAR,				50,		false);	
		super.addField("ACCOUNT_ADDRESS5",					"AccountAddress5",				DatabaseDataType.CHAR,				50,		false);	
		super.addField("ACCOUNT_ADDRESS6",					"AccountAddress6",				DatabaseDataType.CHAR,				50,		false);	
		super.addField("ACCOUNT_ADDRESS7",					"AccountAddress7",				DatabaseDataType.CHAR,				50,		false);	
		super.addField("ACCOUNT_ADDRESS8",					"AccountAddress8",				DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.AVERAGE_BALANCE_MTD,				"AverageBalance_MTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);		
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.BANK_ADDRESS,						"BankAddress",					DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.BANK_CODE,							"BankCode",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				200,	false);	
		super.addField(Field.DAYS_IN_STATEMENT,					"DaysInStatement",				DatabaseDataType.INT,				0,		false);	
		super.addField(Field.INTEREST_ACCRUED_MTD,				"InterestAccrued_MTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_YTD,				"InterestAccrued_YTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_REINVESTED_MTD,			"InterestReinvested_MTD",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_REINVESTED_YTD,			"InterestReinvested_YTD",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.MONTH_START_DATE,					"MonthStartDate",				DatabaseDataType.DATETIME,			0,		false);		
		super.addField(Field.PREVIOUS_BALANCE_AMOUNT,			"PreviousBalanceAmount",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PREVIOUS_MONTHEND_DATE,			"PreviousMonthEndDate",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.RATE_APY_MTD,						"RateAPY_MTD",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RATE_APY_PTD,						"RatePaidInLastAccrual",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RATE_MTD,							"Rate_MTD",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RECORD_ID,							"RecordType",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.SI_NAME,							"SIName",						DatabaseDataType.CHAR,				25,		false);
		super.addField(Field.TOTAL_AMOUNT,						"TotalAmount",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TRANSACTION_AMOUNT,				"TransactionAmount",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TRANSACTION_DATE,					"TransactionDate",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.TRANSACTION_TYPE,					"TransactionType",				DatabaseDataType.CHAR,				8,		false);	
	}

}
