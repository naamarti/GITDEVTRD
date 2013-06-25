package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountBalance.Field;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewAcctBalancesForTransLoader extends AbstractDatabaseBean<ViewAcctBalancesForTransLoader.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 ACCOUNT_NUMBER	
		,INITIAL_BAL_AMT
		,NET_AMT
		,NUM_DEPOSITS
		,NUM_WITHDRAWALS
		//,PENDING_BALANCE
		,RUNNING_BAL_AMT
		,RUNNING_TOTAL_DEP_4_WITH_OFFSET
		,RUNNING_TOTAL_WITH_4_DEP_OFFSET
		,TOTAL_DEP
		,TOTAL_WITH
	}

	public ViewAcctBalancesForTransLoader()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.INITIAL_BAL_AMT,					"PendingBalance",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.NET_AMT,							"",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.NUM_DEPOSITS,						"",								DatabaseDataType.INT,				0,		false);
		super.addField(Field.NUM_WITHDRAWALS,					"",								DatabaseDataType.INT,				0,		false);
		super.addField(Field.RUNNING_BAL_AMT,					"PendingBalance",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RUNNING_TOTAL_DEP_4_WITH_OFFSET,	"",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.RUNNING_TOTAL_WITH_4_DEP_OFFSET,	"",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_DEP,							"",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
		super.addField(Field.TOTAL_WITH,						"",								DatabaseDataType.DECIMAL_AMOUNT,	0,		false);
	}

	// Getters
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}
	
	public Double getInitialBalanceAmount()
	{
		return this.getField(Field.INITIAL_BAL_AMT).getDoubleValue();
	}

	public Double getNetAmount()
	{
		return this.getField(Field.NET_AMT).getDoubleValue();
	}

	public Integer getNumberOfDeposits()
	{
		return this.getField(Field.NUM_DEPOSITS).getIntegerValue();
	}
	
	public Integer getNumberOfWithdrawals()
	{
		return this.getField(Field.NUM_WITHDRAWALS).getIntegerValue();
	}

	public Double getRunningBalanceAmount()
	{
		return this.getField(Field.RUNNING_BAL_AMT).getDoubleValue();
	}

	public Double getRunningTotalDepositsForWithdrawalOffsetAmount()
	{
		return this.getField(Field.RUNNING_TOTAL_DEP_4_WITH_OFFSET).getDoubleValue();
	}
	
	public Double getRunningTotalWithdrawalsForDepositOffsetAmount()
	{
		return this.getField(Field.RUNNING_TOTAL_WITH_4_DEP_OFFSET).getDoubleValue();
	}

	public Double getTotalDepositAmount()
	{
		return this.getField(Field.TOTAL_DEP).getDoubleValue();
	}
	
	public Double getTotalWithdrawalAmount()
	{
		return this.getField(Field.TOTAL_WITH).getDoubleValue();
	}

	// Setters
	public void setAccountNumber(String s)
	{
		this.getField(Field.ACCOUNT_NUMBER).setValue(s);
	}
	
	public void setInitialBalanceAmount(double n)
	{
		this.getField(Field.INITIAL_BAL_AMT).setValue(n);
	}
	
	public void setRunningBalanceAmount(double n)
	{
		this.getField(Field.RUNNING_BAL_AMT).setValue(n);
	}

	public void setRunningTotalDepositsForWithdrawalOffsetAmount(double n)
	{
		this.getField(Field.RUNNING_TOTAL_DEP_4_WITH_OFFSET).setValue(n);
	}

	public void setRunningTotalWithdrawalsForDepositOffsetAmount(double n)
	{
		this.getField(Field.RUNNING_TOTAL_WITH_4_DEP_OFFSET).setValue(n);
	}
	
	public void setTotalDepositAmount(double n)
	{
		this.getField(Field.TOTAL_DEP).setValue(n);
	}

	public void setTotalWithdrawalAmount(double n)
	{
		this.getField(Field.TOTAL_WITH).setValue(n);
	}

}
