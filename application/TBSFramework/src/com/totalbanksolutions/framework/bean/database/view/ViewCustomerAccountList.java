package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.database.table.Program.Field;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewCustomerAccountList extends AbstractDatabaseBean<ViewCustomerAccountList.Field>
{		
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		AccountNumber
		
	}

	public ViewCustomerAccountList()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName				Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.AccountNumber,				"AccountNumber",				DatabaseDataType.CHAR,				20,		false);
		
	}
	
	public String getAccount()
	{
		return this.getField(Field.AccountNumber).getStringValue();
	}
	

}
