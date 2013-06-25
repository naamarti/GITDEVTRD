package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;


/**
 * @author nmartin
 */
public class ViewRG_Report extends AbstractDatabaseBean<ViewRG_Report.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "RG_Report";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		  DESCRIPTION
		, REPORT_ID
		;
	}

	public ViewRG_Report()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -----------------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName               		Database_FieldName          Database_DataType                   Size    IsIdentity
		// -----------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.DESCRIPTION,				"Description",				DatabaseDataType.CHAR,				100,	false);
		super.addField(Field.REPORT_ID,					"Report_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
	}
	
}


