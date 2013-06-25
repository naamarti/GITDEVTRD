package com.totalbanksolutions.framework.bean.database.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.bean.util.BeanResultSetMapper;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class Contact extends AbstractDatabaseBean<Contact.Field>
{		
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Contacts";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	private	AppUser			appUser			= null;
	private MailingAddress	mailingAddress	= null;
	private List<Long>		envelopeIdByDepositInstitutionList = null;
	private List<Long>		envelopeIdBySourceInstitutionList = null;
	
	public enum Field
	{
		 CONTACT_ID
		,CONTACT_TYPE
		,FULL_NAME
		,NAME_TITLE
		,FIRST_NAME
		,MIDDLE_NAME
		,LAST_NAME
		,NAME_SUFFIX
		,COMPANY_NAME
		,DEPARTMENT_NAME
		,JOB_TITLE
		,BUSINESS_PHONE
		,BUSINESS_FAX
		,MOBILE_PHONE
		,EMAIL_ADDRESS
		,MAILING_ADDRESS_ID
		,COMMENTS
		,LAST_MODIFIED_DATETIME
		,LAST_MODIFIED_BY_USER
		,IS_DELETED
		,VERSION_NUMBER
		,CONTACTINSTITUTION_TYPE
		,DEPOSIT_INSTITUTION
		,SOURCE_INSTITUTION
		;
	}

	public Contact()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.CONTACT_ID,				"Contact_PK",				DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.CONTACT_TYPE,				"ContactType",				DatabaseDataType.CHAR,					10,		false);
		super.addField(Field.FULL_NAME,					"FullName",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.NAME_TITLE,				"NameTitle",				DatabaseDataType.CHAR,					5,		false);
		super.addField(Field.FIRST_NAME,				"FirstName",				DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.MIDDLE_NAME,				"MiddleName",				DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.LAST_NAME,					"LastName",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.NAME_SUFFIX,				"NameSuffix",				DatabaseDataType.CHAR,					30,		false);
		super.addField(Field.COMPANY_NAME,				"CompanyName",				DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.DEPARTMENT_NAME,			"DepartmentName",			DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.JOB_TITLE,					"JobTitle",					DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.BUSINESS_PHONE,			"BusinessPhone",			DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.BUSINESS_FAX,				"BusinessFax",				DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.MOBILE_PHONE,				"MobilePhone",				DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.EMAIL_ADDRESS,				"EmailAddress",				DatabaseDataType.CHAR,					50,		false);
		super.addField(Field.MAILING_ADDRESS_ID,		"MailingAddress_FK",		DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.COMMENTS,					"Comments",					DatabaseDataType.CHAR,					255,	false);
		super.addField(Field.LAST_MODIFIED_DATETIME,	"LastModifiedDateTime",		DatabaseDataType.DATETIME,				0,		false);
		super.addField(Field.LAST_MODIFIED_BY_USER,		"LastModifiedByUserName",	DatabaseDataType.CHAR,					30,		false);
		super.addField(Field.IS_DELETED,				"IsDeleted",				DatabaseDataType.BIT,					0,		false);
		super.addField(Field.VERSION_NUMBER,			"VersionNumber",			DatabaseDataType.INT,					0,		false);
		super.addField(Field.CONTACTINSTITUTION_TYPE,	"ContactInstitutionType_FK",DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.DEPOSIT_INSTITUTION,		"DepositInstitution_FK",	DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.SOURCE_INSTITUTION,		"SourceInstitution_FK",		DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

	public Contact(String[] bindList)
	{
		this();
		this.setBindList(bindList);
	}
	
	@Override
	public BeanResultSetMapper getBindObject(String s)
	{
		if(s.equalsIgnoreCase("AppUser")) return this.getAppUser();
		if(s.equalsIgnoreCase("MailingAddress")) return this.getMailingAddress();
		return null;
	}
	
	public AppUser getAppUser() {
		if(this.appUser == null) this.appUser = new AppUser();		
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public MailingAddress getMailingAddress() {
		if(this.mailingAddress == null) this.mailingAddress = new MailingAddress();
		return this.mailingAddress;
	}

	public void setMailingAddress(MailingAddress mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getEnvelopeIdByDepositInstitutionDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getEnvelopeIdByDepositInstitutionList().toArray());
		list = list.replace(", ", "][");
		return list;
	}

	public String getEnvelopeIdBySourceInstitutionDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getEnvelopeIdBySourceInstitutionList().toArray());
		list = list.replace(", ", "][");
		return list;
	}

	public List<Long> getEnvelopeIdByDepositInstitutionList() 
	{
		if(this.envelopeIdByDepositInstitutionList == null) this.envelopeIdByDepositInstitutionList = new ArrayList<Long>();
		return this.envelopeIdByDepositInstitutionList;
	}

	public List<Long> getEnvelopeIdBySourceInstitutionList() 
	{
		if(this.envelopeIdBySourceInstitutionList == null) this.envelopeIdBySourceInstitutionList = new ArrayList<Long>();
		return this.envelopeIdBySourceInstitutionList;
	}

	public void setEnvelopeIdByDepositInstitutionList(List<Long> envelopeIdByDepositInstitutionList) 
	{
		this.envelopeIdByDepositInstitutionList = envelopeIdByDepositInstitutionList;
	}

	public void setEnvelopeIdBySourceInstitutionList(List<Long> envelopeIdBySourceInstitutionList) 
	{
		this.envelopeIdBySourceInstitutionList = envelopeIdBySourceInstitutionList;
	}

	// Getters
	public String getFullName()
	{
		return this.getField(Field.FULL_NAME).getStringValue();
	}
}
