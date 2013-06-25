package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerAccountInitialDeposit extends AbstractDatabaseBean<ViewCustomerAccountInitialDeposit.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccounts";
	
	public enum Field
	{
		  ACCOUNT_ID
		, ACCOUNT_NUMBER	
		, CUSIP_NUMBER
		, INITIAL_DEPOSIT_DATE		
		, LINE1
		, LINE2
		, LINE3
		, LINE4
		, LINE5		
		, LINE6
		, LINE7
		, LINE8
		, SECURITY_NUMBER
		;
	}

	public ViewCustomerAccountInitialDeposit()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_ID,						"CustomerAccount_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.CUSIP_NUMBER,						"CusipNumber",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.INITIAL_DEPOSIT_DATE,				"InitialDepositDate",			DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.LINE1,								"Line1",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.LINE2,								"Line2",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.LINE3,								"Line3",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.LINE4,								"Line4",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.LINE5,								"Line5",						DatabaseDataType.CHAR,				50,		false);		
		super.addField(Field.LINE6,								"Line6",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.LINE7,								"Line7",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.LINE8,								"Line8",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SECURITY_NUMBER,					"SecurityNumber",				DatabaseDataType.CHAR,				50,		false);
	}
	
}
