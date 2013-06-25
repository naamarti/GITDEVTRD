package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewDBTCAFees extends AbstractDatabaseBean<ViewDBTCAFees.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_DESCRIPTION
		, ACCOUNT_NUMBER
		, AVERAGE_BALANCE_PTD
		, BALANCE
		, OFFICE_CODE
		, SI_FEE_ACCRUED_PTD
		, SI_FEE_BASIS_POINTS
		, SOURCE_PRODUCT_CODE
		, TOTAL_FEE_PAID_AT_PE
		;
	}
	
	public ViewDBTCAFees()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName						Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_DESCRIPTION,		"AcctDescription",						DatabaseDataType.CHAR,				10,		false);	
		super.addField(Field.ACCOUNT_NUMBER,			"AccountNumber",						DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.AVERAGE_BALANCE_PTD,		"AverageBalance_PTD",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE,					"Balance",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.OFFICE_CODE,				"OfficeCode",							DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.SI_FEE_ACCRUED_PTD,		"SourceInstitutionFeeAccrued_PTD",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SI_FEE_BASIS_POINTS,		"SIFeeBasisPoints",						DatabaseDataType.INT,				0,		false);
		super.addField(Field.SOURCE_PRODUCT_CODE,		"SourceProductCode",					DatabaseDataType.CHAR,				12,		false);	
		super.addField(Field.TOTAL_FEE_PAID_AT_PE,		"TotalFeePaid_AtPeriodEnd",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);

	}	
}
