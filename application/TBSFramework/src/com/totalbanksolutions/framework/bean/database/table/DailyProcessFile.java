package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class DailyProcessFile extends AbstractDatabaseBean<DailyProcessFile.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessFiles";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 ALTERNATE_INPUT_FILEDIR
		,FILE_ACTION_TYPE_ID
		,FILE_SEQUENCE_NUMBER
		,INPUT_FILEDIR
		,INPUT_FILENAME
		,IS_CREATE_MISSING_OUT_DIR
		,OUTPUT_FILEDIR	
		,OUTPUT_FILENAME
		,PROCESS_TYPE_ID
		,PROGRAM_ID
		,SOURCE_INST_ID	
		,STEP_ID
		;
	}

	public DailyProcessFile()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName             	Database_FieldName              	Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ALTERNATE_INPUT_FILEDIR,	"AlternateInputFileDirectory",		DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.FILE_ACTION_TYPE_ID,		"FileActionType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.FILE_SEQUENCE_NUMBER,		"FileSequenceNumber",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.INPUT_FILEDIR,				"InputFileDirectory",				DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.INPUT_FILENAME,			"InputFileName",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.IS_CREATE_MISSING_OUT_DIR,	"IsCreateMissingOutputDirectory",	DatabaseDataType.BIT,				0,		false);
		super.addField(Field.OUTPUT_FILEDIR,			"OutputFileDirectory",				DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.OUTPUT_FILENAME,			"OutputFileName",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.PROCESS_TYPE_ID,			"ProcessType_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROGRAM_ID,				"Program_FK",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_INST_ID,			"SourceInstitution_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.STEP_ID,					"Step_FK",							DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		;
	}

	// Getters
	public String getAlternateInputFileDir()
	{
		return this.getField(Field.ALTERNATE_INPUT_FILEDIR).getStringValue();
	}

	public long getFileActionTypeId()
	{
		return this.getField(Field.FILE_ACTION_TYPE_ID).getLongValue();
	}
	
	public String getInputFileDir()
	{
		return this.getField(Field.INPUT_FILEDIR).getStringValue();
	}
	
	public String getInputFileName()
	{
		return this.getField(Field.INPUT_FILENAME).getStringValue();
	}
	
	public String getOutputFileDir()
	{
		return this.getField(Field.OUTPUT_FILEDIR).getStringValue();
	}
	
	public String getOutputFileName()
	{
		return this.getField(Field.OUTPUT_FILENAME).getStringValue();
	}
	
	public Boolean isCreateMissingOutputDir()
	{
		return this.getField(Field.IS_CREATE_MISSING_OUT_DIR).getBooleanValue();
	}

	// Setters
	public void setInputFileDir(String s)
	{
		this.getField(Field.INPUT_FILEDIR).setValue(s);
	}
	
	public void setInputFileName(String s)
	{
		this.getField(Field.INPUT_FILENAME).setValue(s);
	}
	
	public void setOutputFileDir(String s)
	{
		this.getField(Field.OUTPUT_FILEDIR).setValue(s);
	}
	
	public void setOutputFileName(String s)
	{
		this.getField(Field.OUTPUT_FILENAME).setValue(s);
	}

}
