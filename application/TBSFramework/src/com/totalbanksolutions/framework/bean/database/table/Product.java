package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class Product extends AbstractDatabaseBean<Product.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Products";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 CUSIP_NUMBER
		,IS_BLENDED
		,IS_SOURCE_INST_DEFAULT
		,PRODUCT_ID
		,PRODUCT_NAME
		,PROGRAM_ID
		,SECURITY_NUMBER
		,SOURCE_INST_ID
		,SOURCE_PRODUCT_CODE
		,STATUS		
		;
	}

	public Product()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName			Database_FieldName				Database_DataType                   Size    IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.CUSIP_NUMBER,			"CusipNumber",					DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.IS_BLENDED,			"IsBlended",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SOURCE_INST_DEFAULT,"IsSourceInstDefault",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.PRODUCT_ID,			"Product_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PRODUCT_NAME,			"Name",							DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.PROGRAM_ID,			"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SECURITY_NUMBER,		"SecurityNumber",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SOURCE_INST_ID,		"SourceInstitution_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SOURCE_PRODUCT_CODE,	"SourceProductCode",			DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.STATUS,				"Status",						DatabaseDataType.BIT,				0,		false);
	}

	// Getters
	public String getCusipNumber()
	{
		return this.getField(Field.CUSIP_NUMBER).getStringValue();
	}

	public boolean isSourceInstitutionDefault()
	{
		return this.getField(Field.IS_SOURCE_INST_DEFAULT).getBooleanValue();
	}

	public long getProductId()
	{
		return this.getField(Field.PRODUCT_ID).getLongValue();
	}
	
	public String getProductName()
	{
		return this.getField(Field.PRODUCT_NAME).getStringValue();
	}

	public String getSecurityNumber()
	{
		return this.getField(Field.SECURITY_NUMBER).getStringValue();
	}

	public String getSourceProductCode()
	{
		return this.getField(Field.SOURCE_PRODUCT_CODE).getStringValue();
	}

	// Setters

}
