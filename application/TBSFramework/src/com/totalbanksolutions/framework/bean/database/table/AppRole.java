package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;

public class AppRole extends AbstractDatabaseBean<AppRole.Field> 
{
	public static String DATABASE_NAME = Databases.COMMON;
	public static String TABLE_NAME = "AppRole";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 BANK_SUPER_USER			("BankSuperUser"    		)
		,BANK_VIEWER				("BankViewer"  				)
		,BANK_RECON_USER			("BankReconUser"			)
		,BROKER_SUPER_USER			("BrokerSuperUser"    		)
		,BROKER_VIEWER				("BrokerViewer"				)
		,NOW_BROKER_SUPER_USER		("NOWBrokerSuperUser"		)
		,NOW_BROKER_VIEWER			("NOWBrokerViewer"			)
		,REPORTS_USER				("ReportsUser"				)
		,SYSTEMS_USER				("TBSSystemsUser"			)
		,SUPER_USER					("SuperUser"				)
		,TBS_INSTITUTION_EMULATOR	("TBSInstitutionEmulator"	)
		,USER_15C3					("15C3User"					)
		;
	
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public enum Field
	{
		 DESCRIPTION
		,ID
		,NAME
		;
	}

	public AppRole()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DESCRIPTION,			"RoleDescription",			DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.ID,					"Role_PK",					DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.NAME,					"RoleName",					DatabaseDataType.CHAR,					50,		false);
	}

	// Getters
	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	public String getName()
	{
		return this.getField(Field.NAME).getStringValue();
	}
	
}
