package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerAverageBalance extends AbstractDatabaseBean<ViewCustomerAverageBalance.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccount_Balances";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, AVERAGE_BALANCE1
		, AVERAGE_BALANCE2
		, AVERAGE_BALANCE3
		;
	}

	public ViewCustomerAverageBalance()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName					Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",					DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.AVERAGE_BALANCE1,					"AverageBalance1",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AVERAGE_BALANCE2,					"AverageBalance2",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AVERAGE_BALANCE3,					"AverageBalance3",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
