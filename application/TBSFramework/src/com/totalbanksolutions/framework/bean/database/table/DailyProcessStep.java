package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class DailyProcessStep extends AbstractDatabaseBean<DailyProcessStep.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessSteps";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 DATE_PATTERN
		,ERROR_REPORT_TIMEOUT
		,IS_ACTIVE
		,IS_LATE_PROCESS
		,IS_SKIPPABLE
		,IS_SKIPPED_BY_DEFAULT
		,LAST_MODIFIED_DATETIME
		,LAST_MODIFIED_USERNAME
		,PROCESS_TYPE_DETAILS
		,PROCESS_TYPE_ID
		,PROGRAM_ID
		,SCHEDULE_TYPE_ID
		,SOURCE_INST_ID
		,START_TIME
		,STEP_ID
		,STEP_ORDER
		,TIMEOUT
		,TRAILER_PATTERN
		;
	}

	public DailyProcessStep()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName					Database_FieldName				Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.DATE_PATTERN,					"DatePattern",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.ERROR_REPORT_TIMEOUT,			"ErrorReportTimeout",			DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.IS_ACTIVE,						"IsActive",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_LATE_PROCESS,				"IsLateProcess",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SKIPPABLE,					"IsSkippable",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SKIPPED_BY_DEFAULT,			"IsSkippedByDefault",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LAST_MODIFIED_DATETIME,		"LastModifiedDateTime",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.LAST_MODIFIED_USERNAME,		"LastModifiedByUserName",		DatabaseDataType.CHAR,				30,		false);		
		super.addField(Field.PROCESS_TYPE_DETAILS,			"ProcessTypeDetails",			DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.PROCESS_TYPE_ID,				"ProcessType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROGRAM_ID,					"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SCHEDULE_TYPE_ID,				"ScheduleType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_INST_ID,				"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);		
		super.addField(Field.START_TIME,					"StartTime",					DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.STEP_ID,						"Step_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.STEP_ORDER,					"StepOrder",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.TIMEOUT,						"Timeout",						DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.TRAILER_PATTERN,				"TrailerPattern",				DatabaseDataType.CHAR,				50,		false);
	}

	// Getters
	public String getDatePattern()
	{
		return this.getField(Field.DATE_PATTERN).getStringValue();
	}

	public String getErrorReportTimeout()
	{
		return this.getField(Field.ERROR_REPORT_TIMEOUT).getStringValue();
	}
	
	public Date getLastModifiedDateTime()
	{
		return this.getField(Field.LAST_MODIFIED_DATETIME).getDateValue();
	}
	
	public boolean isLateProcess()
	{
		return this.getField(Field.IS_LATE_PROCESS).getBooleanValue();
	}

	public long getProcessTypeId()
	{
		return this.getField(Field.PROCESS_TYPE_ID).getLongValue();
	}
	
	public long getProgramId()
	{
		return this.getField(Field.PROGRAM_ID).getLongValue();
	}
	
	public long getSourceInstitutionId()
	{
		return this.getField(Field.SOURCE_INST_ID).getLongValue();
	}
	
	public int getStepOrder()
	{
		return this.getField(Field.STEP_ORDER).getIntegerValue();
	}
	
	public String getStartTime()
	{
		return this.getField(Field.START_TIME).getStringValue();
	}
	
	public String getTimeout()
	{
		return this.getField(Field.TIMEOUT).getStringValue();
	}
	
	public String getTrailerPattern()
	{
		return this.getField(Field.TRAILER_PATTERN).getStringValue();
	}
	
}
