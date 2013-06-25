package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardCustomerAccountBalancesView extends DefaultModelTable
{	

	public final String RECORD_ID				= "dashboardRecordID";
	//public final String ACTIVITY_TYPE			= "dashboardActivityType";
	public final String ACCOUNT_ID					= "dashboardAccountId";
	public final String EFFECTIVE_DATE			= "dashboardEffectiveDate";	
	public final String SOURCE					= "dashboardSource";
	public final String PROCESSED			    = "dashboardProcessed";
	public final String DIFF_REASON		        = "dashboardDiffReason";
	public final String DIFFERENCE  			= "dashboardDifference";



	public DashboardCustomerAccountBalancesView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.RECORD_ID,				"RecordID",                 DatabaseDataType.DECIMAL_LONGINT,	0,		"Record ID",			    "Record ID"                                );
		//this.addColumn( this.ACTIVITY_TYPE,			"ActivityType",				DatabaseDataType.CHAR,				20,		"Activity Type",  			""	                                       );
		this.addColumn( this.ACCOUNT_ID,				"AccountNumber",		    DatabaseDataType.CHAR,				20,		"Account ID",			    "account #"                                );
		this.addColumn( this.SOURCE,		        "Source",			        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Source",			        "Source Transactions requested by Broker"  );
		this.addColumn( this.PROCESSED,		        "Processed",     	        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Processed",			    "Processed Transactions"                   );
		this.addColumn( this.DIFFERENCE,		    "Difference",     	        DatabaseDataType.DECIMAL_AMOUNT,	0,		"Difference",			    "Difference of Source & Processed"         );
		this.addColumn( this.DIFF_REASON,		    "Exception",			    DatabaseDataType.CHAR,	            100,    "Reason for Difference",	"Total"                                    );
		
	}

}


