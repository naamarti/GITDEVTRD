package com.totalbanksolutions.framework.bean.database.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.bean.util.BeanResultSetMapper;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;

public class AppUser extends AbstractDatabaseBean<AppUser.Field> 
{
	public static String DATABASE_NAME = Databases.COMMON;
	public static String TABLE_NAME = "AppUser";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	private AppUserType	appUserType					= null;
	private Contact 	contact 					= null;
	private List<Long>	roleIdList 					= null;
	private List<Long>	sourceInstitutionIdList 	= null;
	private List<Long>	depositInstitutionIdList 	= null;

	public enum Field
	{
		 USER_ID
		,USER_NAME
		,PASSWORD
		,USER_TYPE_ID
		,CONTACT_ID
		,IS_PASSWORD_RESET
		,IS_SECRET_QUESTIONS_SET
		,PASSWORD_CHANGE_DATETIME
		,LOGIN_FAILED_ATTEMPTS
		,LAST_MODIFIED_DATETIME
		,LAST_MODIFIED_BY_USER
		,IS_DELETED
		,VERSION_NUMBER
		,IS_MANDATORY_CONTACT_INFO_SET
		,IS_LOCKED
	}
	
	public AppUser()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName					Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.USER_ID,						"User_PK",					DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.USER_NAME,						"UserName",					DatabaseDataType.CHAR,					30,		false);
		super.addField(Field.PASSWORD,						"UserPassword",				DatabaseDataType.CHAR,					40,		false);
		super.addField(Field.USER_TYPE_ID,					"UserType_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.CONTACT_ID,					"Contact_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.IS_PASSWORD_RESET,				"IsPasswordReset",			DatabaseDataType.BIT,					0,		false);
		super.addField(Field.IS_SECRET_QUESTIONS_SET,		"IsSecretQuestionsSet",		DatabaseDataType.BIT,					0,		false);
		super.addField(Field.PASSWORD_CHANGE_DATETIME,		"PasswordChangeDateTime",	DatabaseDataType.DATETIME,				0,		false);
		super.addField(Field.LOGIN_FAILED_ATTEMPTS,			"LoginFailedAttempts",		DatabaseDataType.INT,					0,		false);
		super.addField(Field.LAST_MODIFIED_DATETIME,		"LastModifiedDateTime",		DatabaseDataType.DATETIME,				0,		false);
		super.addField(Field.LAST_MODIFIED_BY_USER,			"LastModifiedByUserName",	DatabaseDataType.CHAR,					30,		false);
		super.addField(Field.IS_DELETED,					"IsDeleted",				DatabaseDataType.BIT,					0,		false);
		super.addField(Field.VERSION_NUMBER,				"VersionNumber",			DatabaseDataType.INT,					0,		false);
		super.addField(Field.IS_MANDATORY_CONTACT_INFO_SET,	"IsMandatoryContactInfoSet",DatabaseDataType.BIT,					0,		false);
		super.addField(Field.IS_LOCKED,						"IsLocked",					DatabaseDataType.BIT,					0,		false);
	}

	public AppUser(String[] bindList)
	{
		this();
		this.setBindList(bindList);
	}
	
	@Override
	public BeanResultSetMapper getBindObject(String s)
	{
		if(s.equalsIgnoreCase("Contact")) return this.getContact();
		if(s.equalsIgnoreCase("AppUserType")) return this.getAppUserType();
		return null;
	}
	
	public String getRoleIdDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getRoleIdList().toArray());
		list = list.replace(", ", "][");
		return list;
	}
	
	public String getSourceInstitutionIdDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getSourceInstitutionIdList().toArray());
		list = list.replace(", ", "][");
		return list;
	}

	public String getDepositInstitutionIdDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getDepositInstitutionIdList().toArray());
		list = list.replace(", ", "][");
		return list;
	}
	
	public AppUserType getAppUserType() 
	{
		if(this.appUserType == null) this.appUserType = new AppUserType();
		return appUserType;
	}

	public void setAppUserType(AppUserType appUserType) 
	{
		this.appUserType = appUserType;
	}

	public Contact getContact() 
	{
		if(this.contact == null) this.contact = new Contact();
		return this.contact;
	}

	public void setContact(Contact contact) 
	{
		this.contact = contact;
	}
	
	public List<Long> getRoleIdList() 
	{
		if(this.roleIdList == null) this.roleIdList = new ArrayList<Long>();
		return this.roleIdList;
	}
	
	public void setRoleIdList(List<Long> roleIdList) 
	{
		this.roleIdList = roleIdList;
	}

	public List<Long> getSourceInstitutionIdList() 
	{
		if(this.sourceInstitutionIdList == null) this.sourceInstitutionIdList = new ArrayList<Long>();
		return this.sourceInstitutionIdList;
	}

	public void setSourceInstitutionIdList(List<Long> sourceInstitutionIdList) 
	{
		this.sourceInstitutionIdList = sourceInstitutionIdList;
	}

	public List<Long> getDepositInstitutionIdList() 
	{
		if(this.depositInstitutionIdList == null) this.depositInstitutionIdList = new ArrayList<Long>();
		return this.depositInstitutionIdList;
	}

	public void setDepositInstitutionIdList(List<Long> depositInstitutionIdList) 
	{
		this.depositInstitutionIdList = depositInstitutionIdList;
	}
	
	// Getters
	public boolean isDeleted()
	{
		return this.getField(Field.IS_DELETED).getBooleanValue();
	}
	public boolean isLocked()
	{
		return this.getField(Field.IS_LOCKED).getBooleanValue();
	}
	public boolean isPasswordReset()
	{
		return this.getField(Field.IS_PASSWORD_RESET).getBooleanValue();
	}
	public boolean isSecretQuestionsSet()
	{
		return this.getField(Field.IS_SECRET_QUESTIONS_SET).getBooleanValue();
	}
	public long getUserId()
	{
		return this.getField(Field.USER_ID).getLongValue();
	}
	public String getUserName()
	{
		return this.getField(Field.USER_NAME).getStringValue();
	}

}
