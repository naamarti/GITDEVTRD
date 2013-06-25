package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 *            24 Aug 2012 NM #1838: find reports by envelope 
 */
public class Envelopes extends AbstractDatabaseBean<Envelopes.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Envelopes";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 DEPOSIT_REPORTS		                	( "DepositReports"    			  )
		,OMNIBUS_REPORTS        			   		( "OmnibusReport"    			  )
		,SETTLEMENT_REPORT      					( "SettlementReport"              ) 
		,RATE_CHANGE     							( "RateChange" 					  )
		,TRANSFER_INSTRUCTIONS     					( "Transfer Instructions" 		  )
		,OFFSET_INSTRUCTIONS               			( "Offset Instructions"           )
		,OVER_FDIC      							( "Over FDIC"               	  )
		,OVER_FDIC_ACCOUNT                    		( "Over FDIC Account"             )
		,SWEEP_REPORTS    							( "Sweep" 				          )
		,PROGRAM_SUMMARY   							( "Program Summary"		          )
		,SWEEP_EXPECTED_SETTLEMENT_REPORTS        	( "Sweep With Expected Settlement")
		,SWEEP_BOTH_SETTLEMENT_REPORTS          	( "Sweep With All Settlement"     )
		,LATE_SWEEP_REPORTS               			( "Late Sweep Reports"            )
		,BALANCE_SUMMARY               			    ( "Balance Summary"               )
		,FEE_ACTIVITY               			    ( "Fee Activity"                  )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}
	
	
	public enum Field
	{
		 BODY_TEXT
		,ENVELOPE_ID
		,ENVELOPE_TYPE_ID
		,NAME
		,SUBJECT_LINE	
		;
	}

	public Envelopes()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------
		// 					ConstantName             	Database_FieldName              	Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.BODY_TEXT,					"BodyText",							DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.ENVELOPE_ID,				"Envelope_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.ENVELOPE_TYPE_ID,			"EnvelopeType_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.NAME,						"Name",								DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.SUBJECT_LINE,				"SubjectLine",						DatabaseDataType.CHAR,				255,	false);
		;
	}
	
	// Getters
	public String getBodyText()
	{
		return this.getField(Field.BODY_TEXT).getStringValue();
	}
	
	public long getEnvelopeId()
	{
		return this.getField(Field.ENVELOPE_ID).getLongValue();
	}

	public long getEnvelopeTypeId()
	{
		return this.getField(Field.ENVELOPE_TYPE_ID).getLongValue();
	}
	
	public String getName()
	{
		return this.getField(Field.NAME).getStringValue();
	}
	
	public String getSubjectLine()
	{
		return this.getField(Field.SUBJECT_LINE).getStringValue();
	}

	// Setters
	public void setBodyText(String s)
	{
		this.getField(Field.BODY_TEXT).setValue(s);
	}

	public void setEnvelopeTypeId(long i)
	{
		this.getField(Field.ENVELOPE_TYPE_ID).setValue(i);
	}
	
	public void setName(String s)
	{
		this.getField(Field.NAME).setValue(s);
	}
	
	public void setSubjectLine(String s)
	{
		this.getField(Field.SUBJECT_LINE).setValue(s);
	}
	
	
	
	
}
