package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  05 Dec 2012 VC #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class FeesByProductView extends DefaultModelTable
{	

	public final String PRODUCT_NAME			= "productName";
	public final String BALANCE					= "balance";
	public final String CUSTOMER_INTEREST		= "customerInterest";
	public final String TOTAL_FEE				= "totalFee";
	public final String SERVICE_FEE				= "serviceFee";
	public final String SI_FEE					= "sourceInstitutionFee";


	public FeesByProductView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.PRODUCT_NAME,			"ProductName",				DatabaseDataType.CHAR,              40,		"Product",  				"Product"							);
		this.addColumn( this.BALANCE,				"Balance",					DatabaseDataType.DECIMAL_AMOUNT,   	0,		"Balance",					"Balance"							);
		this.addColumn( this.CUSTOMER_INTEREST,		"CustomerInterest",			DatabaseDataType.DECIMAL_AMOUNT,   	0,		"Customer Interest",		"Customer Interest"					);
		this.addColumn( this.TOTAL_FEE,				"TotalFee",					DatabaseDataType.DECIMAL_AMOUNT,   	0,		"Total Fee",				"Total Fee"							);
		this.addColumn( this.SERVICE_FEE,			"ServiceFee",				DatabaseDataType.DECIMAL_AMOUNT,   	0,		"Processor Fee",			"Processor Fee"						);
		this.addColumn( this.SI_FEE,				"SIFee",					DatabaseDataType.DECIMAL_AMOUNT,   	0,		"SI Fee",					"SI Fee"							);
		
		
	}

}


