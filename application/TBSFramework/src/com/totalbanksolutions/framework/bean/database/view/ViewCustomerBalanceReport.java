package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerBalanceReport extends AbstractDatabaseBean<ViewCustomerBalanceReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, ACTIVITY_AMOUNT
		, APY		
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, INTEREST_PAID_YTD
		, OFFICE_CODE
		, PRODUCT_CODE
		, RATE_PAID_IN_LAST_ACCRUAL
		, RECORD_ID
		, REP_CODE
		, SOURCE_PRODUCT_CODE
		, TOTAL_BALANCE
		, TOTAL_FEE_ACCRUED
		, TOTAL_FEE_ACCRUED_PTD
		;
	}

	public ViewCustomerBalanceReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.ACTIVITY_AMOUNT,					"Activity",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.APY,								"APY",							DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_PAID_YTD,					"InterestPaid_YTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.OFFICE_CODE,						"OfficeCode",					DatabaseDataType.CHAR,				30,		false);		
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.RATE_PAID_IN_LAST_ACCRUAL,			"RatePaidInLastAccrual",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.REP_CODE,							"RepCode",						DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceInstitutionProductCode",	DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TOTAL_BALANCE,						"TotalBalance",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFeeAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED_PTD,				"TotalFeeAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
