package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 */
public class ViewProcessCashInterestTotals extends AbstractDatabaseBean<ViewProcessCashInterestTotals.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  COUNT_CASH_INTEREST_TRANSACTIONS
		, COUNT_INTEREST_TRANSACTIONS_NET
		, COUNT_REINVESTED_INTEREST_TRANSACTIONS
		, TOTAL_CASH_INTEREST_TRANSACTIONS
		, TOTAL_INTEREST_TRANSACTIONS_NET
		, TOTAL_REINVESTED_INTEREST_TRANSACTIONS
		;
	}

	public ViewProcessCashInterestTotals()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName									Database_FieldName					Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.COUNT_CASH_INTEREST_TRANSACTIONS,			"CashInterestTransactionsCount",		DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_INTEREST_TRANSACTIONS_NET,			"InterestTransactionsNetCount",		    DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_REINVESTED_INTEREST_TRANSACTIONS,	"ReinvestedInterestTransactionsCount",	DatabaseDataType.INT,   			0,   	false);
		super.addField(Field.TOTAL_CASH_INTEREST_TRANSACTIONS,			"CashInterestTransactionsTotal",		DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_INTEREST_TRANSACTIONS_NET,			"InterestTransactionsNetTotal",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_REINVESTED_INTEREST_TRANSACTIONS,	"ReinvestedInterestTransactionsTotal",	DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}

	// Getters
	public int getCountCashInterest()
	{
		return this.getField(Field.COUNT_CASH_INTEREST_TRANSACTIONS).getIntegerValue();
	}

	public int getCountInterestNet()
	{
		return this.getField(Field.COUNT_INTEREST_TRANSACTIONS_NET).getIntegerValue();
	}

	public int getCountReinvestedInterest()
	{
		return this.getField(Field.COUNT_REINVESTED_INTEREST_TRANSACTIONS).getIntegerValue();
	}
	
	public Double getTotalCashInterest()
	{
		return this.getField(Field.TOTAL_CASH_INTEREST_TRANSACTIONS).getDoubleValue();
	}

	public Double getTotalInterestNet()
	{
		return this.getField(Field.TOTAL_INTEREST_TRANSACTIONS_NET).getDoubleValue();
	}

	public Double getTotalReinvestedInterest()
	{
		return this.getField(Field.TOTAL_REINVESTED_INTEREST_TRANSACTIONS).getDoubleValue();
	}

}