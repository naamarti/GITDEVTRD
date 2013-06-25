package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class StageBankOptOut extends AbstractDatabaseBean<StageBankOptOut.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "StageBankOptOuts";
	
	public enum Field
	{
		 ACCOUNT_NUMBER_INCOMING
		,BALANCE_ID
		,BATCH_NUMBER
		,CREATED_BY_USERNAME
		,CREATED_DATETIME
		,DEPOSIT_INSTITUTION_CODE
		,FDIC_CERTIFICATE_NUMBER
		,IS_DELETE
		,IS_VALID
		,IS_WARNING
		,LINE_NUMBER
		,PROGRAM_ID				
		,RECORD_NUMBER
		,SOURCE_INSTITUTION_ID		
	}
	
	public StageBankOptOut()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_NUMBER_INCOMING, 	"AccountNumber_Incoming",		DatabaseDataType.CHAR,              30,		false);
		super.addField(Field.BALANCE_ID,				"Balance_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.BATCH_NUMBER,				"BatchNumber_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.CREATED_BY_USERNAME,		"CreatedByUserName",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.CREATED_DATETIME,			"CreatedDateTime",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.DEPOSIT_INSTITUTION_CODE,	"DepositInstitutionCode",		DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.FDIC_CERTIFICATE_NUMBER, 	"FDICCertificateNumber",		DatabaseDataType.CHAR,              20,		false);		
		super.addField(Field.IS_DELETE,					"IsDelete",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_VALID,					"IsValid",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_WARNING,				"IsWarning",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LINE_NUMBER,				"LineNumber",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.PROGRAM_ID,				"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);				
		super.addField(Field.RECORD_NUMBER,				"RecordNumber",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,		"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

	// Getters
	
	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER_INCOMING).setValue(s);
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
	
	public void setDepositInstitutionCode(String s)
	{
		this.getField(Field.DEPOSIT_INSTITUTION_CODE).setValue(s);
	}

	public void setFDICCertificateNumber(String s)
	{
		this.getField(Field.FDIC_CERTIFICATE_NUMBER).setValue(s);
	}

	public void setIsDelete(boolean b)
	{
		this.getField(Field.IS_DELETE).setValue(b);
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
	
	public void setProgramId(long n)
	{
		this.getField(Field.PROGRAM_ID).setValue(n);
	}

	public void setRecordNumber(Integer n)
	{
		this.getField(Field.RECORD_NUMBER).setValue(n.intValue());
	}

	public void setSourceInstitutionId(long n)
	{
		this.getField(Field.SOURCE_INSTITUTION_ID).setValue(n);
	}
	
}
