package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewPershingBankFees extends AbstractDatabaseBean<ViewPershingBankFees.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER
		, AVERAGE_BALANCE
		, BALANCE_AMOUNT
		, CUSTOMER_PAYMENT_AMOUNT
		, IBD_CODE
		, IBD_PAYMENT_AMOUNT
		, PERSHING_FEE
		, REP_NUMBER
		, SOURCE_PRODUCT_CODE
		, TIER
		;
	}

	public ViewPershingBankFees()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.AVERAGE_BALANCE,					"AverageBalance",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_AMOUNT,					"BalanceAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.CUSTOMER_PAYMENT_AMOUNT,			"CustomerPaymentAmount",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.IBD_CODE,							"IBDCode",						DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.IBD_PAYMENT_AMOUNT,				"IBDPaymentAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PERSHING_FEE,						"PershingFee",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.REP_NUMBER,						"RepNumber",					DatabaseDataType.CHAR,				3,		false);
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceProductCode",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.TIER,								"Tier",							DatabaseDataType.INT,				0,		false);
	}
	
}
