package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomer1099Report extends AbstractDatabaseBean<ViewCustomer1099Report.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccounts";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, INTEREST_PAID_YTD
	}

	public ViewCustomer1099Report()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.INTEREST_PAID_YTD,					"InterestPaid_YTD",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
	}

}
