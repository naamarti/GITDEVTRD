package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.bean.util.BeanResultSetMapper;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class AppUserChallengeQuestionRelationship extends AbstractDatabaseBean<AppUserChallengeQuestionRelationship.Field>
{	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "AppUser_ChallengeQuestion_Relationship";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	private ChallengeQuestion challengeQuestion = new ChallengeQuestion();
	
	public enum Field
	{
		 QUESTION_ID
		,RELATIONSHIP_ID
		,RESPONSE
		,USER_ID
		;
	}

	public AppUserChallengeQuestionRelationship()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.QUESTION_ID,						"ChallengeQuestion_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.RELATIONSHIP_ID,					"Relationship_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.RESPONSE,							"Response",						DatabaseDataType.CHAR,				100,	false);
		super.addField(Field.USER_ID,							"User_FK",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
	}

	public AppUserChallengeQuestionRelationship(String[] bindList)
	{
		this();
		this.setBindList(bindList);
	}
	
	@Override
	public BeanResultSetMapper getBindObject(String s)
	{
		if(s.equalsIgnoreCase("ChallengeQuestion")) return this.challengeQuestion;
		return null;
	}

	public ChallengeQuestion getChallengeQuestion() 
	{
		return this.challengeQuestion;
	}

	public void setChallengeQuestion(ChallengeQuestion challengeQuestion) 
	{
		this.challengeQuestion = challengeQuestion;
	}

	// Getters
	public long getQuestionId()
	{
		return this.getField(Field.QUESTION_ID).getLongValue();
	}
	
	public String getResponse()
	{
		return this.getField(Field.RESPONSE).getStringValue();
	}
	
	// Setters
	public void setQuestionId(long n)
	{
		this.getField(Field.QUESTION_ID).setValue(n);
	}

	public void setResponse(String s)
	{
		this.getField(Field.RESPONSE).setValue(s);
	}

	public void setUserId(long n)
	{
		this.getField(Field.USER_ID).setValue(n);
	}

}
