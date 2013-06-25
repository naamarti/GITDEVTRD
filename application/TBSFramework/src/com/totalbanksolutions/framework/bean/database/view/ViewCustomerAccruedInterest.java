package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewCustomerAccruedInterest extends AbstractDatabaseBean<ViewCustomerAccruedInterest.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_HOLDER_NAME
		, ACCOUNT_NUMBER	
		, ACCRUAL_AMOUNT_PTD
		, RECORD_ID
		;
	}
	
	public ViewCustomerAccruedInterest()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_HOLDER_NAME,				"Line1",						DatabaseDataType.CHAR,				256,	false);	
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.ACCRUAL_AMOUNT_PTD,				"AccruedInterest",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);	
	}
}
