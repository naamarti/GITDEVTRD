package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 * Modified: 04 May 2011 VC #417: Forecast/Consolidation Group Reports - add "UnderInsuredAmt", "OverInsuredAmt", "OptOutsIgnored", "SIName", "Product"
 */
public class ViewConsolidationGroupBalances extends AbstractDatabaseBean<ViewConsolidationGroupBalances.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";

	public enum Field
	{
		  ACCOUNT_NUMBER
		, ACCOUNT_TYPE
		, BALANCE
		, GROUP_NUMBER
		, INSURED_AMOUNT
		, IS_IGNORE_OPTOUTS
		, NUMBER_OF_ACCOUNTS
		, NUMBER_OPTOUTS
		, OVER_FDIC_AMOUNT
		, OVERINSURED_AMOUNT
		, PRODUCT_NAME
		, SI_NAME
		, UNDERINSURED_AMOUNT
		;
	}

	public ViewConsolidationGroupBalances()
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

		super.addField(Field.INSURED_AMOUNT,					"InsuredAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.IS_IGNORE_OPTOUTS,					"IsIgnoreOptOuts",				DatabaseDataType.BIT,				0,		false);	
		super.addField(Field.GROUP_NUMBER,						"GroupNumber",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.NUMBER_OF_ACCOUNTS,				"NumberOfAccounts",				DatabaseDataType.INT,				0,		false);	
		super.addField(Field.NUMBER_OPTOUTS,					"NumberOfOptOuts",				DatabaseDataType.INT,				0,		false);	
		super.addField(Field.OVER_FDIC_AMOUNT,					"OverFDICAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.OVERINSURED_AMOUNT,				"OverInsuredAmount",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PRODUCT_NAME,						"ProductName",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SI_NAME,							"SIName",						DatabaseDataType.CHAR,				50,		false);	
		super.addField(Field.UNDERINSURED_AMOUNT,				"UnderInsuredAmount",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}
	
}
