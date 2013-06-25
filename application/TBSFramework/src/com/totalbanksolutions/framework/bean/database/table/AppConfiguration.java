package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 * Modified: 04 May 2011 VC #504: DMS Access - Create "DMS Lockdown" functionality to prevent users from logging in or taking any steps
 */  
public class AppConfiguration extends AbstractDatabaseBean<AppConfiguration.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppConfiguration";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Field
	{
		 CONFIGURATION_ID
		,DB_VERSION_NUMBER
		,DEFAULT_USER_PASSWORD
		,DOTNET_VERSION_NUMBER
		,ENVIRONMENT_NAME
		,IS_MANDATORY_CONTACT_INFO_ENABLED
		,IS_TBS_INTERNAL_TEST_MODE
		,JAVA_VERSION_NUMBER
		,MIN_CHALLENGE_QUESTIONS
		,MIN_USERNAME_LENGTH
		,NUM_LOGIN_ATTEMPTS_BEFORE_LOCKOUT
		,PASSWORD_MIN_LENGTH
		,PASSWORD_NUM_CAPS
		,PASSWORD_NUM_HISTORIC_RESTRICT
		,PASSWORD_NUM_NUMERIC
		,PASSWORD_VALID_PERIOD_IN_DAYS
		,RANDOM_CHALLENGE_QUESTIONS
		,SESSION_TIMEOUT_MINUTES		
		,VERSION_NUMBER
		,WEB_LOGGING
		;
	}

	public AppConfiguration()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//				ConstantName								Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.CONFIGURATION_ID,						"AppConfiguration_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.DB_VERSION_NUMBER,						"DBVersionNumber",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.DEFAULT_USER_PASSWORD,					"DefaultUserPassword",			DatabaseDataType.CHAR,				40,		false);
		super.addField(Field.DOTNET_VERSION_NUMBER,					"DotNetVersionNumber",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.ENVIRONMENT_NAME,						"EnvironmentName",				DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.IS_MANDATORY_CONTACT_INFO_ENABLED,		"IsMandatoryContactInfoEnabled",DatabaseDataType.BIT,				0,		false);		
		super.addField(Field.IS_TBS_INTERNAL_TEST_MODE,				"IsTBSInternalTestMode",		DatabaseDataType.BIT,				0,		false);		
		super.addField(Field.JAVA_VERSION_NUMBER,					"JavaVersionNumber",			DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.MIN_CHALLENGE_QUESTIONS,				"MinChallengeQuestions",		DatabaseDataType.INT,				0,		false);
		super.addField(Field.MIN_USERNAME_LENGTH,					"MinUsernameLength",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.NUM_LOGIN_ATTEMPTS_BEFORE_LOCKOUT,		"NumLoginAttemptsBeforeLockout",DatabaseDataType.INT,				0,		false);		
		super.addField(Field.PASSWORD_MIN_LENGTH,					"PasswordMinLength",			DatabaseDataType.INT,				0,		false);
		super.addField(Field.PASSWORD_NUM_CAPS,						"PasswordNumCaps",				DatabaseDataType.INT,				0,		false);		
		super.addField(Field.PASSWORD_NUM_HISTORIC_RESTRICT,		"PasswordNumHistoricRestrict",	DatabaseDataType.INT,				0,		false);		
		super.addField(Field.PASSWORD_NUM_NUMERIC,					"PasswordNumNumeric",			DatabaseDataType.INT,				0,		false);		
		super.addField(Field.PASSWORD_VALID_PERIOD_IN_DAYS,			"PasswordValidPeriodInDays",	DatabaseDataType.INT,				0,		false);		
		super.addField(Field.RANDOM_CHALLENGE_QUESTIONS,			"RandomChallengeQuestions",		DatabaseDataType.INT,				0,		false);
		super.addField(Field.SESSION_TIMEOUT_MINUTES,				"SessionTimeoutMinutes",		DatabaseDataType.INT,				0,		false);		
		super.addField(Field.VERSION_NUMBER,						"VersionNumber",				DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.WEB_LOGGING,							"WebLogging",					DatabaseDataType.BIT,				0,		false);		
	}

	// Getters
	public String getDatabaseVersionNumber()
	{
		return this.getField(Field.DB_VERSION_NUMBER).getStringValue();
	}

	public String getDefaultUserPassword()
	{
		return this.getField(Field.DEFAULT_USER_PASSWORD).getStringValue();
	}

	public String getDotNetVersionNumber()
	{
		return this.getField(Field.DOTNET_VERSION_NUMBER).getStringValue();
	}
	
	public String getEnvironmentName()
	{
		return this.getField(Field.ENVIRONMENT_NAME).getStringValue();
	}

	public boolean isMandatoryContactInfoEnabled()
	{
		return this.getField(Field.IS_MANDATORY_CONTACT_INFO_ENABLED).getBooleanValue();
	}

	public boolean isTBSInternalTestMode()
	{
		return this.getField(Field.IS_TBS_INTERNAL_TEST_MODE).getBooleanValue();
	}

	public String getJavaVersionNumber()
	{
		return this.getField(Field.JAVA_VERSION_NUMBER).getStringValue();
	}
	
	public int getMinimumChallengeQuestions()
	{
		return this.getField(Field.MIN_CHALLENGE_QUESTIONS).getIntegerValue();
	}

	public int getMinimumUserNameLength()
	{
		return this.getField(Field.MIN_USERNAME_LENGTH).getIntegerValue();
	}
	
	public int getNumberOfLoginAttemptsBeforeLockout()
	{
		return this.getField(Field.NUM_LOGIN_ATTEMPTS_BEFORE_LOCKOUT).getIntegerValue();
	}
	
	public int getPasswordMinimumLength()
	{
		return this.getField(Field.PASSWORD_MIN_LENGTH).getIntegerValue();
	}
	
	public int getPasswordNumberOfCaps()
	{
		return this.getField(Field.PASSWORD_NUM_CAPS).getIntegerValue();
	}
	
	public int getPasswordNumberOfHistoricRestrict()
	{
		return this.getField(Field.PASSWORD_NUM_HISTORIC_RESTRICT).getIntegerValue();
	}
	
	public int getPasswordNumberOfNumerics()
	{
		return this.getField(Field.PASSWORD_NUM_NUMERIC).getIntegerValue();
	}
	
	public int getPasswordValidPeriodInDays()
	{
		return this.getField(Field.PASSWORD_VALID_PERIOD_IN_DAYS).getIntegerValue();
	}
	
	public int getRandomChallengeQuestions()
	{
		return this.getField(Field.RANDOM_CHALLENGE_QUESTIONS).getIntegerValue();
	}

	public int getSessionTimoutMinutes()
	{
		return this.getField(Field.SESSION_TIMEOUT_MINUTES).getIntegerValue();
	}

	public String getVersionNumber()
	{
		return this.getField(Field.VERSION_NUMBER).getStringValue();
	}

}
