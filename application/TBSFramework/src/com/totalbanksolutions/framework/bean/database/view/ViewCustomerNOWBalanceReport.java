package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerNOWBalanceReport extends AbstractDatabaseBean<ViewCustomerNOWBalanceReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, BALANCE_MMDA_AMOUNT
		, BALANCE_NOW_AMOUNT		
		, CASH_INTEREST_PAID
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, INTEREST_ACCRUED4DIGIT
		, INTEREST_PAID_EARLY_PTD
		, INTEREST_REINVESTED
		, OVER_FDIC_SSN1
		, OVER_FDIC_SSN2
		, PAYOUT_TYPE
		, PRODUCT_CODE
		, RATE_PAID_IN_LAST_ACCRUAL
		, SECURITY_NUMBER
		, SOURCE_ACCOUNT_TYPE
		, SOURCE_PRODUCT_CODE
		, TAXID
		, TAXID2
		;
	}

	public ViewCustomerNOWBalanceReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.BALANCE_MMDA_AMOUNT,				"Balance_MMDA",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_NOW_AMOUNT,				"Balance_NOW",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.CASH_INTEREST_PAID,				"InterestPaid",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED4DIGIT,			"InterestAccrued4Digit",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_PAID_EARLY_PTD,			"InterestPaidEarly_PTD",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_REINVESTED,				"InterestReinvested",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.OVER_FDIC_SSN1,					"OverFDIC_SSN1",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.OVER_FDIC_SSN2,					"OverFDIC_SSN2",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PAYOUT_TYPE,						"PayoutType",					DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.RATE_PAID_IN_LAST_ACCRUAL,			"RatePaidInLastAccrual",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SECURITY_NUMBER,					"SecurityNumber",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SOURCE_ACCOUNT_TYPE,				"SourceAccountType",			DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceProductCode",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAXID,								"TaxID",						DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.TAXID2,							"TaxID2",						DatabaseDataType.CHAR,				40,		false);	
	}

}
