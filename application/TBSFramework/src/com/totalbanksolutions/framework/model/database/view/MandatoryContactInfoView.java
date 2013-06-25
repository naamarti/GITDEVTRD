package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class MandatoryContactInfoView extends DefaultModelTable
{	
	public final String USER_NAME					= "userName";
	public final String CONTACT_ID					= "contactId";
	public final String FULL_NAME					= "fullName";
	public final String BUSINESS_PHONE				= "businessPhone";
	public final String EMAIL_ADDRESS 				= "emailAddress";

	public MandatoryContactInfoView()
	{
		this.setDatabaseName( Databases.TBS_Common );
		this.setTableName( "Contacts" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.USER_NAME,				"UserName",					DatabaseDataType.CHAR,				30,		"User Name",				"User Name for login");
		this.addColumn( this.CONTACT_ID,			"Contact_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		"",							"");
		this.addColumn( this.FULL_NAME,				"FullName",					DatabaseDataType.CHAR,				50,		"Full Name",				"First and Last Name");
		this.addColumn( this.BUSINESS_PHONE,		"BusinessPhone",			DatabaseDataType.CHAR,				50,		"Business Phone",			"Business Phone Number");
		this.addColumn( this.EMAIL_ADDRESS,			"EmailAddress",				DatabaseDataType.CHAR,				50,		"Email Address",			"Email Address");	
	}

}

