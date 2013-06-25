package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class DailyProcessOperationType extends AbstractDatabaseBean<DailyProcessOperationType.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessOperationType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	public enum Values
	{
		 DMS_PROCESS				(1)
		,FILE_IO					(2)
		,FILE_LOADER				(3)
		,FILE_WRITER				(4)
		,MANUAL_CHECKOFF			(5)
		,NAVIGATE_TO_MENU			(6)
		,REPORT_WRITER				(7)
		,EMAIL_SENDER				(8)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}
	
	public enum Field
	{
		 DESCRIPTION
		,OPERATION_HANDLER
		,OPERATION_TYPE
		,OPERATION_TYPE_ID
		;
	}

	public DailyProcessOperationType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DESCRIPTION,			"Description",				DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.OPERATION_HANDLER,		"OperationHandler",			DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.OPERATION_TYPE,		"OperationType",			DatabaseDataType.CHAR,					100,	false);
		super.addField(Field.OPERATION_TYPE_ID,		"OperationType_PK",			DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

}
