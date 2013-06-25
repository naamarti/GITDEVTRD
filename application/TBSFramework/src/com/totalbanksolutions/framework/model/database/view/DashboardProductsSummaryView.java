package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardProductsSummaryView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String RECORD_ID				= "dashboardRecordID";
	public final String NAME					= "dashboardProductName";
	public final String COUNT					= "dashboardProductCount";
	public final String TOTAL					= "dashboardProductTotal";
	
	public final String STATUS					= "dashboardProductStatus";
	public final String FDIC_LIMIT              = "dashboardProductFDICLimit";
	public final String SI_DEFAULT              = "dashboardProductSIDefault";
	


	public DashboardProductsSummaryView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.RECORD_ID,				"RecordID",                 DatabaseDataType.DECIMAL_LONGINT,   0,		"Record ID",				"Record ID"							);
		this.addColumn( this.NAME,					"Name",						DatabaseDataType.CHAR,              40,		"Name",  					""									);
		this.addColumn( this.COUNT,					"Count",					DatabaseDataType.DECIMAL_LONGINT,   0,		"# of Accts",				"Number of accounts per Product"	);
		this.addColumn( this.TOTAL,					"TotalBalance",				DatabaseDataType.DECIMAL_AMOUNT,    0,		"Product Balance",			"Balance Per Product"				);

		this.addColumn( this.STATUS,                "Status",                   DatabaseDataType.CHAR,              0,		"Status",			        "Status"				            );
		this.addColumn( this.FDIC_LIMIT,            "FDICLimit",				DatabaseDataType.DECIMAL_AMOUNT,    0,		"FDIC Limit",			    "FDIC Limit max for Allocation"		);
		this.addColumn( this.SI_DEFAULT,            "SiDefault",				DatabaseDataType.BIT,               0,		"Default",			        "SI Default if none specified"		);
		
		
	}

}


