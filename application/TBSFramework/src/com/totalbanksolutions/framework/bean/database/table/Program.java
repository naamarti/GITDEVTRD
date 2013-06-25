package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  10 Aug 2011 VC  #579: Create a new checklist for "Universal Reports" that span all program databases
 *            25 Jan 2012 NAM #1230: FTP_Archive directory added
 *            30 Jul 2012 NAM #1795: updated for FTPArchive_Backup directory
 *            25 Sep 2012 NAM #1869: removed Working and Archive Directories from program table.  Now in Processing_Directory table
 *            
 * Bean for the Programs table.
 * 
 * =================================================================================================
 */
public class Program extends AbstractDatabaseBean<Program.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Programs";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 CLEARVIEW			( "Clearview"              )
		,CURIAN				( "Curian"                 )
		,DAVIDSON			( "Davidson"               )
		,DB_SECURITIES		( "DBSecurities"           )
		,DBTCA				( "DBTCA"                  )
		,DEMO				( "Demo"                   )
		,FIRST_SOUTH_WEST	( "FSW"                    )
		,FOLIO				( "Folio"                  )
		,HCDENISON			( "Denison"                )
		,HILLIARD_LYONS		( "HilliardLyons"          )
		,LEGENT				( "Legent"                 )
		,LOYAL3				( "LOYAL3"                 )			
		,MESIROW			( "Mesirow"                )
		,NONE				( "NONE"                   )
		,OPTIONS_XPRESS		( "OptionsXpress"          )
		,PENSON_TEXAS		( "PensonTX"               )
		,PERSHING			( "Pershing"               )
		,PLAINS_CAPITAL		( "PlainsCapital"          )
		,PRIMEVEST			( "Primevest"              )
		,RBC				( "RBC"                    )
		,PENSON_NY			( "PensonNY"               )
		,SMITH_MOORE		( "SmithMoore"             )
		,STERNE				( "Sterne"                 )
		,UBS				( "UBS"                    )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public enum Field
	{
		 ID
		,INTERNAL_CODE
		,NAME
		,PERIOD_END_DATE
		,PROGRAM_DATABASE_NAME
		,PROGRAM_FOLDER_NAME
		,PROGRAM_SHORT_CODE
		,PROGRAM_SETTLE_WIRE_DIR
		,FTP_ARCHIVE_DIR
		,FTP_ARCHIVE_DIR_BACKUP
		;
	}

	public Program()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName            	Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,						"Program_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.INTERNAL_CODE,				"InternalCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.NAME,						"ProgramName",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PERIOD_END_DATE,			"PeriodEndDate",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.PROGRAM_DATABASE_NAME,		"ProgramDatabaseName",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PROGRAM_FOLDER_NAME,		"ProgramFolderName",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PROGRAM_SHORT_CODE,		"ProgramShortCode",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PROGRAM_SETTLE_WIRE_DIR,	"SettlementWireOutputDir",		DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.FTP_ARCHIVE_DIR,			"FTPArchiveDir",				DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.FTP_ARCHIVE_DIR_BACKUP,	"FTPArchiveDir_Backup",			DatabaseDataType.CHAR,				200,	false);
	}

	// Getters
	public long getProgramId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
	public String getInternalCode()
	{
		return this.getField(Field.INTERNAL_CODE).getStringValue();
	}
	
	public String getProgramName()
	{
		return this.getField(Field.NAME).getStringValue();
	}
	
	public int getPeriodEndDate()
	{
		return this.getField(Field.PERIOD_END_DATE).getIntegerValue();
	}

	public String getProgramDatabaseName()
	{
		return this.getField(Field.PROGRAM_DATABASE_NAME).getStringValue();
	}
	
	public String getProgramFolderName()
	{
		return this.getField(Field.PROGRAM_FOLDER_NAME).getStringValue();
	}

	public String getFtpArchiveDir()
	{
		return this.getField(Field.FTP_ARCHIVE_DIR).getStringValue();
	}
	
	public String getFtpArchiveDirBackup()
	{
		return this.getField(Field.FTP_ARCHIVE_DIR_BACKUP).getStringValue();
	}
	
	public String getProgramShortCode()
	{
		return this.getField(Field.PROGRAM_SHORT_CODE).getStringValue();
	}

	public String getSettlementWireOutputDir()
	{
		return this.getField(Field.PROGRAM_SETTLE_WIRE_DIR).getStringValue();
	}
	
	// Setters
	
}
