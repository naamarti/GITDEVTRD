package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 */
public class EMS_Server_ReportsToSend extends AbstractDatabaseBean<EMS_Server_ReportsToSend.Field>
{	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "EMS_Server_ReportsToSend";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 BATCH_NUMBER_ID
		,DEPOSIT_INSTITUTION_ID
		,ENVELOPE_ID
		,PROGRAM_ID
		,REPORT_FILE_FULL_PATH
		,SOURCE_INSTITUTION_ID
		;
	}

	public EMS_Server_ReportsToSend()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName             	Database_FieldName              	Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.BATCH_NUMBER_ID,			"BatchNumber_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.DEPOSIT_INSTITUTION_ID,	"DepositInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.ENVELOPE_ID,				"Envelope_FK",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROGRAM_ID,				"Program_FK",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.REPORT_FILE_FULL_PATH,		"ReportFileFullPath",				DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.SOURCE_INSTITUTION_ID,		"SourceInstitution_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		;
	}
	
}
