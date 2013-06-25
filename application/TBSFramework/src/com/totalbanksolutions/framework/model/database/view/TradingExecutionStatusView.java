package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  
 * =================================================================================================
 */
public class TradingExecutionStatusView extends DefaultModelTable
{	
	public final String PROCESS_DATE				= "processDate";
	public final String PROCESS_STATUS				= "processStatus";	
	public final String MODIFIED_DATETIME			= "modifiedDateTime";
	public final String USER_NAME		     		= "userName";
	public final String DETAILS						= "details";
	
	public TradingExecutionStatusView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.PROCESS_DATE,			"ProcessDate",				DatabaseDataType.DATETIME,	 		0,		"Trade Date",				"Trade Date");
		this.addColumn( this.PROCESS_STATUS,		"ProcessStatus",			DatabaseDataType.CHAR,    		 	30,		"Process Status",			"Process Status");
		this.addColumn( this.MODIFIED_DATETIME,		"ModifiedDateTime",			DatabaseDataType.DATETIME,			0,		"LastModified",				"Last Modified Date and Time");
		this.addColumn( this.USER_NAME,				"UserName",					DatabaseDataType.CHAR,	 		 	50,		"User Name",				"User Name");
		this.addColumn( this.DETAILS,	     		"Details",					DatabaseDataType.CHAR,     		 	500,	"Process Details",			"Process Details");
		
	}

}


