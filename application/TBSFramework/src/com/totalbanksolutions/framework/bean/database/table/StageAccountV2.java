package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class StageAccountV2 extends AbstractDatabaseBean<StageAccountV2.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "StageAccountsV2";

	public enum Field
	{
		 ACCOUNT_ID
		,ACCOUNT_NUMBER
		,BATCH_NUMBER_ID
		,IS_TEFRA_WITHHELD
		,OFFICE_CODE
		,PAYOUT_TYPE
		,REGISTRATION_LINE1
		,REGISTRATION_LINE2
		,REGISTRATION_LINE3
		,REGISTRATION_LINE4
		,REGISTRATION_LINE5
		,REGISTRATION_LINE6
		,REGISTRATION_LINE7
		,REGISTRATION_LINE8
		,REP_CODE
		,SOURCE_ACCOUNT_TYPE
		,SOURCE_INSTITUTION_ID
		,SOURCE_PAYOUT_TYPE
		,SOURCE_PRODUCT_CODE
		,TAX_ID
		,TAX_ID2
	}

	public StageAccountV2()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_ID,				"Account_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ACCOUNT_NUMBER,			"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.BATCH_NUMBER_ID,			"BatchNumber_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.IS_TEFRA_WITHHELD,			"IsTefraWithheld",				DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.OFFICE_CODE,				"OfficeCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PAYOUT_TYPE,				"PayoutType",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.REGISTRATION_LINE1,		"RegistrationLine1",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE2,		"RegistrationLine2",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE3,		"RegistrationLine3",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE4,		"RegistrationLine4",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE5,		"RegistrationLine5",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE6,		"RegistrationLine6",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE7,		"RegistrationLine7",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE8,		"RegistrationLine8",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REP_CODE,					"RepCode",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SOURCE_ACCOUNT_TYPE,		"SourceAccountType",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,		"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_PRODUCT_CODE,		"SourceProductCode",			DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.TAX_ID,					"TaxID",						DatabaseDataType.CHAR,				9,		false);
		super.addField(Field.TAX_ID2,					"TaxID2",						DatabaseDataType.CHAR,				9,		false);
	}

	// Getters
	public long getAccountId()
	{
		return this.getField(Field.ACCOUNT_ID).getLongValue();
	}
	
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}
	
	
	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}

	public void setBatchNumberId(long s)
	{
		this.getField(Field.BATCH_NUMBER_ID).setValue(s);
	}

	public void setIsTefraWithheld(String s)
	{
		this.getField(Field.IS_TEFRA_WITHHELD).setValue(s);
	}
	
	public void setOfficeCode(String s)
	{
		this.getField(Field.OFFICE_CODE).setValue(s);
	}

	public void setPayoutType(String s)
	{
		this.getField(Field.PAYOUT_TYPE).setValue(s);
	}
	
	public void setRegistrationLine1(String s)
	{
		this.getField(Field.REGISTRATION_LINE1).setValue(s);
	}
	
	public void setRegistrationLine2(String s)
	{
		this.getField(Field.REGISTRATION_LINE2).setValue(s);
	}

	public void setRegistrationLine3(String s)
	{
		this.getField(Field.REGISTRATION_LINE3).setValue(s);
	}
	
	public void setRegistrationLine4(String s)
	{
		this.getField(Field.REGISTRATION_LINE4).setValue(s);
	}

	public void setRegistrationLine5(String s)
	{
		this.getField(Field.REGISTRATION_LINE5).setValue(s);
	}

	public void setRegistrationLine6(String s)
	{
		this.getField(Field.REGISTRATION_LINE6).setValue(s);
	}

	public void setRegistrationLine7(String s)
	{
		this.getField(Field.REGISTRATION_LINE7).setValue(s);
	}

	public void setRegistrationLine8(String s)
	{
		this.getField(Field.REGISTRATION_LINE8).setValue(s);
	}
	
	public void setRepCode(String s)
	{
		this.getField(Field.REP_CODE).setValue(s);
	}

	public void setSourceAccountType(String s)
	{
		this.getField(Field.SOURCE_ACCOUNT_TYPE).setValue(s);
	}
	
	public void setSourceInstitutionId(long n)
	{
		this.getField(Field.SOURCE_INSTITUTION_ID).setValue(n);
	}

	public void setSourceProductCode(String s)
	{
		this.getField(Field.SOURCE_PRODUCT_CODE).setValue(s);
	}
	
	public void setTaxId(String s)
	{
		this.getField(Field.TAX_ID).setValue(s);
	}

	public void setTaxId2(String s)
	{
		this.getField(Field.TAX_ID2).setValue(s);
	}
	
}
