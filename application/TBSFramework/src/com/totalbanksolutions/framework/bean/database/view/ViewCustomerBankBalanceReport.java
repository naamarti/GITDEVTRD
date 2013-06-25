package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerBankBalanceReport extends AbstractDatabaseBean<ViewCustomerBankBalanceReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, BANK_ADDRESS
		, BANK_CODE
		, BANK_NAME
		, CASH_INTEREST_PAID
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, INTEREST_REINVESTED
		, OPT_OUT_INDICATOR
		, PRODUCT_CODE
		, RATE_PAID_IN_LAST_ACCRUAL
		, RECORD_ID
		, SOURCE_PRODUCT_CODE
		, TOTAL_BALANCE
		, TOTAL_FEE_ACCRUED
		, TOTAL_FEE_ACCRUED_PTD
		;
	}

	public ViewCustomerBankBalanceReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.BANK_ADDRESS,						"BankAddress",					DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.BANK_CODE,							"BankCode",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				200,	false);	
		super.addField(Field.CASH_INTEREST_PAID,				"InterestPaid",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_REINVESTED,				"InterestReinvested",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.OPT_OUT_INDICATOR,					"OptOutIndicator",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.RATE_PAID_IN_LAST_ACCRUAL,			"RaitPaidInLastAccrual",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceProductCode",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TOTAL_BALANCE,						"TotalBalance",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFeeAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED_PTD,				"TotalFeeAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
