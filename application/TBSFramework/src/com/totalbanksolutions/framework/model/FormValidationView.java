package com.totalbanksolutions.framework.model;

import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Modified:  20 Dec 2012 VC #    : 
 * =================================================================================================
 */
public class FormValidationView extends DefaultModelTable
{	
	public final String COLUMN_NAME				= "columnName";
	public final String IS_VALID				= "isValid";
	public final String MESSAGE					= "message";

	public FormValidationView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.COLUMN_NAME,		"ColumnName",					DatabaseDataType.CHAR,				100,	"",							"");
		this.addColumn( this.IS_VALID,			"IsValid",						DatabaseDataType.BIT,				0,		"",							"");
		this.addColumn( this.MESSAGE,			"ValidationMessage",			DatabaseDataType.CHAR,				500,	"",							"");
	}

}


