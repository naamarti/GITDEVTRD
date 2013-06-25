package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/* 
 * =================================================================================================
 * Modified:  19 Sep 2011 VC #1006: System Stress Test: Transaction Loading Optimizations  
 * =================================================================================================
 */
public class StageTransactionsFromBalances extends AbstractDatabaseBean<StageTransactionsFromBalances.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "StageTransactionsFromBalances";
	
	public enum Field
	{
		 ACCOUNT_NUMBER
		,BALANCE_AMOUNT
		,BALANCE_DATE
		,BATCH_NUMBER
		,INTEREST_ACCRUED_PTD
		,SOURCE_INSTITUTION_ID
	}

	public StageTransactionsFromBalances()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.BALANCE_AMOUNT,					"BalanceAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.BATCH_NUMBER,						"BatchNumber_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,				"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

	// Getters
	
	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}

	public void setBalanceAmount(double n)
	{
		this.getField(Field.BALANCE_AMOUNT).setValue(n);
	}
	
	public void setBalanceDate(Date d)
	{
		this.getField(Field.BALANCE_DATE).setValue(d);
	}

	public void setBatchNumber(long n)
	{
		this.getField(Field.BATCH_NUMBER).setValue(n);
	}

	public void setInterestAccruedPTD(double n)
	{
		this.getField(Field.INTEREST_ACCRUED_PTD).setValue(n);
	}

	public void setSourceInstitutionId(long n)
	{
		this.getField(Field.SOURCE_INSTITUTION_ID).setValue(n);
	}

}
