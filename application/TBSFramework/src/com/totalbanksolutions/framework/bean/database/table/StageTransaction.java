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
public class StageTransaction extends AbstractDatabaseBean<StageTransaction.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "StageTransactions";
	
	public enum Field
	{
		 ACCOUNT_NUMBER
		,BATCH_NUMBER
		,CONFIRMED_AMOUNT
		,DEBIT_CREDIT_IND
		,EFFECTIVE_DATE
		,ENTRY_DATE
		,FUND_NUM
		,IS_DELETED_TRANSACTION
		,IS_FULL_WITHDRAWAL
		,IS_NETTED_AMOUNT
		,IS_SEND_BACK_CONFIRM
		,OFFSET_AMOUNT
		,PRODUCT_CODE
		,RECORD_ID
		,REQUESTED_AMOUNT
		,SOURCE_INSTITUTION_ID
		,SOURCE_REF_NUMBER
		,TIER_CODE
		,TRANSACTION_EXCEPTION_TYPE_ID
		,TRANSACTION_ID
		,TRANSACTION_TYPE
		,UPDATE_IND
	}

	public StageTransaction()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.BATCH_NUMBER,						"BatchNumber_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.CONFIRMED_AMOUNT,					"ConfirmedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.DEBIT_CREDIT_IND,					"DebitCreditInd",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.EFFECTIVE_DATE,					"EffectiveDate",				DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.ENTRY_DATE,						"EntryDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.FUND_NUM,							"FundNum",						DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.IS_DELETED_TRANSACTION,			"IsDeletedTransaction",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_FULL_WITHDRAWAL,				"IsFullWithdrawal",				DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.IS_NETTED_AMOUNT,					"IsNettedAmount",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SEND_BACK_CONFIRM,				"IsSendBackConfirm",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.OFFSET_AMOUNT,						"OffsetAmount",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PRODUCT_CODE,						"ProductCode",					DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.RECORD_ID,							"RecordID",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.REQUESTED_AMOUNT,					"RequestedAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.SOURCE_INSTITUTION_ID,				"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_REF_NUMBER,					"SourceRefNumber",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.TIER_CODE,							"TierCode",						DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.TRANSACTION_TYPE,					"TransactionType",				DatabaseDataType.CHAR,				10,		false);
		super.addField(Field.TRANSACTION_EXCEPTION_TYPE_ID,		"TransactionExceptionType_FK",	DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.UPDATE_IND,						"UpdateInd",					DatabaseDataType.CHAR,				1,		false);
		super.addField(Field.TRANSACTION_ID,					"Transaction_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
	}

	// Getters
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}
	
	public Double getConfirmedAmount()
	{
		return this.getField(Field.CONFIRMED_AMOUNT).getDoubleValue();
	}
	
	public String getDebitCreditIndicator()
	{
		return this.getField(Field.DEBIT_CREDIT_IND).getStringValue();
	}
	
	public Double getRequestedAmount()
	{
		return this.getField(Field.REQUESTED_AMOUNT).getDoubleValue();
	}

	public Double getOffsetAmount()
	{
		return this.getField(Field.OFFSET_AMOUNT).getDoubleValue();
	}
	
	public boolean isFullWithdrawal()
	{
		return this.getField(Field.IS_FULL_WITHDRAWAL).getBooleanValue();
	}
	
	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}

	public void setBatchNumber(long n)
	{
		this.getField(Field.BATCH_NUMBER).setValue(n);
	}
	
	public void setConfirmedAmount(double n)
	{
		this.getField(Field.CONFIRMED_AMOUNT).setValue(n);
	}

	public void setDebitCreditIndicator(String s)
	{
		this.getField(Field.DEBIT_CREDIT_IND).setValue(s);
	}

	public void setEffectiveDate(Date d)
	{
		this.getField(Field.EFFECTIVE_DATE).setValue(d);
	}

	public void setEntryDate(Date d)
	{
		this.getField(Field.ENTRY_DATE).setValue(d);
	}

	public void setIsDeleteTransaction(boolean b)
	{
		this.getField(Field.IS_DELETED_TRANSACTION).setValue(b);
	}
	
	public void setIsFullWithdrawal(String s)
	{
		this.getField(Field.IS_FULL_WITHDRAWAL).setValue(s);
	}
	
	public void setIsNettedAmount(boolean b)
	{
		this.getField(Field.IS_NETTED_AMOUNT).setValue(b);
	}
	
	public void setIsSendBackConfirm(boolean b)
	{
		this.getField(Field.IS_SEND_BACK_CONFIRM).setValue(b);
	}

	public void setOffsetAmount(double n)
	{
		this.getField(Field.OFFSET_AMOUNT).setValue(n);
	}

	public void setProductCode(String s)
	{
		this.getField(Field.PRODUCT_CODE).setValue(s);
	}

	public void setFundNum(String s)
	{
		this.getField(Field.FUND_NUM).setValue(s);
	}
	
	public void setRecordId(long n)
	{
		this.getField(Field.RECORD_ID).setValue(n);
	}
	
	public void setRequestedAmount(double n)
	{
		this.getField(Field.REQUESTED_AMOUNT).setValue(n);
	}

	public void setSourceInstitutionId(long n)
	{
		this.getField(Field.SOURCE_INSTITUTION_ID).setValue(n);
	}

	public void setSourceRefNumber(String s)
	{
		this.getField(Field.SOURCE_REF_NUMBER).setValue(s);
	}

	public void setTransactionExceptionTypeId(long n)
	{
		this.getField(Field.TRANSACTION_EXCEPTION_TYPE_ID).setValue(n);
	}

	public void setTransactionId(long n)
	{
		this.getField(Field.TRANSACTION_ID).setValue(n);
	}
	
	public void setTransactionType(String transType)
	{
		this.getField(Field.TRANSACTION_TYPE).setValue(transType);
	}

	public void setTierCode(String tierCode)
	{
		this.getField(Field.TIER_CODE).setValue(tierCode);
	}
	
	public void setUpdateInd(String indicator)
	{
		this.getField(Field.UPDATE_IND).setValue(indicator);
	}
}
