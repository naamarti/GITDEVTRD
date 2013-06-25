package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class DailyProcessScheduleType extends AbstractDatabaseBean<DailyProcessScheduleType.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessScheduleType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 AUTOMATED_EVERYDAY			(8)
		,AUTOMATED_FIRST_WEEKDAY	(11)
		,AUTOMATED_LAST_WEEKDAY		(12)
		,AUTOMATED_MONTH_END		(10)
		,AUTOMATED_PERIOD_END		(9)
		,AUTOMATED_PERIOD_START		(13)
		
		,MANUAL_EVERYDAY			(2)
		,MANUAL_FIRST_WEEKDAY		(5)
		,MANUAL_LAST_WEEKDAY		(6)
		,MANUAL_MONTH_END			(4)
		,MANUAL_PERIOD_END			(3)
		,MANUAL_PERIOD_START		(7)
		,ON_DEMAND					(1)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}
	
	public enum Field
	{
		 DESCRIPTION
		,SCHEDULE_TYPE
		,SCHEDULE_TYPE_ID
		;
	}

	public DailyProcessScheduleType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DESCRIPTION,			"Description",				DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.SCHEDULE_TYPE,			"ScheduleType",				DatabaseDataType.CHAR,					100,	false);
		super.addField(Field.SCHEDULE_TYPE_ID,		"Schedule_PK",				DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

}
