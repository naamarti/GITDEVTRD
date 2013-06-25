package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewConsolidationAccountBalances extends AbstractDatabaseBean<ViewConsolidationAccountBalances.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";

	public enum Field
	{ 
		  ACCOUNT_NUMBER
		, ACCOUNT_TYPE
		, BALANCE
		, GROUP_NUMBER
		, PRODUCT_NAME
		, SI_NAME
		;
	}

	public ViewConsolidationAccountBalances()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.ACCOUNT_TYPE,						"AccountType",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.BALANCE,							"Balance",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	

		for(int i = 1; i <= 50; i++)
		{
			super.addField("BALANCE_BANK"+i,					"Balance_Bank"+i,				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		}
		
		super.addField(Field.GROUP_NUMBER,						"GroupNumber",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.PRODUCT_NAME,						"ProductName",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SI_NAME,							"SIName",						DatabaseDataType.CHAR,				50,		false);	
	}
	
}
