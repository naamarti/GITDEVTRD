package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewDepositBanksUsedInPeriod extends AbstractDatabaseBean<ViewDepositBanksUsedInPeriod.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		 NUMBER_OF_BANKS
		;
	}

	public ViewDepositBanksUsedInPeriod()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		for(int i = 1; i <= 50; i++)
		{
			super.addField("BANKNAME"+i,						"BankName"+i,					DatabaseDataType.CHAR,				4,		false);	
		}
		
		super.addField(Field.NUMBER_OF_BANKS,					"NumberOfBanks",				DatabaseDataType.INT,				0,		false);	
	}
	
	// Getters
	public int getBankCount()
	{
		return this.getField(Field.NUMBER_OF_BANKS).getIntegerValue();
	}

}
