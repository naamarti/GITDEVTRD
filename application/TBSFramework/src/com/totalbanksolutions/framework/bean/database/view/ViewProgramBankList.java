package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author epaparatto
 */
public class ViewProgramBankList extends AbstractDatabaseBean<ViewProgramBankList.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  BANK_ADDRESS
		, BANK_CODE
		, BANK_ID
		, BANK_NAME	
		, FDIC_BANK_CODE
		, IS_PENDING_DEPOSITS
		;
	}

	public ViewProgramBankList()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BANK_ADDRESS,						"BankAddress",					DatabaseDataType.CHAR,				40,		false);	
		super.addField(Field.BANK_CODE,							"Code",							DatabaseDataType.CHAR,				4,		false);	
		super.addField(Field.BANK_ID,							"DepInst_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.BANK_NAME,							"BankName",						DatabaseDataType.CHAR,				24,		false);	
		super.addField(Field.FDIC_BANK_CODE,					"FDICCertificateNumber",		DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.IS_PENDING_DEPOSITS,				"IsPendingDeposits",			DatabaseDataType.BIT,				0,		false);	
	}

	// Getters
	public String getBankAddress()
	{
		return this.getField(Field.BANK_ADDRESS).getStringValue();
	}
	
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

	public String getFDICBankCode()
	{
		return this.getField(Field.FDIC_BANK_CODE).getStringValue();
	}
	
	public boolean isPendingDeposits()
	{
		return this.getField(Field.IS_PENDING_DEPOSITS).getBooleanValue();
	}

}
