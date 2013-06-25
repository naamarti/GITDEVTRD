package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  04 May 2011 VC #877: Forecast Waterfall method
 *			  23 Apr 2012 VC #1449: Forecast Report - display new column "Balances after Insured Pass"
 *			  23 Apr 2012 VC #1495: Forecast - separate waterfall forecast & generate reports steps from normal forecast
 *
 * =================================================================================================
 */
public class ViewForecastBalancesReport extends AbstractDatabaseBean<ViewForecastBalancesReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";

	public enum Field
	{
		  BALANCE_ALLOCATED_PASS1
		, BALANCE_ALLOCATED_PASS2
		, BALANCE_ALLOCATED_PASS3
		, BALANCE_ALLOCATED_PASS4
		, BALANCE_ALLOCATED_PASS5
		, BANK_NAME
		, FORECAST_ACTIVITY
		, FORECAST_CAPACITY
		, FORECAST_ENDING_BALANCE
		, FORECAST_TARGET_BALANCE
		, OPENING_BALANCE
		;
	}

	public ViewForecastBalancesReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BALANCE_ALLOCATED_PASS1,			"BalanceAllocatedPass1",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.BALANCE_ALLOCATED_PASS2,			"BalanceAllocatedPass2",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.BALANCE_ALLOCATED_PASS3,			"BalanceAllocatedPass3",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.BALANCE_ALLOCATED_PASS4,			"BalanceAllocatedPass4",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.BALANCE_ALLOCATED_PASS5,			"BalanceAllocatedPass5",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				24,		false);	
		super.addField(Field.FORECAST_ACTIVITY,					"ForecastActivity",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.FORECAST_ENDING_BALANCE,			"ForecastEndingBalance",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.FORECAST_TARGET_BALANCE,			"ForecastTarget",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.FORECAST_CAPACITY,					"ForecastCapacity",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.OPENING_BALANCE,					"OpeningBalance",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	

	}
	
}
