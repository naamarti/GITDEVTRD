package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 *  Created:	20 Nov 2012 DJM
 * =================================================================================================
 */
public class DashboardBankMonitorDetailView extends DefaultModelTable
{	

	public final String BANK_NAME					= "bankName";
	public final String ASSETS						= "assets";
	public final String IRA_QUALITY_SCORE			= "iraQualityScore";
	public final String REG_CAPITAL_ADEQUACY		= "regCapitalAdequacy";
	public final String IRA_SHADOW_CAMELS			= "iraShadowCamels";
	public final String T1_RATIO					= "t1Ratio";
	public final String ROA							= "roa";
	public final String ROE							= "roe";
	public final String TEXAS_RATIO					= "texasRatio";

	public DashboardBankMonitorDetailView()
	{
		this.setDatabaseName( Databases.TBS_Program );
		this.setTableName( "View_BankMonitorInfoSummary" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               				Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.BANK_NAME,				"BankName",					DatabaseDataType.CHAR,				30,		"Institution",						"Bank Name" 								);
		this.addColumn( this.ASSETS,				"Assets",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"Assets, $K",						"Bank Assets in $K" 						);
		this.addColumn( this.IRA_QUALITY_SCORE,		"IRAQualityScore",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"IRA Counterparty Quality Score",	"IRA Counterpary Quality Score" 			); 
		this.addColumn( this.REG_CAPITAL_ADEQUACY,	"RegCapitalAdequacy",		DatabaseDataType.CHAR,				50,		"Regulatory Capital Adequacy",		"Regulatory Capital Adequacy" 				);
		this.addColumn( this.IRA_SHADOW_CAMELS,		"IRAShadowCamels",			DatabaseDataType.DECIMAL_AMOUNT,	0,		"IRA Shadow CAMELS",				"IRA Shadow CAMELS" 						); 
		this.addColumn( this.T1_RATIO,				"T1Ratio",					DatabaseDataType.DECIMAL_AMOUNT,	0,		"T1 Ratio",							"T1 Ratio" 									); 
		this.addColumn( this.ROA,					"ROA",						DatabaseDataType.DECIMAL_AMOUNT,	0,		"ROA, %",							"ROA %" 									); 
		this.addColumn( this.ROE,					"ROE",						DatabaseDataType.DECIMAL_AMOUNT,	0,		"ROE, %",							"ROE %" 									); 
		this.addColumn( this.TEXAS_RATIO,			"TexasRatio",				DatabaseDataType.DECIMAL_AMOUNT,	0,		"Texas Ratio",						"Texas Ratio %" 							); 
	}

}


