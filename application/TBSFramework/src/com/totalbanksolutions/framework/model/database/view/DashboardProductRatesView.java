package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardProductRatesView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String RECORD_ID				= "dashboardProductRecordID";
	public final String NAME					= "dashboardProductName";
	public final String EFFECTIVE_DATE			= "dashboardProductEffectiveDate";
	public final String FEE_TYPE_DESCRIPTION	= "dashboardProductFeeType";	
	public final String IS_BLENDED				= "dashboardProductIsBlended";
	public final String IS_USE_TIER             = "dashboardProductIsUseTier";
	

	public DashboardProductRatesView()
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
		this.addColumn( this.EFFECTIVE_DATE,		"EffectiveDate",			DatabaseDataType.DATETIME,          0,		"Effective Date",			"Effective Date of Rate"        	);
		this.addColumn( this.FEE_TYPE_DESCRIPTION,	"FeeTypeDescription",		DatabaseDataType.CHAR,              75,		"Fee Type",		"Fee Type Description"				);
		this.addColumn( this.IS_BLENDED,            "isBlended",                DatabaseDataType.BIT,               0,		"Blended",			        "Is Rate Blended"     	            );
		this.addColumn( this.IS_USE_TIER,           "isUseTier",				DatabaseDataType.BIT,               0,		"Use Tiers",			    "Flag for using Rate Tiers"	     	);
		
		
	}

}


