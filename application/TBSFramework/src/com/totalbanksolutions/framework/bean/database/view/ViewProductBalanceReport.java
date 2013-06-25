package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewProductBalanceReport extends AbstractDatabaseBean<ViewProductBalanceReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACTIVITY_AMOUNT
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, PRODUCT_CODE
		, PRODUCT_RATE
		, RECORD_ID
		, TOTAL_FEE_ACCRUED
		, TOTAL_FEE_ACCRUED_PTD
		;
	}

	public ViewProductBalanceReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACTIVITY_AMOUNT,					"Activity",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PRODUCT_RATE,						"ProductRate",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFeeAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED_PTD,				"TotalFeeAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
