package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	27 Nov 2012 DJM
 * =================================================================================================
 */
public class DashboardContactInfoView extends DefaultModelTable
{	

	public final String FULL_NAME			= "fullName";
	public final String COMPANY_NAME		= "companyName";
	public final String BUSINESS_PHONE		= "businessPhone";
	public final String EMAIL_ADDRESS		= "emailAddress"; 
	
	public DashboardContactInfoView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_ContactInfo" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               				Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.FULL_NAME,				"FullName",					DatabaseDataType.CHAR,				50,		"Full Name",						"Full Name" 								);
		this.addColumn( this.COMPANY_NAME,			"CompanyName",				DatabaseDataType.CHAR,				50,		"Company Name",						"Company Name" 								);
		this.addColumn( this.BUSINESS_PHONE,		"BusinessPhone",			DatabaseDataType.CHAR,				50,		"Business Phone",					"Business Phone" 							);
		this.addColumn( this.EMAIL_ADDRESS,			"EmailAddress",				DatabaseDataType.CHAR,				100,	"Email Address",					"Email Address" 							);
	}

}


