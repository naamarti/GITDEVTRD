package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Created:   NAM
 * Modified:  20 Sep 2012      
 *
 * Bean for the Processing_DirectoryType table.
 * 
 * =================================================================================================
 */
public class Processing_DirectoryType extends AbstractDatabaseBean<Processing_DirectoryType.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Processing_DirectoryType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 WORKING                ( "Working"                     )
		,ARCHIVE             	( "Archive"                     )		
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public enum Field
	{
		 ID
		,DIRECTORY_TYPE
		,DESCRIPTION
		;
	}

	public Processing_DirectoryType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName			Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------	
		super.addField(Field.ID,						"DirectoryType_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.DIRECTORY_TYPE,			"DirectoryType",			DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.DESCRIPTION,				"Description",				DatabaseDataType.CHAR,				200,	false);
	}

	// Getters

	public String getDirectoryType()
	{
		return this.getField(Field.DIRECTORY_TYPE).getStringValue();
	}
	
	public String getDescription()
	{
		return this.getField(Field.DESCRIPTION).getStringValue();
	}
	
	public long getProcessing_DirectoryTypeId()
	{
		return this.getField(Field.ID).getLongValue();
	}
}
