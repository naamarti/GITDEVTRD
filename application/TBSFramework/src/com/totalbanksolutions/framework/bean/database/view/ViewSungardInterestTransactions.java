package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewSungardInterestTransactions extends AbstractDatabaseBean<ViewSungardInterestTransactions.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, AVERAGE_BALANCE_PTD
		, BALANCE
		, INTEREST_AMOUNT
		, PRODUCT
		, SUSPENSE_ACCOUNT_NUMBER
		;
	}
	
	public ViewSungardInterestTransactions()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.AVERAGE_BALANCE_PTD,				"AverageBalance_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.BALANCE,							"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.INTEREST_AMOUNT,					"InterestAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.PRODUCT,							"Product",						DatabaseDataType.CHAR,				12,		false);	
		super.addField(Field.SUSPENSE_ACCOUNT_NUMBER,			"SuspenseAccountNumber",		DatabaseDataType.CHAR,				50,		false);	
	}

}
