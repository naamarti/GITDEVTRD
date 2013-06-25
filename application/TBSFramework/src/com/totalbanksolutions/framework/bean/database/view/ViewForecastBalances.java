package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewForecastBalances extends AbstractDatabaseBean<ViewForecastBalances.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";

	public enum Field
	{
		  BANK_NAME
		, FORECAST_ACTIVITY
		, FORECAST_ENDING_BALANCE
		, MAX_BALANCE
		, OPENING_BALANCE
		, TARGET_BALANCE
		;
	}

	public ViewForecastBalances()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				24,		false);	
		super.addField(Field.FORECAST_ACTIVITY,					"ForecastActivity",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.FORECAST_ENDING_BALANCE,			"ForecastEndingBalance",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.MAX_BALANCE,						"Max",							DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.OPENING_BALANCE,					"OpeningBalance",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.TARGET_BALANCE,					"Target",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	

	}

	// Getters
	public String getBankName()
	{
		return this.getField(Field.BANK_NAME).getStringValue();
	}
	
	public double getMaxBalance()
	{
		return this.getField(Field.MAX_BALANCE).getDoubleValue();
	}
	
	public double getOpeningBalance()
	{
		return this.getField(Field.OPENING_BALANCE).getDoubleValue();
	}
	
}
