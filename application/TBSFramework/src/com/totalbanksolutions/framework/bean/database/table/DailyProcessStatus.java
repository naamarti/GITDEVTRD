package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class DailyProcessStatus extends AbstractDatabaseBean<DailyProcessStatus.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessStatus";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 CANCELED	(3)
		,FAILED		(4)
		,ON_HOLD	(6)
		,PENDING	(1)
		,RUNNING	(2)
		,SKIPPED	(8)
		,SUCCESS	(5)
		,UNDOING	(9)
		,WAITING	(7)
		;
		private Values(long id) { this.id = id; }		
		private long id;
		public long getId() { return id; }
	}

	public enum Field
	{
		 DESCRIPTION
		,PROCESS_STATUS
		,PROCESS_STATUS_ID
		;
	}

	public DailyProcessStatus()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName				Database_FieldName			Database_DataType                   Size    IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.DESCRIPTION,				"Description",				DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.PROCESS_STATUS,			"ProcessStatus",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PROCESS_STATUS_ID,			"ProcessStatus_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
	}

}
