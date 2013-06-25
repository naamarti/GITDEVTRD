package com.totalbanksolutions.framework.bean.database.view;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewStatementDates extends AbstractDatabaseBean<ViewStatementDates.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  MONTH_ACCRUAL_END_DATE
		, MONTH_BUSINESS_END_DATE
		, MONTH_DAYS
		, MONTH_DAYS_WITH_ACCRUAL
		, MONTH_START_DATE
		, PERIOD_ACCRUAL_END_DATE
		, PERIOD_BUSINESS_END_DATE
		, PERIOD_DAYS
		, PERIOD_DAYS_WITH_ACCRUAL
		, PERIOD_START_DATE
		;
	}

	public ViewStatementDates()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.MONTH_ACCRUAL_END_DATE,			"MonthAccrualEndDate",			DatabaseDataType.DATETIME,			0,		false);	
		super.addField(Field.MONTH_BUSINESS_END_DATE,			"MonthBusinessEndDate",			DatabaseDataType.DATETIME,			0,		false);	
		super.addField(Field.MONTH_DAYS,						"MonthDays",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.MONTH_DAYS_WITH_ACCRUAL,			"MonthDaysWithAccrual",			DatabaseDataType.INT,				0,		false);	
		super.addField(Field.MONTH_START_DATE,					"MonthStartDate",				DatabaseDataType.DATETIME,			0,		false);	
		super.addField(Field.PERIOD_ACCRUAL_END_DATE,			"PeriodAccrualEndDate",			DatabaseDataType.DATETIME,			0,		false);	
		super.addField(Field.PERIOD_BUSINESS_END_DATE,			"PeriodBusinessEndDate",		DatabaseDataType.DATETIME,			0,		false);	
		super.addField(Field.PERIOD_DAYS,						"PeriodDays",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.PERIOD_DAYS_WITH_ACCRUAL,			"PeriodDaysWithAccrual",		DatabaseDataType.INT,				0,		false);	
		super.addField(Field.PERIOD_START_DATE,					"PeriodStartDate",				DatabaseDataType.DATETIME,			0,		false);	
	}
	
	// Getters
	public Date getMonthAccrualEndDate()
	{
		return this.getField(Field.MONTH_ACCRUAL_END_DATE).getDateValue();
	}
	
	public Date getMonthBusinessEndDate()
	{
		return this.getField(Field.MONTH_BUSINESS_END_DATE).getDateValue();
	}
	
	public int getMonthDays()
	{
		return this.getField(Field.MONTH_DAYS).getIntegerValue();
	}

	public int getMonthDaysWithAccrual()
	{
		return this.getField(Field.MONTH_DAYS_WITH_ACCRUAL).getIntegerValue();
	}
	
	public Date getMonthStartDate()
	{
		return this.getField(Field.MONTH_START_DATE).getDateValue();
	}

	public Date getPeriodAccrualEndDate()
	{
		return this.getField(Field.PERIOD_ACCRUAL_END_DATE).getDateValue();
	}
	
	public Date getPeriodBusinessEndDate()
	{
		return this.getField(Field.PERIOD_BUSINESS_END_DATE).getDateValue();
	}
	
	public int getPeriodDays()
	{
		return this.getField(Field.PERIOD_DAYS).getIntegerValue();
	}

	public int getPeriodDaysWithAccrual()
	{
		return this.getField(Field.PERIOD_DAYS_WITH_ACCRUAL).getIntegerValue();
	}
	
	public Date getPeriodStartDate()
	{
		return this.getField(Field.PERIOD_START_DATE).getDateValue();
	}
	
}
