package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewWires extends AbstractDatabaseBean<ViewWires.Field>
{		
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NO		
		, BUSINESS_DATE
		, CUT
		, TEMPLATE				
		, USD
		, WIRE_OUT
	}

	public ViewWires()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_NO,				"AccountNo",					DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.BUSINESS_DATE,				"BusinessDate",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.CUT,						"Cut",							DatabaseDataType.CHAR,              20,		false);	
		super.addField(Field.TEMPLATE,					"Template",						DatabaseDataType.CHAR,              20,		false);				
		super.addField(Field.USD, 						"USD",							DatabaseDataType.CHAR,              20,		false);		
		super.addField(Field.WIRE_OUT, 					"WireOut",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}

}
