package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Modified:  23 Apr 2012 VC #1449: Forecast Report - display new column "Balances after Insured Pass"
 *
 * =================================================================================================
 */
public class ViewForecastSummaryReport extends AbstractDatabaseBean<ViewForecastSummaryReport.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";

	public enum Field
	{
		  INSURANCE_NEEDED
		, OVER_FDIC_AMOUNT
		, UNDER_INSURED_AMOUNT
		;
	}

	public ViewForecastSummaryReport()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.INSURANCE_NEEDED,					"TotalInsuranceNeeded",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.OVER_FDIC_AMOUNT,					"OverFDICAmount",				DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
		super.addField(Field.UNDER_INSURED_AMOUNT,				"UnderInsuredAmount",			DatabaseDataType.DECIMAL_AMOUNT,	0,		false);	
	}
	
}
