package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewCustomerAccountProduct extends AbstractDatabaseBean<ViewCustomerAccountProduct.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "CustomerAccounts";
	
	public enum Field
	{
		  ACCOUNT_NUMBER	
		, PRODUCT_ID
		;
	}

	public ViewCustomerAccountProduct()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.ACCOUNT_NUMBER,					"AccountNumber",				DatabaseDataType.CHAR,				20,		false);	
		super.addField(Field.PRODUCT_ID,						"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);

	}

	// Getters
	public String getAccountNumber()
	{
		return this.getField(Field.ACCOUNT_NUMBER).getStringValue();
	}
	
	public long getProductId()
	{
		return this.getField(Field.PRODUCT_ID).getLongValue();
	}

}
