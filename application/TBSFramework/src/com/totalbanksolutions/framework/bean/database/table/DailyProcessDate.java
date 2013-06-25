package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class DailyProcessDate extends AbstractDatabaseBean<DailyProcessDate.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessDate";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 CALENDAR_DATE
		,DAYS_ELAPSED_IN_MONTH
		,DAYS_ELAPSED_IN_PERIOD
		,EXTRA_ACCRUAL_DAYS
		,IS_BUSINESS_DAY
		,IS_FIRST_WEEKDAY
		,IS_HOLIDAY
		,IS_LAST_WEEKDAY
		,IS_MONTH_END
		,IS_MONTH_START
		,IS_PERIOD_END
		,IS_PERIOD_START
		,IS_STATEMENT_TEST
		,IS_WEEKEND
		,LAST_MODIFIED_DATETIME
		,LAST_MODIFIED_USERNAME
		,NEXT_BUSINESS_DATE
		,PREVIOUS_BUSINESS_DATE
		,PROGRAM_ID
		,TOTAL_DAYS_IN_MONTH
		,TOTAL_DAYS_IN_PERIOD
		;
	}

	public DailyProcessDate()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName               		Database_FieldName            Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.CALENDAR_DATE,				"CalendarDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.PROGRAM_ID,				"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.IS_BUSINESS_DAY,			"IsBusinessDay",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_FIRST_WEEKDAY,			"IsFirstDayOfWeek",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_HOLIDAY,				"IsHoliday",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_LAST_WEEKDAY,			"IsLastDayOfWeek",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_MONTH_END,				"IsMonthEnd",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_MONTH_START,			"IsMonthStart",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_PERIOD_END,				"IsPeriodEnd",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_PERIOD_START,			"IsPeriodStart",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_STATEMENT_TEST,			"IsStatementTest",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_WEEKEND,				"IsWeekend",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.PREVIOUS_BUSINESS_DATE,	"PreviousBusinessDate",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.NEXT_BUSINESS_DATE,		"NextBusinessDate",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.EXTRA_ACCRUAL_DAYS,		"ExtraDaysInAccrual",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.DAYS_ELAPSED_IN_PERIOD,	"DaysElapsedInPeriod",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.DAYS_ELAPSED_IN_MONTH,		"DaysElapsedInMonth",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.TOTAL_DAYS_IN_PERIOD,		"TotalDaysInPeriod",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.TOTAL_DAYS_IN_MONTH,		"TotalDaysInMonth",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.LAST_MODIFIED_DATETIME,	"LastModifiedDateTime",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.LAST_MODIFIED_USERNAME,	"LastModifiedByUserName",		DatabaseDataType.CHAR,				30,		false);
		;
	}

	// Getters
	public int getExtraAccrualDays()
	{
		return this.getField(Field.EXTRA_ACCRUAL_DAYS).getIntegerValue();
	}
	
	public boolean isMonthEnd()
	{
		return this.getField(Field.IS_MONTH_END).getBooleanValue();
	}
	
	public boolean isPeriodEnd()
	{
		return this.getField(Field.IS_PERIOD_END).getBooleanValue();
	}
	
	public boolean isPeriodStart()
	{
		return this.getField(Field.IS_PERIOD_START).getBooleanValue();
	}	
	
	public boolean isStatementTest()
	{
		return this.getField(Field.IS_STATEMENT_TEST).getBooleanValue();
	}

	public Date getNextBusinessDate()
	{
		return this.getField(Field.NEXT_BUSINESS_DATE).getDateValue();
	}

	public Date getPreviousBusinessDate()
	{
		return this.getField(Field.PREVIOUS_BUSINESS_DATE).getDateValue();
	}
	
}
