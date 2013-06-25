package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewSourceInstitutionBalanceReport extends AbstractDatabaseBean<ViewSourceInstitutionBalanceReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACTIVITY
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, INTEREST_YIELD
		, RECORD_ID
		, SERVICE_FEE
		, SERVICE_FEE_PTD
		, SOURCE_INSTITUTION_NAME
		, TOTAL_FEE_ACCRUED
		, TOTAL_FEE_ACCRUED_PTD
		;
	}

	public ViewSourceInstitutionBalanceReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACTIVITY,							"Activity",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_YIELD,					"InterestYield",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.SERVICE_FEE,						"ServiceFee",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SERVICE_FEE_PTD,					"ServiceFee_PTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SOURCE_INSTITUTION_NAME,			"SourceInstitutionName",		DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFeeAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED_PTD,				"TotalFeeAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
