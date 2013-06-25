package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class DailyProcessBatch extends AbstractDatabaseBean<DailyProcessBatch.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessBatch";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 BATCH_ID
		,BATCH_SEQUENCE_NUMBER
		,BUSINESS_DATE
		,DETAILS
		,FILEHEADER_TIMESTAMP
		,IS_LATE_PROCESS
		,LAST_MODIFIED_DATETIME
		,LAST_MODIFIED_USERNAME
		,PROCESS_STATUS_ID
		,PROCESS_TYPE_ID
		,PROGRAM_ID
		,SOURCE_INST_ID
		,STEP_ORDER
		,TOTAL_RECORDS		
		;
	}

	public DailyProcessBatch()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName               		Database_FieldName            Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.BATCH_ID,					"BatchNumber_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.BATCH_SEQUENCE_NUMBER,		"BatchSequenceNumber",			DatabaseDataType.INT,				0,		false);		
		super.addField(Field.BUSINESS_DATE,				"BusinessDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.DETAILS,					"Details",						DatabaseDataType.CHAR,				500,	false);
		super.addField(Field.FILEHEADER_TIMESTAMP,		"FileHeaderTimestamp",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.IS_LATE_PROCESS,			"IsLateProcess",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LAST_MODIFIED_DATETIME,	"LastModifiedDateTime",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.LAST_MODIFIED_USERNAME,	"LastModifiedByUserName",		DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PROCESS_STATUS_ID,			"ProcessStatus_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROCESS_TYPE_ID,			"ProcessType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROGRAM_ID,				"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_INST_ID,			"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.STEP_ORDER,				"StepOrder",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.TOTAL_RECORDS,				"TotalRecords",					DatabaseDataType.INT,				0,		false);		
	}
	
	// Getters
	
	// Setters
	public void setBatchSequenceNumber(int n)
	{
		this.getField(Field.BATCH_SEQUENCE_NUMBER).setValue(n);
	}

	public void setBusinessDate(Date d)
	{
		this.getField(Field.BUSINESS_DATE).setValue(d);
	}
	
	public void setDetails(String s)
	{
		this.getField(Field.DETAILS).setValue(s);
	}
	
	public void setIsLateProcess(boolean b)
	{
		this.getField(Field.IS_LATE_PROCESS).setValue(b);		
	}
	
	public void setProcessStatusId(long id)
	{
		this.getField(Field.PROCESS_STATUS_ID).setValue(id);
	}
	
	public void setProcessTypeId(long id)
	{
		this.getField(Field.PROCESS_TYPE_ID).setValue(id);
	}
	
	public void setProgramId(long id)
	{
		this.getField(Field.PROGRAM_ID).setValue(id);
	}
	
	public void setSourceInstitutionId(long id)
	{
		this.getField(Field.SOURCE_INST_ID).setValue(id);
	}
	
	public void setStepOrder(int n)
	{
		this.getField(Field.STEP_ORDER).setValue(n);
	}
	
	public void setUserName(String s)
	{
		this.getField(Field.LAST_MODIFIED_USERNAME).setValue(s);
	}

}
