package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewRBCRevenue extends AbstractDatabaseBean<ViewRBCRevenue.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER
		, AVERAGE_BALANCE_AMOUNT		
		, BANK_PAYOUT
		//, BANK_PAYOUT_SIGN
		, INTEREST_PAYOUT		
		, NUMBER_OF_RECORDS
		, PRODUCT_CODE
		, RECORD_ID
		, SERVICE_FEE_ACCRUED		
		, SOURCE_FEE_ACCRUED		
		, TOTAL_FEE_ACCRUED	
		;
	}
	
	public ViewRBCRevenue()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.AVERAGE_BALANCE_AMOUNT,			"AverageBalance",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BANK_PAYOUT,						"BankPayout",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);		
		super.addField(Field.INTEREST_PAYOUT,					"Interest",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.NUMBER_OF_RECORDS,					"Count",						DatabaseDataType.INT,				0,		false);
		super.addField(Field.PRODUCT_CODE,						"AccountProductCode",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.RECORD_ID,							"Record_ID",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.SERVICE_FEE_ACCRUED,				"ServiceFee",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SOURCE_FEE_ACCRUED,				"SourceFee",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFee",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}	

}
