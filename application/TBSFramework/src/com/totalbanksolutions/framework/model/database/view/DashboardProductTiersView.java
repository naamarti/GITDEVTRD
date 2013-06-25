package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardProductTiersView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String RECORD_ID				= "dashboardProductTiersRecordID";
	public final String NAME					= "dashboardProductName";
	public final String LOWER_BOUND	            = "dashboardProductTiersLowerBound";	
	public final String UPPER_BOUND				= "dashboardProductTiersUpperBound";
	public final String INDEX				    = "dashboardProductTiersIndex";
	public final String PEG				        = "dashboardProductTiersPeg";
	public final String TOTAL_RATE				= "dashboardProductTiersTotalRate";
	public final String TIER_LABEL				= "dashboardProductTiersLabel";
	public final String SINGLE_RATE				= "dashboardProductTiersSingleRate";
	

	public DashboardProductTiersView()
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
		this.addColumn( this.LOWER_BOUND,	        "LowerBound",		        DatabaseDataType.DECIMAL_AMOUNT,    0,		"Lower Bound",		        "The Tiers Lower Bound"				);
		this.addColumn( this.UPPER_BOUND,           "UpperBound",               DatabaseDataType.DECIMAL_AMOUNT,    0,		"Upper Bound",			    "The Tiers Upper Bound"     	    );
		this.addColumn( this.INDEX,				    "RateIndex",                DatabaseDataType.CHAR,              30,		"Index",				    "Index type (ZER, EFF, etc.)"		);
		this.addColumn( this.PEG,				    "Peg",                      DatabaseDataType.DECIMAL_AMOUNT,    0,		"Peg",				        "Peg Amount to add to index"		);
		this.addColumn( this.TOTAL_RATE,			"TotalRate",				DatabaseDataType.DECIMAL_AMOUNT,    0,		"Total Rate",  				""									);
		this.addColumn( this.TIER_LABEL,	        "TierLabel",		        DatabaseDataType.CHAR,              30,		"Tier Label",		        "Label"				                );
		this.addColumn( this.SINGLE_RATE,           "SingleRate",               DatabaseDataType.DECIMAL_AMOUNT,    0,		"Single Rate",			    ""     	                            );
	}
	
	
	
	

}


