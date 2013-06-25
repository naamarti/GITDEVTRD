package com.totalbanksolutions.framework.bean.database.table;

import java.util.ArrayList;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class AppNavigationAppRoleRelationship extends AbstractDatabaseBean<AppNavigationAppRoleRelationship.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppNavigation_AppRole_Relationship";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		  NAVIGATION_ID
		, ROLE_ID
		, IS_FULL_ACCESS
		;
	}

	public AppNavigationAppRoleRelationship()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName			Database_FieldName          Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.NAVIGATION_ID,		"Navigation_FK",		DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.ROLE_ID,			"Role_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.IS_FULL_ACCESS,	"IsFullAccess",			DatabaseDataType.BIT,					0,		false);
	}
	
	public long getNavigationId()
	{
		return this.getField(Field.NAVIGATION_ID).getLongValue();
	}

	public long getRoleId()
	{
		return this.getField(Field.ROLE_ID).getLongValue();
	}
	
	public boolean isFullAccess()
	{
		return this.getField(Field.IS_FULL_ACCESS).getBooleanValue();
	}
	
}
