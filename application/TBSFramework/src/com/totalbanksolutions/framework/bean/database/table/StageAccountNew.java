package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class StageAccountNew extends AbstractDatabaseBean<StageAccountNew.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "StageAccountsNew";
	
	public enum Field
	{
		 ACCOUNT_ID
		,ACCOUNT_NUMBER
		,ACCOUNT_NUMBER_OLD
		,ACCT_INVALID_DESC
		,ACCT_INVALID_VALUE
		,ACCT_TYPE_INVALID_DESC
		,ACCT_TYPE_INVALID_VALUE
		,AGENT
		,BATCH_NUMBER
		,COUNTRY_CODE
		,CREATED_BY_USERNAME
		,CREATED_DATETIME
		,FILE_DATE
		,HOUSEHOLD_VALUE
		,IS_MANUAL
		,IS_VALID
		,IS_WARNING
		,LINE_NUMBER
		,MAPPED_ACCOUNT_TYPE
		,MAPPED_PRODUCT_CODE
		,OFFICE_CODE
		,PRODUCT_ID
		,PRODUCT_INVALID_DESC
		,PRODUCT_INVALID_VALUE
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
		,SHORT_NAME
		,SOURCE_ACCOUNT_TYPE
		,SOURCE_INSTITUTION_ID
		,SOURCE_PRODUCT_CODE
		,STARTING_ADDRESS_LINE
		,STATE_CODE
		,TAX_ID
		,TAX_ID_INVALID_DESC
		,TAX_ID_INVALID_VALUE
		,TAX_ID2
		,TAX_ID2_INVALID_DESC
		,TAX_ID2_INVALID_VALUE
		,TAX_STATUS
		,TAX_WH_TYPE
		,TEFRA_CODE
		,ZIP_CODE
		,ZIP_PLUS4
	}

	public StageAccountNew()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_ID,				"Account_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ACCOUNT_NUMBER,			"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.ACCOUNT_NUMBER_OLD,		"OldAccountNumber",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCT_INVALID_DESC,			"AcctInvalidDesc",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCT_INVALID_VALUE,		"AcctInvalidValue",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.ACCT_TYPE_INVALID_DESC,	"AcctTypeInvalidDesc",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ACCT_TYPE_INVALID_VALUE,	"AcctTypeInvalidValue",			DatabaseDataType.CHAR,				3,		false);
		super.addField(Field.AGENT,						"Agent",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.BATCH_NUMBER,				"BatchNumber_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.COUNTRY_CODE,				"CountryCode",					DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.CREATED_BY_USERNAME,		"CreatedByUserName",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.CREATED_DATETIME,			"CreatedDateTime",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.FILE_DATE,					"FileDate",						DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.HOUSEHOLD_VALUE,			"Balance_From_SI",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.IS_MANUAL,					"IsManual",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_VALID,					"IsValid",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_WARNING,				"IsWarning",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LINE_NUMBER,				"LineNumber",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.MAPPED_ACCOUNT_TYPE,		"MappedAccountType",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.MAPPED_PRODUCT_CODE,		"MappedProductCode",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.OFFICE_CODE,				"OfficeCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PRODUCT_ID,				"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PRODUCT_INVALID_DESC,		"ProductInvalidDesc",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.PRODUCT_INVALID_VALUE,		"ProductInvalidValue",			DatabaseDataType.CHAR,				9,		false);
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
		super.addField(Field.REP_CODE,					"RepCode",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SHORT_NAME,				"ShortName",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SOURCE_ACCOUNT_TYPE,		"SourceAccountType",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,		"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_PRODUCT_CODE,		"SourceProductCode",			DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.STARTING_ADDRESS_LINE,		"StartingAddressLine",			DatabaseDataType.CHAR,				5,		false);
		super.addField(Field.STATE_CODE,				"StateCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.TAX_ID,					"TaxID",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_ID_INVALID_DESC,		"TaxIDInvalidDesc",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_ID_INVALID_VALUE,		"TaxIDInvalidValue",			DatabaseDataType.CHAR,				9,		false);
		super.addField(Field.TAX_ID2,					"TaxID2",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_ID2_INVALID_DESC,		"TaxID2InvalidDesc",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_ID2_INVALID_VALUE,		"TaxID2InvalidValue",			DatabaseDataType.CHAR,				9,		false);
		super.addField(Field.TAX_STATUS,				"TaxStatus",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TAX_WH_TYPE,				"TaxWitholdingType",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.TEFRA_CODE,				"TefraCode",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.ZIP_CODE,					"ZipCode",						DatabaseDataType.CHAR,				5,		false);
		super.addField(Field.ZIP_PLUS4,					"ZipPlus4",						DatabaseDataType.CHAR,				10,		false);
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
	
	public String getMappedAccountType()
	{
		return this.getField(Field.MAPPED_ACCOUNT_TYPE).getStringValue();
	}
	
	
	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}

	public void setAccountNumberOld(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER_OLD).setValue(s);
	}	
	
	public void setAccountInvalidDescription(String s)
	{
		this.getField(Field.ACCT_INVALID_DESC).setValue(s);
	}

	public void setAccountInvalidValue(String s)
	{
		this.getField(Field.ACCT_INVALID_VALUE).setValue(s);
	}

	public void setAccountTypeInvalidDescription(String s)
	{
		this.getField(Field.ACCT_TYPE_INVALID_DESC).setValue(s);
	}

	public void setAccountTypeInvalidValue(String s)
	{
		this.getField(Field.ACCT_TYPE_INVALID_VALUE).setValue(s);
	}

	public void setAgent(String s)
	{
		this.getField(Field.AGENT).setValue(s);
	}	

	public void setBatchNumber(long n)
	{
		this.getField(Field.BATCH_NUMBER).setValue(n);
	}

	public void setCountryCode(String s)
	{
		this.getField(Field.COUNTRY_CODE).setValue(s);
	}
	
	public void setCreatedByUserName(String s)
	{
		this.getField(Field.CREATED_BY_USERNAME).setValue(s);
	}

	public void setCreatedDateTime(Date d)
	{
		this.getField(Field.CREATED_DATETIME).setValue(d);
	}

	public void setFileDate(Date d)
	{
		this.getField(Field.FILE_DATE).setValue(d);
	}
	
	public void setIsManual(boolean b)
	{
		this.getField(Field.IS_MANUAL).setValue(b);
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

	public void setMappedAccountType(String s)
	{
		this.getField(Field.MAPPED_ACCOUNT_TYPE).setValue(s);
	}

	public void setMappedProductCode(String s)
	{
		this.getField(Field.MAPPED_PRODUCT_CODE).setValue(s);
	}	
	
	public void setOfficeCode(String s)
	{
		this.getField(Field.OFFICE_CODE).setValue(s);
	}

	public void setProductId(long n)
	{
		this.getField(Field.PRODUCT_ID).setValue(n);
	}	
	
	public void setProductInvalidDescription(String s)
	{
		this.getField(Field.PRODUCT_INVALID_DESC).setValue(s);
	}

	public void setProductInvalidValue(String s)
	{
		this.getField(Field.PRODUCT_INVALID_VALUE).setValue(s);
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

	public void setShortName(String s)
	{
		this.getField(Field.SHORT_NAME).setValue(s);
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
	
	public void setStartingAddressLine(String s)
	{
		this.getField(Field.STARTING_ADDRESS_LINE).setValue(s);
	}
	
	public void setStateCode(String s)
	{
		this.getField(Field.STATE_CODE).setValue(s);
	}
	
	public void setTaxId(String s)
	{
		this.getField(Field.TAX_ID).setValue(s);
	}

	public void setTaxIdInvalidDescription(String s)
	{
		this.getField(Field.TAX_ID_INVALID_DESC).setValue(s);
	}
	
	public void setTaxIdInvalidValue(String s)
	{
		this.getField(Field.TAX_ID_INVALID_VALUE).setValue(s);
	}
	
	public void setTaxId2(String s)
	{
		this.getField(Field.TAX_ID2).setValue(s);
	}
	
	public void setTaxId2InvalidDescription(String s)
	{
		this.getField(Field.TAX_ID_INVALID_DESC).setValue(s);
	}
	
	public void setTaxId2InvalidValue(String s)
	{
		this.getField(Field.TAX_ID_INVALID_VALUE).setValue(s);
	}
	
	public void setTaxStatus(String s)
	{
		this.getField(Field.TAX_STATUS).setValue(s);
	}

	public void setTaxWithholdingType(String s)
	{
		this.getField(Field.TAX_WH_TYPE).setValue(s);
	}
	
	public void setTefraCode(String s)
	{
		this.getField(Field.TEFRA_CODE).setValue(s);
	}
	
	public void setZipCode(String s)
	{
		this.getField(Field.ZIP_CODE).setValue(s);
	}

	public void setZipPlus4(String s)
	{
		this.getField(Field.ZIP_PLUS4).setValue(s);
	}
	
}
