package com.totalbanksolutions.trading.web.model;

import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.enums.JavaDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class LoginData extends DefaultModelTable
{
	public final String USER_NAME					= "userName";
	public final String PASSWORD					= "password";
	public final String CONFIRM_PASSWORD			= "confirmPassword";	
	public final String CHALLENGE_QUESTION			= "challengeQuestion";
	public final String CHALLENGE_QUESTION_ID		= "challengeQuestionId";

	public LoginData()
	{
		this.setDatabaseName( Databases.COMMON );
		initializeColumns();
	}

	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					JavaDataType				Size	Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.USER_NAME,				JavaDataType.STRING,		30,		"User Name",				"User name for login" ); 
		this.addColumn( this.PASSWORD,				JavaDataType.STRING,		30,		"Password",					"Password for login" );
		this.addColumn( this.CONFIRM_PASSWORD,		JavaDataType.STRING,		30,		"Confirm Password",			"Confirm Password for login" );
		this.addColumn( this.CHALLENGE_QUESTION,	JavaDataType.STRING,		100,	"Challenge Question",		"Security Question for login" );
		this.addColumn( this.CHALLENGE_QUESTION_ID,	JavaDataType.LONG,			0,		"Challenge Question ID",	"Security Question ID" );
	}

}

