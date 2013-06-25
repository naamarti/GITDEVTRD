package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewCustomerAccountTransactionDelete extends AbstractDatabaseBean<ViewCustomerAccountTransactionDelete.Field>
{		
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  TRANSACTION_ID		
		, TRANSACTION_DATE
		, TRANSACTION_TYPE
		, AMOUNT				
		, PRODUCT
		, ACCOUNT
		, IS_SELECTED
		
	}

	public ViewCustomerAccountTransactionDelete()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.TRANSACTION_ID,			"Transaction_PK",				DatabaseDataType.INT,				0,		true);
		super.addField(Field.TRANSACTION_DATE,			"Date",							DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TRANSACTION_TYPE,			"Trans",						DatabaseDataType.CHAR,              20,		false);	
		super.addField(Field.AMOUNT,					"Amount",						DatabaseDataType.CHAR,			    20,		false);				
		super.addField(Field.PRODUCT,					"Product",						DatabaseDataType.CHAR,			    20,		false);		
		super.addField(Field.IS_SELECTED,				"isSelected",					DatabaseDataType.BIT,			    0,		false);		
		super.addField(Field.ACCOUNT,					"AccountNo",				 	DatabaseDataType.CHAR,			    40,		false);		
		
	}

}
