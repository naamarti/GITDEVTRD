package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class DailyProcessFileActionType extends AbstractDatabaseBean<DailyProcessFileActionType.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessFileActionType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 DIR_COPY								(4)
		,DIR_COPY_STRUCTURE_ONLY				(11)
		,DIR_CREATE								(7)
		,DIR_DELETE								(6)
		,DIR_MOVE								(5)
		,FILE_APPEND							(12)
		,FILE_COPY								(8)
		,FILE_COPY_WITH_RENAME					(1)
		,FILE_DELETE							(3)
		,FILE_MOVE								(9)
		,FILE_MOVE_WITH_RENAME					(2)
		,FILE_COPY_WITH_RENAME_VALIDATE_SIZE 	(16)
		,NONE									(0)
		,WAIT_UNTIL_FILE_EXISTS					(10)
		,FTP_FILE_REQUEST_GET					(13)
		,FTP_FILE_REQUEST_SEND					(18)
		,FTP_FILE_CONFIRM_SEND					(17)
		,VALIDATE_FILE_MOVE_WITH_RENAME			(14)
		,FILE_COPY_WITH_RENAME_NO_FAIL			(15)
		,DIR_COPY_NO_FAIL						(19)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}
	
	public enum Field
	{
		 DESCRIPTION
		,FILE_ACTION_TYPE
		,FILE_ACTION_TYPE_ID
		;
	}

	public DailyProcessFileActionType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DESCRIPTION,				"Description",				DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.FILE_ACTION_TYPE,			"FileActionType",			DatabaseDataType.CHAR,					100,	false);
		super.addField(Field.FILE_ACTION_TYPE_ID,		"FileActionType_PK",		DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

}
