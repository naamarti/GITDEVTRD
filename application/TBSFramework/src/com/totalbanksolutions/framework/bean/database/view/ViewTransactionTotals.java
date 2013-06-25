package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewTransactionTotals extends AbstractDatabaseBean<ViewTransactionTotals.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  AMOUNT_DEPOSITS
		, AMOUNT_ICE
		, AMOUNT_NET
		, AMOUNT_WITHDRAWALS
		, AMOUNT_DELETIONS
		, AMOUNT_DELETION_DEPOSITS
		, AMOUNT_DELETION_WITHDRAWALS
		, COUNT_DEPOSITS
		, COUNT_ICE
		, COUNT_NET
		, COUNT_WITHDRAWALS
		, COUNT_DELETIONS
		, COUNT_WITHDRAWALS_DELETIONS
		, COUNT_DEPOSITS_DELETIONS
		;
	}

	
	
	
	
	public ViewTransactionTotals()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.AMOUNT_DEPOSITS,					"Deposits",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AMOUNT_ICE,						"Ice",							DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AMOUNT_NET,						"Net",							DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AMOUNT_WITHDRAWALS,				"Withdrawals",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AMOUNT_DELETIONS,					"Deletions",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AMOUNT_DELETION_WITHDRAWALS,		"DelWithdrawals",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.AMOUNT_DELETION_DEPOSITS,			"DelDeposits",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.COUNT_DEPOSITS,					"DepositsCount",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_ICE,							"IceCount",						DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_NET,							"NetCount",						DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_WITHDRAWALS,					"WithdrawalsCount",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_DELETIONS,					"DeletionsCount",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_WITHDRAWALS_DELETIONS,		"DelWithdrawalsCount",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_DEPOSITS_DELETIONS,			"DelDepositsCount",				DatabaseDataType.INT,				0,		false);
	}

	//Getters
	public double getAmountDeposits()
	{
		return this.getField(Field.AMOUNT_DEPOSITS).getDoubleValue();
	}
	
	public double getAmountICE()
	{
		return this.getField(Field.AMOUNT_ICE).getDoubleValue();
	}
	
	public double getAmountNet()
	{
		return this.getField(Field.AMOUNT_NET).getDoubleValue();
	}

	public double getAmountWithdrawals()
	{
		return this.getField(Field.AMOUNT_WITHDRAWALS).getDoubleValue();
	}
	
	public double getAmountDeletions()
	{
		return this.getField(Field.AMOUNT_DELETIONS).getDoubleValue();
	}
	
	public double getAmountDepositsForDeletion()
	{
		return this.getField(Field.AMOUNT_DELETION_DEPOSITS).getDoubleValue();
	}
	
	public double getAmountWithdrawalsForDeletion()
	{
		return this.getField(Field.AMOUNT_DELETION_WITHDRAWALS).getDoubleValue();
	}
	
	public int getCountDepositsForDeletion()
	{
		return this.getField(Field.COUNT_DEPOSITS_DELETIONS).getIntegerValue();
	}
	
	public int getCountWithdrawalsForDeletion()
	{
		return this.getField(Field.COUNT_WITHDRAWALS_DELETIONS).getIntegerValue();
	}
		
	public int getCountDeposits()
	{
		return this.getField(Field.COUNT_DEPOSITS).getIntegerValue();
	}
	
	public int getCountICE()
	{
		return this.getField(Field.COUNT_ICE).getIntegerValue();
	}
	
	public int getCountNet()
	{
		return this.getField(Field.COUNT_NET).getIntegerValue();
	}
	
	public int getCountWithdrawals()
	{
		return this.getField(Field.COUNT_WITHDRAWALS).getIntegerValue();
	}
	
	public int getCountDeletions()
	{
		return this.getField(Field.COUNT_DELETIONS).getIntegerValue();
	}
}
