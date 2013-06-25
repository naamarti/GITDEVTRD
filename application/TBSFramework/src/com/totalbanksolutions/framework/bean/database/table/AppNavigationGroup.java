package com.totalbanksolutions.framework.bean.database.table;

import java.util.ArrayList;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class AppNavigationGroup extends AbstractDatabaseBean<AppNavigationGroup.Field> 
{
	private boolean	isDeleted = false;
	private List<AppNavigation>	navigationList = new ArrayList<AppNavigation>();

	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppNavigationGroup";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 ID
		,NAME
		,ORDER
		;
	}

	public AppNavigationGroup()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName			Database_FieldName          Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,				"NavigationGroup_PK",		DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.NAME,				"GroupName",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ORDER,				"GroupOrder",				DatabaseDataType.INT,				0,		false);
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<AppNavigation> getNavigationList() {
		return navigationList;
	}

	public void setNavigationList(List<AppNavigation> navigationList) {
		this.navigationList = navigationList;
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
	
	// Setters
	public void setId(long n)
	{
		this.getField(Field.ID).setValue(n);
	}
	
	public void setName(String s)
	{
		this.getField(Field.NAME).setValue(s);
	}
	
}
