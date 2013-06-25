package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Created:   EP
 * Modified:  07 Jun 2011 VC #905: Multiple MMDAs at single DI - Report Naming Convention
 * 
 * This class holds a list of all Program Deposit Accounts for a particular program.
 * 
 * =================================================================================================
 */
public class ViewProgramDepositAccountList extends AbstractDatabaseBean<ViewProgramDepositAccountList.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  BANK_CODE
		, BANK_ID
		, BANK_NAME	
		, BANK_SEQ_NUM
		, FDIC_BANK_CODE
		, IS_PENDING_DEPOSITS
		, PROGRAM_DEPOSIT_ACCOUNT_ID
		;
	}

	public ViewProgramDepositAccountList()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BANK_CODE,							"Code",							DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.BANK_ID,							"DepInst_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				24,		false);	
		super.addField(Field.BANK_SEQ_NUM,						"BankSequenceNum",				DatabaseDataType.INT,				0,		false);	
		super.addField(Field.FDIC_BANK_CODE,					"FDICCertificateNumber",		DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.IS_PENDING_DEPOSITS,				"IsPendingDeposits",			DatabaseDataType.BIT,				0,		false);	
		super.addField(Field.PROGRAM_DEPOSIT_ACCOUNT_ID,		"ProgramDepositAccount_PK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
	}

	// Getters
	public String getBankCode()
	{
		return this.getField(Field.BANK_CODE).getStringValue();
	}
	
	public long getBankId()
	{
		return this.getField(Field.BANK_ID).getLongValue();
	}
	
	public String getBankName()
	{
		return this.getField(Field.BANK_NAME).getStringValue();
	}
	
	public int getBankSeqNum()
	{
		return this.getField(Field.BANK_SEQ_NUM).getIntegerValue();
	}

	public String getFDICBankCode()
	{
		return this.getField(Field.FDIC_BANK_CODE).getStringValue();
	}
	
	public boolean isPendingDeposits()
	{
		return this.getField(Field.IS_PENDING_DEPOSITS).getBooleanValue();
	}
	
	public long getProgramDepositAccountId()
	{
		return this.getField(Field.PROGRAM_DEPOSIT_ACCOUNT_ID).getLongValue();
	}
	
}
