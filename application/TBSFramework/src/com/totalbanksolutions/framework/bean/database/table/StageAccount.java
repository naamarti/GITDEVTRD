package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class StageAccount extends AbstractDatabaseBean<StageAccount.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "StageAccounts";
	
	public enum Field
	{
		 ACCOUNT_ID
		,ACCOUNT_NUMBER
		,ACCOUNT_TYPE
		,ACCOUNT_VALUE
		,BATCH_NUMBER
		,CREATED_BY_USERNAME
		,CREATED_DATETIME
		,IBD_CODE
		,IS_VALID
		,IS_WARNING
		,LINE_NUMBER
		,OFFICE_CODE
		,PRODUCT_CODE
		,PROGRAM_ID
		,RECORD_NUMBER
		,REGISTRATION_LINE1
		,REGISTRATION_LINE2
		,REGISTRATION_LINE3
		,REGISTRATION_LINE4
		,REGISTRATION_LINE5
		,REGISTRATION_LINE6
		,REGISTRATION_LINE7
		,REGISTRATION_LINE8
		,REP_CODE
		,SOURCE_INSTITUTION_ID
		,TAX_ID
		,TAX_ID_CODE
		,TAX_ID2
		,TAX_STATUS
		,TAX_WH_TYPE
	}

	public StageAccount()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_ID,				"Account_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ACCOUNT_NUMBER,			"AccountNumber",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCOUNT_TYPE,				"AccountType",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCOUNT_VALUE,				"AccountValue",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BATCH_NUMBER,				"BatchNumber_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.CREATED_BY_USERNAME,		"CreatedByUserName",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.CREATED_DATETIME,			"CreatedDateTime",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.IBD_CODE,					"IBDCode",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.IS_VALID,					"IsValid",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_WARNING,				"IsWarning",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LINE_NUMBER,				"LineNumber",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.OFFICE_CODE,				"OfficeCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PRODUCT_CODE,				"ProductCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PROGRAM_ID,				"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.RECORD_NUMBER,				"RecordNumber",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.REGISTRATION_LINE1,		"RegistrationLine1",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE2,		"RegistrationLine2",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE3,		"RegistrationLine3",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE4,		"RegistrationLine4",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE5,		"RegistrationLine5",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE6,		"RegistrationLine6",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE7,		"RegistrationLine7",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REGISTRATION_LINE8,		"RegistrationLine8",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.REP_CODE,					"RepCode",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,		"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.TAX_ID,					"TaxID",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_ID2,					"TaxID2",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_ID_CODE,				"TaxIDCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_STATUS,				"TaxStatus",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_WH_TYPE,				"TaxWitholdingType",			DatabaseDataType.CHAR,				30,		false);
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
	
	public String getAccountType()
	{
		return this.getField(Field.ACCOUNT_TYPE).getStringValue();
	}
	
	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}

	public void setAccountType(String s)
	{
		this.getField(Field.ACCOUNT_TYPE).setValue(s);
	}

	public void setAccountValue(double n)
	{
		this.getField(Field.ACCOUNT_VALUE).setValue(n);
	}

	public void setBatchNumber(long n)
	{
		this.getField(Field.BATCH_NUMBER).setValue(n);
	}

	public void setCreatedByUserName(String s)
	{
		this.getField(Field.CREATED_BY_USERNAME).setValue(s);
	}

	public void setCreatedDateTime(Date d)
	{
		this.getField(Field.CREATED_DATETIME).setValue(d);
	}

	public void setIBDCode(String s)
	{
		this.getField(Field.IBD_CODE).setValue(s);
	}

	public void setIsValid(boolean b)
	{
		this.getField(Field.IS_VALID).setValue(b);
	}

	public void setIsWarning(boolean b)
	{
		this.getField(Field.IS_WARNING).setValue(b);
	}

	public void setLineNumber(int n)
	{
		this.getField(Field.LINE_NUMBER).setValue(n);
	}

	public void setOfficeCode(String s)
	{
		this.getField(Field.OFFICE_CODE).setValue(s);
	}

	public void setProductCode(String s)
	{
		this.getField(Field.PRODUCT_CODE).setValue(s);
	}

	public void setProgramId(long n)
	{
		this.getField(Field.PROGRAM_ID).setValue(n);
	}	
	
	public void setRecordNumber(int n)
	{
		this.getField(Field.RECORD_NUMBER).setValue(n);
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

	public void setSourceInstitutionId(long n)
	{
		this.getField(Field.SOURCE_INSTITUTION_ID).setValue(n);
	}

	public void setTaxId(String s)
	{
		this.getField(Field.TAX_ID).setValue(s);
	}

	public void setTaxId2(String s)
	{
		this.getField(Field.TAX_ID2).setValue(s);
	}
	
	public void setTaxStatus(String s)
	{
		this.getField(Field.TAX_STATUS).setValue(s);
	}

}
