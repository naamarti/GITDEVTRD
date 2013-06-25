package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewDepositInstitutionMailingAddress extends AbstractDatabaseBean<ViewDepositInstitutionMailingAddress.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DepositInstitutions";
	
	protected enum Field
	{
		 BANK_CODE
		,CITY_NAME
		,STATE_CODE
		,STATE_NAME
		;
	}

	public ViewDepositInstitutionMailingAddress()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.BANK_CODE,							"Code",							DatabaseDataType.CHAR,				4,		false);
		super.addField(Field.CITY_NAME,							"City",							DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.STATE_CODE,						"StateCode",					DatabaseDataType.CHAR,				2,		false);
		super.addField(Field.STATE_NAME,						"State",						DatabaseDataType.CHAR,				50,		false);
	}

	// Getters
	public String getBankCode()
	{
		return this.getField(Field.BANK_CODE).getStringValue();
	}
	
	public String getCityName()
	{
		return this.getField(Field.CITY_NAME).getStringValue();
	}
	
	public String getStateCode()
	{
		return this.getField(Field.STATE_CODE).getStringValue();
	}
	
	public String getStateName()
	{
		return this.getField(Field.STATE_NAME).getStringValue();
	}

}
