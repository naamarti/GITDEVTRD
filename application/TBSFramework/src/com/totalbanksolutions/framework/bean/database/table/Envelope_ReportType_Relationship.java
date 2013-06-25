package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 * MODIFIED        22 Aug 2012 NAM  - orig
 */
public class Envelope_ReportType_Relationship extends AbstractDatabaseBean<Envelope_ReportType_Relationship.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Envelope_ReportType_Relationship";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	

	public enum Field
	{
		 RELATIONSHIP_PK
		,ENVELOPE_FK	
		,REPORT_TYPE_FK
		;
	}

	public Envelope_ReportType_Relationship()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName             	Database_FieldName              	Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.RELATIONSHIP_PK,			"Relationship_PK",				DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.ENVELOPE_FK,				"Envelope_FK",					DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		super.addField(Field.REPORT_TYPE_FK,			"ReportType_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		false);
		;
	}

	// Getters
	public Long getRelationshipId()
	{
		return this.getField(Field.RELATIONSHIP_PK).getLongValue();
	}
	public Long getEnvelopeId()
	{
		return this.getField(Field.ENVELOPE_FK).getLongValue();
	}
	public Long getReportId()
	{
		return this.getField(Field.REPORT_TYPE_FK).getLongValue();
	}
	

	// Setters
	public void setEnvelopeId(Long i)
	{
		this.getField(Field.ENVELOPE_FK).setValue(i);
	}
	
	public void setReportId(Long i)
	{
		this.getField(Field.REPORT_TYPE_FK).setValue(i);
	}
}
