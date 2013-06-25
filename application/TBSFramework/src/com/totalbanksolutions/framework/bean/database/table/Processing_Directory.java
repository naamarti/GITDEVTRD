package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Created:   NAM
 * Modified:  20 Sep 2012
 *            
 * Bean for the Processing_Directory table.
 * 
 * =================================================================================================
 */
public class Processing_Directory extends AbstractDatabaseBean<Processing_Directory.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Processing_Directory";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	


	public enum Field
	{
		 ID
		,DIRECTORY
		,DIRECTORY_TYPE
		,IS_PRIMARY
		;
	}

	public Processing_Directory()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName            	Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,						"ProcessingDirectory_PK",		DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.DIRECTORY,					"Directory",					DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.DIRECTORY_TYPE,			"DirectoryType",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.IS_PRIMARY,				"IsPrimary",					DatabaseDataType.BIT,				0,		false);
	}

	// Getters
	public long getProcessing_DirectoryId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
	public String getDirectory()
	{
		return this.getField(Field.DIRECTORY).getStringValue();
	}
	
	public long getDirectoryType()
	{
		return this.getField(Field.DIRECTORY_TYPE).getLongValue();
	}
	
	public boolean getIsPrimary()
	{
		return this.getField(Field.IS_PRIMARY).getBooleanValue();
	}

	// Setters
	
}
