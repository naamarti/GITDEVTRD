package com.totalbanksolutions.framework.model.database.table;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.bean.database.table.Contact.Field;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class ContactsTable extends DefaultModelTable
{	
	public final String CONTACT_ID					= "contactId";
	public final String CONTACT_TYPE				= "contactTypeId";
	public final String FULL_NAME					= "fullName";
	public final String NAME_TITLE					= "nameTitle";
	public final String FIRST_NAME 					= "firstName";
	public final String MIDDLE_NAME					= "middleName";
	public final String LAST_NAME					= "lastName";
	public final String NAME_SUFFIX					= "nameSuffix";
	public final String COMPANY_NAME				= "companyName";
	public final String DEPARTMENT_NAME				= "departmentName";
	public final String JOB_TITLE					= "jobTitle";
	public final String BUSINESS_PHONE				= "businessPhone";
	public final String BUSINESS_FAX				= "businessFax";
	public final String MOBILE_PHONE				= "mobilePhone";
	public final String EMAIL_ADDRESS 				= "emailAddress";
	public final String MAILING_ADDRESS_ID 			= "mailingAddressId";
	public final String COMMENTS					= "comments";
	public final String LAST_MODIFIED_DATETIME		= "lastModifiedDateTime";
	public final String LAST_MODIFIED_BY_USER		= "lastModifiedByUser";
	public final String IS_DELETED					= "isDeleted";
	public final String VERSION_NUMBER				= "versionNumber";
	public final String CONTACTINSTITUTION_TYPE		= "contactInstitutionTypeId";
	public final String DEPOSIT_INSTITUTION			= "depositInstitutionId";
	public final String SOURCE_INSTITUTION			= "sourceInstitutionId";

	public ContactsTable()
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
		
		this.addColumn( this.CONTACT_ID,			"Contact_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		"",							"");
		this.addColumn( this.CONTACT_TYPE,			"ContactType",				DatabaseDataType.CHAR,				10,		"Contact Type",				"");
		this.addColumn( this.FULL_NAME,				"FullName",					DatabaseDataType.CHAR,				50,		"Contact Full Name",		"Full name of the Contact");
		this.addColumn( this.NAME_TITLE,			"NameTitle",				DatabaseDataType.CHAR,				5,		"Contact Title",			"Contact Name Title");
		this.addColumn( this.FIRST_NAME,			"FirstName",				DatabaseDataType.CHAR,				50,		"First Name",				"Enter First Name of the Contact");
		this.addColumn( this.MIDDLE_NAME,			"MiddleName",				DatabaseDataType.CHAR,				50,		"Middle Name",				"Enter Middle Name of the Contact");
		this.addColumn( this.LAST_NAME,				"LastName",					DatabaseDataType.CHAR,				50,		"Last Name",				"Enter Last Name of the Contact");
		this.addColumn( this.NAME_SUFFIX,			"NameSuffix",				DatabaseDataType.CHAR,				30,		"Suffix",					"Suffix");
		this.addColumn( this.COMPANY_NAME,			"CompanyName",				DatabaseDataType.CHAR,				50,		"Company Name",				"Company Name for Contact");
		this.addColumn( this.DEPARTMENT_NAME,		"DepartmentName",			DatabaseDataType.CHAR,				50,		"Department Name",			"Department Name for Contact");
		this.addColumn( this.JOB_TITLE,				"JobTitle",					DatabaseDataType.CHAR,				50,		"Job Title",				"Job Title for Contact");
		this.addColumn( this.BUSINESS_PHONE,		"BusinessPhone",			DatabaseDataType.CHAR,				50,		"Business Phone",			"Contacts Business Phone Number");
		this.addColumn( this.BUSINESS_FAX,			"BusinessFax",				DatabaseDataType.CHAR,				50,		"Business Fax",				"Contacts Business Fax Number");
		this.addColumn( this.MOBILE_PHONE,			"MobilePhone",				DatabaseDataType.CHAR,				50,		"Mobile Phone",				"Contacts Mobile Phone Number");
		this.addColumn( this.EMAIL_ADDRESS,			"EmailAddress",				DatabaseDataType.CHAR,				50,		"Email Address",			"Contacts Email Address");
		this.addColumn( this.MAILING_ADDRESS_ID,	"MailingAddress_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		"Mailling Address",			"Contacts Mailing Address");
		this.addColumn( this.COMMENTS,				"Comments",					DatabaseDataType.CHAR,				255,	"Comments",					"Enter Any Comments for this Contact");
		this.addColumn( this.LAST_MODIFIED_DATETIME,"LastModifiedDateTime",		DatabaseDataType.DATETIME,			0,		"Last Modified Date",		"");
		this.addColumn( this.LAST_MODIFIED_BY_USER,	"LastModifiedByUserName",	DatabaseDataType.CHAR,				30,		"Last Modified by User",	"");
		this.addColumn( this.IS_DELETED,			"IsDeleted",				DatabaseDataType.BIT,				0,		"Active",		    		"Contacted Deleted?");
		this.addColumn( this.VERSION_NUMBER,		"VersionNumber",			DatabaseDataType.INT,				0,		"Version Number",			"");
		this.addColumn( this.CONTACTINSTITUTION_TYPE,"ContactInstitutionType_FK",DatabaseDataType.DECIMAL_LONGINT,	0,		"Institution Type",			"Instititution Type");
		this.addColumn( this.DEPOSIT_INSTITUTION,	"DepositInstitution_FK",	DatabaseDataType.DECIMAL_LONGINT,	0,		"Deposit Institution",		"Deposit Institution");
		this.addColumn( this.SOURCE_INSTITUTION,	"SourceInstitution_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		"Source Institution",		"Source Institution");
	
	}

}

