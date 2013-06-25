package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;

public class AppNavigation extends AbstractDatabaseBean<AppNavigation.Field> 
{
	private boolean		isDeleted;
	private boolean		isSelected;
	private boolean		isFullAccess;

	public static String DATABASE_NAME = Databases.COMMON;
	public static String TABLE_NAME = "AppNavigation";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 GROUP_ID
		,ID
		,NAME
		,ORDER
		,URL
		//,APP_ID
		;
	}

	public AppNavigation()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName					Database_FieldName			Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.GROUP_ID,					"NavigationGroup_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.ID,						"Navigation_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.NAME,						"NavigationName",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ORDER,						"NavigationOrder",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.URL,						"NavigationURL",			DatabaseDataType.CHAR,				255,	false);
		//super.addField(Field.APP_ID,					"AppId_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isFullAccess() {
		return isFullAccess;
	}

	public void setFullAccess(boolean isFullAccess) {
		this.isFullAccess = isFullAccess;
	}

	// Getters
	public long getGroupId()
	{
		return this.getField(Field.GROUP_ID).getLongValue();
	}
	
	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
	public String getName()
	{
		return this.getField(Field.NAME).getStringValue();
	}

	public String getUrl()
	{
		return this.getField(Field.URL).getStringValue();
	}
	
	// Setters
	public void setGroupId(long id)
	{
		this.getField(Field.GROUP_ID).setValue(id);
	}

	public void setName(String name)
	{
		this.getField(Field.NAME).setValue(name);
	}

	public void setUrl(String url)
	{
		this.getField(Field.URL).setValue(url);
	}

}
