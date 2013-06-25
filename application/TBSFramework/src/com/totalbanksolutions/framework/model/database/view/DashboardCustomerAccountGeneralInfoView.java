package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardCustomerAccountGeneralInfoView extends DefaultModelTable
{	

	public final String ACCOUNT_ID              = "dashboardAccountID";
	public final String ACCOUNT_LINK            = "dashboardAccountLink";
	public final String TAX_ID1                 = "dashboardTaxID1";
	public final String TAX_ID2                 = "dashboardTaxID2";
	public final String ACCOUNT_TYPE            = "dashboardAccountType";
	public final String PAYOUT_TYPE             = "dashboardPayoutType";
	public final String HH_BALANCE              = "dashboardHHBalance";
	//public final String REG_LINE                = "dashboardRegLine";



	public DashboardCustomerAccountGeneralInfoView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.ACCOUNT_ID,            "AccountID",                DatabaseDataType.CHAR,              20,		"ID Number",			    "ID Number"                                );
		this.addColumn( this.ACCOUNT_LINK,          "AccountLink",              DatabaseDataType.CHAR,              20,		"ID Link",			        "ID Link"                                  );
		this.addColumn( this.TAX_ID1,               "TaxID1",                   DatabaseDataType.CHAR,              20,		"TaxID1",			        "TaxID1"                                   );
		this.addColumn( this.TAX_ID2,               "TaxID2",                   DatabaseDataType.CHAR,              20,		"TaxID2",			        "TaxID2"                                   );
		this.addColumn( this.ACCOUNT_TYPE,          "AccountType",              DatabaseDataType.CHAR,              50,		"Account Type",			    "Account Type"                             );
		this.addColumn( this.PAYOUT_TYPE,           "PayoutType",               DatabaseDataType.CHAR,              50,		"Payout Type",			    "Payout Type"                              );
		this.addColumn( this.HH_BALANCE,            "HHBalance",                DatabaseDataType.DECIMAL_AMOUNT,     0,		"HH Balance",			    "HouseHolding Balance"                     );
		//this.addColumn( this.REG_LINE,              "RegInfoLine",              DatabaseDataType.CHAR,              100,	"",			                "Registration Info Line"                   );

	}

}


