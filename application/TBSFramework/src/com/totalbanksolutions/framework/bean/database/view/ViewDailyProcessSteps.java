package com.totalbanksolutions.framework.bean.database.view;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewDailyProcessSteps extends AbstractDatabaseBean<ViewDailyProcessSteps.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessSteps";
	
	public enum Field
	{
		  BATCH_ID
		, BUSINESS_DATE
		, DETAILS
		, IS_ALLOW_RUN_PROCESS
		, IS_ALLOW_SKIP
		, IS_ALLOW_STOP
		, IS_ALLOW_UNDO
		, IS_ALLOW_UNSKIP
		, IS_LATE_PROCESS
		, IS_NEXT_STEP
		, IS_SKIPPABLE
		, LAST_MODIFIED_DATETIME
		, LAST_MODIFIED_USERNAME
		, MENU_NAME
		, OPERATION_HANDLER
		, OPERATION_TYPE
		, OPERATION_TYPE_ID
		, PERCENT_COMPLETE
		, PROCESS_DESCRIPTION
		, PROCESS_STATUS	
		, PROCESS_STATUS_ID
		, PROCESS_TYPE	
		, PROCESS_TYPE_ID
		, PROGRAM_ID
		, SCHEDULE_TYPE_ID
		, SOURCE_INST_ID
		, SOURCE_INSTITUTION
		, STEP_ID
		, STEP_ORDER
		;
	}

	public ViewDailyProcessSteps()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BATCH_ID,							"BatchNumber_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.BUSINESS_DATE,						"BusinessDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.DETAILS,							"Details",						DatabaseDataType.CHAR,				500,	false);
		super.addField(Field.IS_ALLOW_RUN_PROCESS,				"IsAllowRunProcess",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_ALLOW_SKIP,						"IsAllowSkip",					DatabaseDataType.BIT,				0,		false);		
		super.addField(Field.IS_ALLOW_STOP,						"IsAllowStop",					DatabaseDataType.BIT,				0,		false);		
		super.addField(Field.IS_ALLOW_UNDO,						"IsAllowUndo",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_ALLOW_UNSKIP,					"IsAllowUnSkip",				DatabaseDataType.BIT,				0,		false);		
		super.addField(Field.IS_LATE_PROCESS,					"IsLateProcess",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_NEXT_STEP,						"IsNextStep",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SKIPPABLE,						"IsSkippable",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LAST_MODIFIED_DATETIME,			"LastModifiedDateTime",			DatabaseDataType.DATETIME2,			0,		false);
		super.addField(Field.LAST_MODIFIED_USERNAME,			"LastModifiedByUserName",		DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.MENU_NAME,							"MenuName",						DatabaseDataType.CHAR,				100,	false);
		super.addField(Field.OPERATION_HANDLER,					"OperationHandler",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.OPERATION_TYPE,					"OperationType",				DatabaseDataType.CHAR,				100,	false);
		super.addField(Field.OPERATION_TYPE_ID,					"OperationType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PERCENT_COMPLETE,					"PercentComplete",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PROCESS_DESCRIPTION,				"Description",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.PROCESS_STATUS,					"ProcessStatus",				DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.PROCESS_STATUS_ID,					"ProcessStatus_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROCESS_TYPE,						"ProcessType",					DatabaseDataType.CHAR,				100,	false);	
		super.addField(Field.PROCESS_TYPE_ID,					"ProcessType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROGRAM_ID,						"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SCHEDULE_TYPE_ID,					"ScheduleType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.SOURCE_INST_ID,					"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_INSTITUTION,				"SourceInstitution",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.STEP_ID,							"Step_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.STEP_ORDER,						"StepOrder",					DatabaseDataType.INT,				0,		false);
	}
	
	// Getters
	public long getBatchId()
	{
		return this.getField(Field.BATCH_ID).getLongValue();
	}
	
	public String getDetails()
	{
		return this.getField(Field.DETAILS).getStringValue();
	}
	
	public boolean isAllowRunProcess()
	{
		return this.getField(Field.IS_ALLOW_RUN_PROCESS).getBooleanValue();
	}

	public boolean isAllowSkip()
	{
		return this.getField(Field.IS_ALLOW_SKIP).getBooleanValue();
	}

	public boolean isAllowStop()
	{
		return this.getField(Field.IS_ALLOW_STOP).getBooleanValue();
	}

	public boolean isAllowUndo()
	{
		return this.getField(Field.IS_ALLOW_UNDO).getBooleanValue();
	}

	public boolean isAllowUnSkip()
	{
		return this.getField(Field.IS_ALLOW_UNSKIP).getBooleanValue();
	}
	
	public boolean isLateProcess()
	{
		return this.getField(Field.IS_LATE_PROCESS).getBooleanValue();
	}
	
	public boolean isNextStep()
	{
		return this.getField(Field.IS_NEXT_STEP).getBooleanValue();
	}

	public boolean isSkippable()
	{
		return this.getField(Field.IS_SKIPPABLE).getBooleanValue();
	}
	
	public Date getLastModifiedDateTime()
	{
		return this.getField(Field.LAST_MODIFIED_DATETIME).getDateValue();
	}
	
	public String getLastModifiedUserName()
	{
		return this.getField(Field.LAST_MODIFIED_USERNAME).getStringValue();
	}

	public String getMenuName()
	{
		return this.getField(Field.MENU_NAME).getStringValue();
	}
	
	public String getOperationType()
	{
		return this.getField(Field.OPERATION_TYPE).getStringValue();
	}

	public String getProcessDescription()
	{
		return this.getField(Field.PROCESS_DESCRIPTION).getStringValue();
	}

	public String getProcessStatus()
	{
		return this.getField(Field.PROCESS_STATUS).getStringValue();
	}
	
	public long getProcessStatusId()
	{
		return this.getField(Field.PROCESS_STATUS_ID).getLongValue();
	}
	
	public long getStepId()
	{
		return this.getField(Field.STEP_ID).getLongValue();
	}
	
	public int getStepOrder()
	{
		return this.getField(Field.STEP_ORDER).getIntegerValue();
	}

	// Setters
	public void setIsAllowRunProcess(boolean b)
	{
		this.getField(Field.IS_ALLOW_RUN_PROCESS).setValue(b);
	}
	
	public void setIsAllowSkip(boolean b)
	{
		this.getField(Field.IS_ALLOW_SKIP).setValue(b);
	}
	
	public void setIsAllowStop(boolean b)
	{
		this.getField(Field.IS_ALLOW_STOP).setValue(b);
	}

	public void setIsAllowUndo(boolean b)
	{
		this.getField(Field.IS_ALLOW_UNDO).setValue(b);
	}

	public void setIsAllowUnSkip(boolean b)
	{
		this.getField(Field.IS_ALLOW_UNSKIP).setValue(b);
	}

	public void setIsNextStep(boolean b)
	{
		this.getField(Field.IS_NEXT_STEP).setValue(b);
	}

}
