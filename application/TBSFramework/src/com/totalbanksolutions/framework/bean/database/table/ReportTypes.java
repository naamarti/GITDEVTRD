package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 * MODIFIED        07 Nov 2011 NAM #1051 - added SI email processes
 *                 07 May 2012 NM #1547: add Program Summary Report for email sender
 */
public class ReportTypes extends AbstractDatabaseBean<ReportTypes.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "ReportTypes";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 DEPOSIT_ACTIVITY_SUMMARY                	( "Deposit Activity Summary"    )
		,OMNIBUS_ACTIVITY_SUMMARY           		( "Omnibus Activity Summary"    ) 
		,SETTLEMENT      							( "Settlement"               	) 
		,TRANSFER_INSTRUCTIONS     					( "Transfer Instructions" 		)  
		,OFFSET_INSTRUCTIONS               			( "Offset Instructions"         )
		,OVER_FDIC      							( "Over FDIC"               	) 
		,OVER_FDIC_ACCOUNT                    		( "Over FIDC Account"           )  
		,SWEEP_DETAIL    							( "Sweep Detail" 				)  
		,SWEEP_SUMMARY                				( "Sweep Summary"               )
		,SWEEP_EXCEPTIONS               			( "Sweep Exceptions"            )
		,PROGRAM_SUMMARY               				( "Program Summary"             )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}
	
	public enum Field
	{
		 FILEDIR
		,FILENAME	
		,REPORT_TYPE_PK
		,TYPE
		;
	}

	public ReportTypes()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName             	Database_FieldName              	Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.FILEDIR,					"FileDirectory",				DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.FILENAME,					"FileName",						DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.REPORT_TYPE_PK,			"ReportType_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.TYPE,						"Type",							DatabaseDataType.CHAR,				255,	false);
		;
	}

	// Getters
	public String getFileDirectory()
	{
		return this.getField(Field.FILEDIR).getStringValue();
	}
	
	public String getFileName()
	{
		return this.getField(Field.FILENAME).getStringValue();
	}

	public long getReportTypeId()
	{
		return this.getField(Field.REPORT_TYPE_PK).getLongValue();
	}
	
	public String getType()
	{
		return this.getField(Field.TYPE).getStringValue();
	}

	// Setters
	public void setFileDirectory(String s)
	{
		this.getField(Field.FILEDIR).setValue(s);
	}

	public void setFileName(String s)
	{
		this.getField(Field.FILENAME).setValue(s);
	}
	
	public void setType(String s)
	{
		this.getField(Field.TYPE).setValue(s);
	}
}
