package com.totalbanksolutions.framework.bean.database.table;

import java.util.ArrayList;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 */
public class App extends AbstractDatabaseBean<App.Field>
{
	private List<AppNavigation>	navigationList = new ArrayList<AppNavigation>();
	private boolean		isDeleted;
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppID";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	

	public enum Field
	{
		 APP
		,APPLICATION
		,DESCRIPTION
		;
	}

	public App()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName               		Database_FieldName            Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.APP,						"App_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.APPLICATION,				"Application",					DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.DESCRIPTION,				"Description",					DatabaseDataType.CHAR,				255,	false);
	}
	
	public List<AppNavigation> getNavigationList() {
		return navigationList;
	}

	public void setNavigationList(List<AppNavigation> navigationList) {
		this.navigationList = navigationList;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
