package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class AppNavigationView extends DefaultModelTable
{
	public final String GROUP_ID					= "groupId";
	public final String GROUP_NAME					= "groupName";
	public final String GROUP_ORDER					= "groupOrder";
	public final String NAVIGATION_ID				= "navigationId";
	public final String NAVIGATION_NAME				= "navigationName";
	public final String NAVIGATION_ORDER			= "navigationOrder";
	public final String NAVIGATION_URL				= "navigationURL";

	public AppNavigationView()
	{
		this.setDatabaseName( Databases.COMMON );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.GROUP_ID,				"NavigationGroup_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		"",							"" );
		this.addColumn( this.GROUP_NAME,			"GroupName",				DatabaseDataType.CHAR,				30,		"",							"" ); 
		this.addColumn( this.GROUP_ORDER,			"GroupOrder",				DatabaseDataType.INT,				0,		"",							"" );
		this.addColumn( this.NAVIGATION_ID,			"Navigation_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		"",							"" );
		this.addColumn( this.NAVIGATION_NAME,		"NavigationName",			DatabaseDataType.CHAR,				30,		"",							"" );
		this.addColumn( this.NAVIGATION_ORDER,		"NavigationOrder",			DatabaseDataType.INT,				0,		"",							"" );
		this.addColumn( this.NAVIGATION_URL,		"NavigationURL",			DatabaseDataType.CHAR,				250,	"",							"" );
	}

}

