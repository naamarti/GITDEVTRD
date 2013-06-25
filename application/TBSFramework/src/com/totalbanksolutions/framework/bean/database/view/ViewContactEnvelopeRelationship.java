package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ViewContactEnvelopeRelationship extends AbstractDatabaseBean<ViewContactEnvelopeRelationship.Field>
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "";
	
	protected enum Field
	{
		  CONTACT_ID
		, ENVELOPE_ID
		, IS_SELECTED
		, NAME	
		;
	}

	public ViewContactEnvelopeRelationship()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.CONTACT_ID,						"Contact_fk",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.ENVELOPE_ID,						"Envelope_PK",					DatabaseDataType.INT,				0,		false);	
		super.addField(Field.IS_SELECTED,                       "isSelected",					DatabaseDataType.BIT,				0,		false);
		super.addField(Field.NAME,								"EnvelopeName",					DatabaseDataType.CHAR,			    50,		false);	
	}

}
