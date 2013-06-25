package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewBalanceSummaryAndFeesByProduct extends AbstractDatabaseBean<ViewBalanceSummaryAndFeesByProduct.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 AVERAGE_BALANCE_AMOUNT
		,BALANCE_AMOUNT
		,BLENDED_TOTAL_FEE_ACCRUED
		,BLENDED_TOTAL_FEE_ACCRUED_PTD
		,INTEREST_ACCRUED
		,INTEREST_ACCRUED_PTD
		,INTEREST_PAID_EARLY_PTD
		,PRODUCT_NAME
		,SI_NAME
		,TOTAL_FEE_ACCRUED
		,TOTAL_FEE_ACCRUED_PTD
		;
	}

	public ViewBalanceSummaryAndFeesByProduct()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.AVERAGE_BALANCE_AMOUNT,		    "AverageBalance_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);		
		super.addField(Field.BALANCE_AMOUNT,					"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BLENDED_TOTAL_FEE_ACCRUED,			"BlendedTotalFeeAccrued",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BLENDED_TOTAL_FEE_ACCRUED_PTD,		"BlendedTotalFeeAccrued_PTD",	DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_PAID_EARLY_PTD,		   	"InterestPaidEarly_PTD",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PRODUCT_NAME,						"ProductName",					DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.SI_NAME,						    "SIName",						DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED,					"TotalFeeAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_FEE_ACCRUED_PTD,				"TotalFeeAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
