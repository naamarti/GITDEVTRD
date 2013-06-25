package com.totalbanksolutions.framework.bean.database.view;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ViewAppUserSession extends AbstractDatabaseBean<ViewAppUserSession.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppSession";	
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	public enum Field
	{
		 CONTACT_ID
		,IS_LOCKED
		,IS_MANDATORY_CONTACT_INFO_SET
		,IS_PASSWORD_MATCH
		,IS_PASSWORD_RESET
		,IS_SECRET_QUESTIONS_SET
		,LOGIN_FAILED_ATTEMPTS
		,LOGIN_STATUS
		,NAVIGATION_ID
		,PASSWORD_CHANGE_DATE_TIME
		,SESSION_ID
		,SESSION_KEY
		,USER_ID
		,USER_NAME	
		,USER_PASSWORD
		,WORKING_DATE
		,WORKING_PROGRAM_ID
		,WORKING_SOURCE_INST_ID		
		;
	}

	public ViewAppUserSession()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.CONTACT_ID,						"Contact_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.IS_LOCKED,							"IsLocked",						DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_MANDATORY_CONTACT_INFO_SET,		"IsMandatoryContactInfoSet",	DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_PASSWORD_MATCH,					"IsPasswordMatch",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_PASSWORD_RESET,					"IsPasswordReset",				DatabaseDataType.BIT,				0,		false);
		super.addField(Field.IS_SECRET_QUESTIONS_SET,			"IsSecretQuestionsSet",			DatabaseDataType.BIT,				0,		false);
		super.addField(Field.LOGIN_FAILED_ATTEMPTS,				"LoginFailedAttempts",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.LOGIN_STATUS,						"LoginStatus",					DatabaseDataType.INT,				0,		false);
		super.addField(Field.NAVIGATION_ID,						"Navigation_FK",				DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PASSWORD_CHANGE_DATE_TIME,			"PasswordChangeDateTime",		DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.SESSION_ID,						"Session_PK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.SESSION_KEY,						"SessionKey",					DatabaseDataType.CHAR,				40,		false);
		super.addField(Field.USER_ID,							"User_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.USER_NAME,							"UserName",						DatabaseDataType.CHAR,				30,		false);	
		super.addField(Field.USER_PASSWORD,						"UserPassword",					DatabaseDataType.CHAR,				40,		false);
		super.addField(Field.WORKING_DATE,						"WorkingDate",					DatabaseDataType.DATETIME,			0,		false);
		super.addField(Field.WORKING_PROGRAM_ID,				"WorkingProgram_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.WORKING_SOURCE_INST_ID,			"WorkingSourceInstitution_FK",	DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		
	}

	// Getters
	public long getContactId()
	{
		return this.getField(Field.CONTACT_ID).getLongValue();
	}
	
	public boolean isLocked()
	{
		return this.getField(Field.IS_LOCKED).getBooleanValue();
	}

	public boolean isMandatoryContactInfoSet()
	{
		return this.getField(Field.IS_MANDATORY_CONTACT_INFO_SET).getBooleanValue();
	}
	
	public boolean isPasswordMatch()
	{
		return this.getField(Field.IS_PASSWORD_MATCH).getBooleanValue();
	}
	
	public boolean isPasswordReset()
	{
		return this.getField(Field.IS_PASSWORD_RESET).getBooleanValue();
	}
	
	public boolean isSecretQuestionsSet()
	{
		return this.getField(Field.IS_SECRET_QUESTIONS_SET).getBooleanValue();
	}

	public int getLoginFailedAttempts()
	{
		return this.getField(Field.LOGIN_FAILED_ATTEMPTS).getIntegerValue();
	}
	
	public long getNavigationId()
	{
		return this.getField(Field.NAVIGATION_ID).getLongValue();
	}

	public Date getPasswordChangeDateTime()
	{
		return this.getField(Field.PASSWORD_CHANGE_DATE_TIME).getDateValue();
	}
	
	public String getSessionKey()
	{
		return this.getField(Field.SESSION_KEY).getStringValue();
	}
	
	public long getUserId()
	{
		return this.getField(Field.USER_ID).getLongValue();
	}
	
	public String getUserName()
	{
		return this.getField(Field.USER_NAME).getStringValue();
	}
	
	public String getUserPassword()
	{
		return this.getField(Field.USER_PASSWORD).getStringValue();
	}

	public Date getWorkingDate()
	{
		return this.getField(Field.WORKING_DATE).getDateValue();
	}
	
	public long getWorkingProgramId()
	{
		return this.getField(Field.WORKING_PROGRAM_ID).getLongValue();
	}
	
	public long getWorkingSourceInstitutionId()
	{
		return this.getField(Field.WORKING_SOURCE_INST_ID).getLongValue();
	}

	// Setters
	public void setMandatoryContactInfo(boolean b)
	{
		this.getField(Field.IS_MANDATORY_CONTACT_INFO_SET).setValue(b);
	}
	
	public void setNavigationId(long n)
	{
		this.getField(Field.NAVIGATION_ID).setValue(n);
	}
	
	public void setPasswordChangeDateTime(Date d)
	{
		this.getField(Field.PASSWORD_CHANGE_DATE_TIME).setValue(d);
	}

	public void setPasswordReset(boolean b)
	{
		this.getField(Field.IS_PASSWORD_RESET).setValue(b);
	}

	public void setSecretQuestionsSet(boolean b)
	{
		this.getField(Field.IS_SECRET_QUESTIONS_SET).setValue(b);
	}
	
	public void setSessionKey(String s)
	{
		this.getField(Field.SESSION_KEY).setValue(s);
	}
	
	public void setUserId(long n)
	{
		this.getField(Field.USER_ID).setValue(n);
	}

	public void setUserName(String s)
	{
		this.getField(Field.USER_NAME).setValue(s);
	}
	
	public void setUserPassword(String s)
	{
		this.getField(Field.USER_PASSWORD).setValue(s);
	}
	
	public void setWorkingDate(Date d)
	{
		this.getField(Field.WORKING_DATE).setValue(d);
	}
	
	public void setWorkingProgramId(long n)
	{
		this.getField(Field.WORKING_PROGRAM_ID).setValue(n);
	}

	public void setWorkingSourceInstitutionId(long n)
	{
		this.getField(Field.WORKING_SOURCE_INST_ID).setValue(n);
	}
	
}
