package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardProductBanksView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String RECORD_ID				= "dashboardProductTiersRecordID";
	public final String NAME					= "dashboardProductBankName";
	public final String OPT_OUT	                = "dashboardProductBankOptOut";
	public final String OPT_IN				    = "dashboardProductBankOptIn";
	public final String FAVORED_BANK_RATING	    = "dashboardProductBankFavoredRating";
	

	public DashboardProductBanksView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.RECORD_ID,				"RecordID",                 DatabaseDataType.DECIMAL_LONGINT,   0,		"Record ID",				"Record ID"							);
		this.addColumn( this.NAME,					"Name",						DatabaseDataType.CHAR,              30,		"Name",  					""									);
		this.addColumn( this.OPT_OUT,	            "OptOut",		            DatabaseDataType.BIT,                0,		"Opt Out",		            ""				);
		this.addColumn( this.OPT_IN,                "OptIn",                    DatabaseDataType.BIT,                0,		"Opt In",			        ""     	        );
		this.addColumn( this.FAVORED_BANK_RATING,	"FavoredRating",            DatabaseDataType.CHAR,              30,		"Favored Bank Rating",		""		        );
	}
	
	
}


