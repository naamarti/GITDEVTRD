package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerAccountBalance extends AbstractDatabaseBean<ViewCustomerAccountBalance.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccount_Balances";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, AVERAGE_BALANCE_PTD
		, BALANCE_AMOUNT
		, BALANCE_DATE
		, CASH_INTEREST_PAID
		, CASH_INTEREST_PAID_AT_PE
		, END_DATE_MRA
		, INTEREST_ACCRUED
		, INTEREST_ACCRUED_PTD
		, INTEREST_PAID_EARLY
		, INTEREST_PAID_EARLY_PTD
		, INTEREST_REINVESTED
		, INTEREST_REINVESTED_AT_PE
		, NUMBER_OF_DAYS_ACCRUED
		, PENDING_BALANCE
		, PRODUCT_NAME
		, RATE_APY_MRA_PTD
		, RATE_APY_PTD
		, RATE_PAID_LAST_ACCRUAL
		, START_DATE_MRA
		;
	}

	public ViewCustomerAccountBalance()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName					Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",					DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.AVERAGE_BALANCE_PTD,				"AverageBalance_PTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_AMOUNT,					"Balance",							DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.BALANCE_DATE,						"BalanceDate",						DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.CASH_INTEREST_PAID,				"InterestPaid",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.CASH_INTEREST_PAID_AT_PE,			"InterestPaid_AtPeriodEnd",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.END_DATE_MRA,						"EndDate_MRA",						DatabaseDataType.DATETIME,			0,		false);		
		super.addField(Field.INTEREST_ACCRUED,					"InterestAccrued",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_ACCRUED_PTD,				"InterestAccrued_PTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_PAID_EARLY,				"InterestPaidEarly",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_PAID_EARLY_PTD,			"InterestPaidEarly_PTD",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);		
		super.addField(Field.INTEREST_REINVESTED,				"InterestReinvested",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.INTEREST_REINVESTED_AT_PE,			"InterestReinvested_AtPeriodEnd",	DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.NUMBER_OF_DAYS_ACCRUED,			"NumberOfDaysAccrued",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PENDING_BALANCE,					"PendingBalance",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.PRODUCT_NAME,						"ProductCode",						DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.RATE_APY_MRA_PTD,					"RateAPY_MRA_PTD",					DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RATE_APY_PTD,						"RateAPY_PTD",						DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RATE_PAID_LAST_ACCRUAL,			"RatePaidInLastAccrual",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.START_DATE_MRA,					"StartDate_MRA",					DatabaseDataType.DATETIME,			0,		false);
	}
	
	public int compareTo(AbstractDatabaseBean<ViewCustomerAccountBalance.Field> obj)
	{    
		return this.getField(ViewCustomerAccountBalance.Field.ACCOUNT_NUMBER).getStringValue().compareTo(obj.getField(ViewCustomerAccountBalance.Field.ACCOUNT_NUMBER).getStringValue());
	}

	// Getters
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}

	public Double getPendingBalance()
	{
		return this.getField(Field.PENDING_BALANCE).getDoubleValue();
	}

	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}
	
	public void setPendingBalance(double n)
	{
		this.getField(Field.PENDING_BALANCE).setValue(n);
	}
	
}
