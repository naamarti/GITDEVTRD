package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author dmerkel
 */
public class ViewProductMapping extends AbstractDatabaseBean<ViewProductMapping.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  BRANCH_FROM_NAME
		, BRANCH_TO_NAME
		, IS_SOURCE_INSTITUTION_DEFAULT
		, PRODUCT_ID
		, SOURCE_PRODUCT_CODE
	}

	public ViewProductMapping()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BRANCH_FROM_NAME,					"BranchFromName",				DatabaseDataType.CHAR,				8,		false);	
		super.addField(Field.BRANCH_TO_NAME,					"BranchToName",					DatabaseDataType.CHAR,				8,		false);	
		super.addField(Field.IS_SOURCE_INSTITUTION_DEFAULT,		"IsSourceInstDefault",			DatabaseDataType.BIT,				0,		false);	
		super.addField(Field.PRODUCT_ID,						"Product_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.SOURCE_PRODUCT_CODE,				"SourceProductCode",			DatabaseDataType.CHAR,				8,		false);	
	}

	// Getters
	public String getBranchFromName()
	{
		return this.getField(Field.BRANCH_FROM_NAME).getStringValue();
	}
	
	public String getBranchToName()
	{
		return this.getField(Field.BRANCH_TO_NAME).getStringValue();
	}
	
	public boolean isSourceInstitutionDefault()
	{
		return this.getField(Field.IS_SOURCE_INSTITUTION_DEFAULT).getBooleanValue();
	}
	
	public long getProductId()
	{
		return this.getField(Field.PRODUCT_ID).getLongValue();
	}
	
	public String getSourceProductCode()
	{
		return this.getField(Field.SOURCE_PRODUCT_CODE).getStringValue();
	}
}
