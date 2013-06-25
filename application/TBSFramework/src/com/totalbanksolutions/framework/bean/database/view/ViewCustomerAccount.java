package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerAccount extends AbstractDatabaseBean<ViewCustomerAccount.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccounts";
	
	public enum Field
	{
		  ACCOUNT_ID
		, ACCOUNT_NUMBER	
		, ACCOUNT_TYPE	
		, ACCOUNT_TYPE_ID
		, IS_TEFRA_WITHHELD
		, OFFICE_CODE	
		, PAYOUT_TYPE_ID
		, PRODUCT_ID
		, PRODUCT_NAME	
		, REGISTRATION_LINE1
		, REGISTRATION_LINE2
		, REGISTRATION_LINE3
		, REGISTRATION_LINE4
		, REGISTRATION_LINE5		
		, REGISTRATION_LINE6
		, REGISTRATION_LINE7
		, REGISTRATION_LINE8
		, REP_CODE	
		, SOURCE_INST_ACCOUNT_TYPE	
		, SOURCE_INSTITUTION_ID		
		, TAX_ID	
		, TAX_ID2
	}

	public ViewCustomerAccount()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_ID,						"CustomerAccount_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.ACCOUNT_TYPE,						"AccountType",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.ACCOUNT_TYPE_ID,					"CustomerAccount_Types_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.IS_TEFRA_WITHHELD,					"IsTefraWithheld",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.OFFICE_CODE,						"OfficeCode",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.PAYOUT_TYPE_ID,					"PayoutType_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.PRODUCT_ID,						"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PRODUCT_NAME,						"ProductName",					DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.REGISTRATION_LINE1,				"Line1",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE2,				"Line2",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE3,				"Line3",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE4,				"Line4",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE5,				"Line5",						DatabaseDataType.CHAR,				50,		false);		
		super.addField(Field.REGISTRATION_LINE6,				"Line6",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE7,				"Line7",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE8,				"Line8",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REP_CODE,							"RepCode",						DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SOURCE_INST_ACCOUNT_TYPE,			"SourceInstitutionAccountType",	DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.SOURCE_INSTITUTION_ID,				"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);		
		super.addField(Field.TAX_ID,							"TaxID1",						DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.TAX_ID2,							"TaxID2",						DatabaseDataType.CHAR,				40,		false);	
	}

	// Getters
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}
	
	public String getRegistrationLine1()
	{
		return this.getField(Field.REGISTRATION_LINE1).getStringValue();
	}

	public String getRegistrationLine2()
	{
		return this.getField(Field.REGISTRATION_LINE2).getStringValue();
	}

	public String getRegistrationLine3()
	{
		return this.getField(Field.REGISTRATION_LINE3).getStringValue();
	}

	public String getRegistrationLine4()
	{
		return this.getField(Field.REGISTRATION_LINE4).getStringValue();
	}
	
	public String getRegistrationLine5()
	{
		return this.getField(Field.REGISTRATION_LINE5).getStringValue();
	}

	public String getRegistrationLine6()
	{
		return this.getField(Field.REGISTRATION_LINE6).getStringValue();
	}

	public String getRegistrationLine7()
	{
		return this.getField(Field.REGISTRATION_LINE7).getStringValue();
	}
	
	public String getRegistrationLine8()
	{
		return this.getField(Field.REGISTRATION_LINE8).getStringValue();
	}
	
	public String getTaxId()
	{
		return this.getField(Field.TAX_ID).getStringValue();
	}

}
