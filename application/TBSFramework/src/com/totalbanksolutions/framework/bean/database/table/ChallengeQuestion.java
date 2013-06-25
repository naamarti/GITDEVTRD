package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;

/**
 * @author vcatrini
 */
public class ChallengeQuestion extends AbstractDatabaseBean<ChallengeQuestion.Field>
{	
	public static String DATABASE_NAME = Databases.COMMON;
	public static String TABLE_NAME = "ChallengeQuestion";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	public enum Field
	{
		 QUESTION_ID
		,QUESTION
		;
	}

	public ChallengeQuestion()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.QUESTION_ID,						"ChallengeQuestion_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.QUESTION,							"ChallengeQuestion",			DatabaseDataType.CHAR,				255,	false);
	}

	public long getQuestionId()
	{
		return this.getField(Field.QUESTION_ID).getLongValue();
	}

	public String getQuestion()
	{
		return this.getField(Field.QUESTION).getStringValue();
	}
	
}
