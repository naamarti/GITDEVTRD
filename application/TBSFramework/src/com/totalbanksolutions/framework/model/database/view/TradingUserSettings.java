package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.table.AppUser.Field;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Created:  16 Jan 2013 - DJM
 * =================================================================================================
 */
public class TradingUserSettings extends DefaultModelTable
{	
	public final String USER_ID							= "userId";
	public final String DISCLAIMER_APPROVED				= "disclaimerApproved";
	
	public TradingUserSettings()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.USER_ID,				"User_FK",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"User Id",					"User Id"																		);
		this.addColumn( this.DISCLAIMER_APPROVED,	"IsDisclaimerApproved",		DatabaseDataType.BIT,	 			0,		"Disclaimer Approved?",		"Is Disclaimer Approved or Not"													);
	}


}


