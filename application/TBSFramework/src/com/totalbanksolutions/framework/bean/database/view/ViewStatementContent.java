package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author npandya
 */
public class ViewStatementContent extends AbstractDatabaseBean<ViewStatementContent.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 RECORD_ID
		 ,VALUE
		;
	}

	public ViewStatementContent()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.RECORD_ID,							"Record_id",					DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.VALUE,								"Value",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);			
	}
	
}
