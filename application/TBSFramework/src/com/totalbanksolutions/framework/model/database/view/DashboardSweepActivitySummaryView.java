package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardSweepActivitySummaryView extends DefaultModelTable
{	

	public final String RECORD_ID				= "dashboardRecordID";
	public final String ACTIVITY_TYPE			= "dashboardActivityType";
	public final String COUNT					= "dashboardCount";
	public final String TOTAL					= "dashboardTotal";
	public final String SOURCE_TOTAL			= "dashboardSourceTotal";
	public final String DIFF_TOTAL		        = "dashboardDiffTotal";
	public final String PROCESSED_TOTAL			= "dashboardProcessedTotal";



	public DashboardSweepActivitySummaryView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.RECORD_ID,				"RecordID",                 DatabaseDataType.DECIMAL_LONGINT,	0,		"Record ID",			"Record ID"                         );
		this.addColumn( this.ACTIVITY_TYPE,			"ActivityType",				DatabaseDataType.CHAR,				20,		"",  					""	                                );
		this.addColumn( this.COUNT,					"Count",					DatabaseDataType.DECIMAL_LONGINT,	0,		"Count",				"Number of accounts with activity"  );
		this.addColumn( this.TOTAL,		            "Total",			        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Total",				"Total"                             );
		this.addColumn( this.PROCESSED_TOTAL,		"Total",			        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Processed",			"Total"                             );
		this.addColumn( this.SOURCE_TOTAL,		    "Total",			        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Source",				"Total"                             );
		this.addColumn( this.DIFF_TOTAL,		    "Total",			        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Difference",			"Total"                             );
	}

}


