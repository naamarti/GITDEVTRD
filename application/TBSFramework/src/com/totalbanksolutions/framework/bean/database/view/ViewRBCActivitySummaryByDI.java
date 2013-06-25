package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * -- Modified     6 June 2011 NP #905: Multiple MMDAs at single DI - Report Naming Convention
 */
public class ViewRBCActivitySummaryByDI extends AbstractDatabaseBean<ViewRBCActivitySummaryByDI.Field> 
{

	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  ACTIVITY_MMDA
		, ACTIVITY_NOW
		, ACTIVITY_TOTAL		
		, ACTIVITY_TYPE		
		, BANK_SEQUENCE		
		, DEPOSIT_INSTITUTION_ID
		, DEPOSIT_INSTITUTION_NAME		
		, PROGRAM_DEPOSIT_ACCOUNT_ID
	};
	
	public ViewRBCActivitySummaryByDI()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACTIVITY_MMDA,						"ActivityMMDA",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.ACTIVITY_NOW,						"ActivityNOW",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.ACTIVITY_TOTAL,					"ActivityTotal",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.ACTIVITY_TYPE,						"ActivityType",					DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.BANK_SEQUENCE,						"BankSequenceNum",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.DEPOSIT_INSTITUTION_ID,			"DepositInstitution_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.DEPOSIT_INSTITUTION_NAME,			"DepositInstitution",			DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.PROGRAM_DEPOSIT_ACCOUNT_ID,		"ProgramDepositAccount_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
	}	

	// Getters
	public String getActivityType()
	{
		return this.getField(Field.ACTIVITY_TYPE).getStringValue();
	}
	
	public int getBankSequenceNumber()
	{
		return this.getField(Field.BANK_SEQUENCE).getIntegerValue();
	}
	
	public long getDepositInstitutionId()
	{
		return this.getField(Field.DEPOSIT_INSTITUTION_ID).getLongValue();
	}

	public String getDepositInstitutionName()
	{
		return this.getField(Field.DEPOSIT_INSTITUTION_NAME).getStringValue();
	}
	
	public long getProgramDepositAccountId()
	{
		return this.getField(Field.PROGRAM_DEPOSIT_ACCOUNT_ID).getLongValue();
	}

}
