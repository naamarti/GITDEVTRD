package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class EmailSenderLogView extends DefaultModelTable
{
	public final String EMS_Server_Log_ID				= "emsServerLogId";
	public final String Program_Name					= "programName";
	public final String Program_ID						= "programId";
	public final String Envelope_ID						= "envelopeId";
	public final String Envelope_Name					= "envelopeName";
	public final String type							= "type";
	public final String message							= "message";
	public final String ErrorCode						= "errorCode";
	public final String PostedDateTime					= "postedDateTime";
	public final String PostedUser						= "postedUser";
	public final String IsDeleted						= "isDeleted";
	public final String BatchNumber_ID					= "batchnumberId";
	public final String DepositInstitution_ID			= "depositIntitutionId";
	public final String DI_Name							= "depositInstitutionName";
	public final String SourceInstitution_ID			= "sourceInstitutionID";
	public final String SI_Name							= "sourceInstitutionName";


	public EmailSenderLogView()
	{
		this.setDatabaseName( Databases.COMMON );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		
			    		  
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.EMS_Server_Log_ID,		"EMS_Server_Log_PK",	    DatabaseDataType.INT,				30,		"EMS_Server_Log_PK",		"Log PK" ); 
		this.addColumn( this.Program_Name,			"Program_Name",		    	DatabaseDataType.CHAR,				30,		"Program",  				"Program_Name" );
		this.addColumn( this.Program_ID,			"Program_FK",		    	DatabaseDataType.INT,				30,		"Program_FK",				"Program_FK" );
		this.addColumn( this.Envelope_ID,			"Envelope_FK",				DatabaseDataType.INT,				30,		"Envelope_FK",			    "Envelope_FK" );
		this.addColumn( this.Envelope_Name,			"Envelope_Name",		   	DatabaseDataType.CHAR,				30,		"Envelope",      			"Envelope_Name" );
		this.addColumn( this.type,					"type",						DatabaseDataType.CHAR,				30,		"type",						"type" );
		this.addColumn( this.message,				"message",					DatabaseDataType.CHAR,				250,	"message",					"message" );
		this.addColumn( this.ErrorCode,				"ErrorCode",				DatabaseDataType.CHAR,				30,		"ErrorCode",				"ErrorCode" );
		this.addColumn( this.PostedDateTime,		"PostedDateTime",			DatabaseDataType.CHAR,				30,		"PostedDateTime",			"PostedDateTime" );
		this.addColumn( this.PostedUser,			"PostedUser",				DatabaseDataType.CHAR,				30,		"PostedUser",				"PostedUser" );
		this.addColumn( this.IsDeleted,				"IsDeleted",				DatabaseDataType.BIT,				30,		"IsDeleted",				"IsDeleted" );
		this.addColumn( this.BatchNumber_ID,		"BatchNumber_FK",			DatabaseDataType.INT,				30,		"BatchNumber_FK",			"BatchNumber_FK" );
		this.addColumn( this.DepositInstitution_ID,	"DepositInstitution_FK",	DatabaseDataType.INT,				30,		"DepositInstitution_FK",	"DepositInstitution_FK" );
		this.addColumn( this.DI_Name,				"DI_Name",				   	DatabaseDataType.CHAR,				30,		"DepositInstitution",		"DI_Name" );
		this.addColumn( this.SourceInstitution_ID,	"SourceInstitution_FK",		DatabaseDataType.INT,				30,		"SourceInstitution_FK",		"SourceInstitution_FK" );
		this.addColumn( this.SI_Name,				"SI_Name",				   	DatabaseDataType.CHAR,				30,		"SourceInstituion",			"SI_Name" );
		
	
	}

}

