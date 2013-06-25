package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewDailyBankBalancesByProduct extends AbstractDatabaseBean<ViewDailyBankBalancesByProduct.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  BALANCE_AMOUNT
		, BALANCE_DATE
		, PRODUCT_NAME
	   ;
	}
	
	public ViewDailyBankBalancesByProduct()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName						Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BALANCE_AMOUNT,			"Balance",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	

		for(int i = 1; i <= 50; i++)
		{
			super.addField("BALANCE_BANK"+i,			"Balance_Bank"+i,						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		}
		
		super.addField(Field.BALANCE_DATE,				"BalanceDate",							DatabaseDataType.DATETIME,			0,		false);	
		super.addField(Field.PRODUCT_NAME,				"ProductName",							DatabaseDataType.CHAR,				50,		false);
	}	

}
