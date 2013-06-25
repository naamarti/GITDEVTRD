package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class SSNGenerator extends AbstractDatabaseBean<SSNGenerator.Field>
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "SSNGenerator";
		
	public enum Field
	{
		 SSN_GENERATOR_ID
		,SSN_ROOT
	}

	public SSNGenerator()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.SSN_GENERATOR_ID,			"SSNGenerator_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.SSN_ROOT,					"SSNRoot",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}
	
}
