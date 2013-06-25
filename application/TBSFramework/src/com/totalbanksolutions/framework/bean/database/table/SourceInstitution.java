package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * 
 * Modified:  05 Oct 2011 VC #986:  Add DMS checklist steps for each S.I. in DBTCA to generate Over FDIC reports
 *
 * =================================================================================================
 */
public class SourceInstitution extends AbstractDatabaseBean<SourceInstitution.Field>
{
	//private boolean	isSelected = false;

	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "SourceInstitutions";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 AMERICAN_NATIONAL_BANK		( "AmericanNationalBank"      )
		,ANDROSCOGGIN				( "AndroscogginBank"          )
		,BAY_TREE					( "BayTreeBank"               )
		,BBNT						( "BBNTBank"                  )
		,CIRCLE						( "CircleBank"                )
		,CLEARVIEW					( "Clearview"                 )
		,CONDON						( "CondonBank"                )
		,CORNHUSKER					( "CornhuskerBank"            )
		,CURIAN						( "Curian"                    )
		,DAVIDSON					( "Davidson"                  )
		,DB_TRUST					( "DBTrustBank"               )	
		,EAST_WEST					( "EastWestBank"              )
		,EVOLVE						( "EvolveBank"                )
		,FIRST_SOUTH_WEST			( "FSW"                       )
		,FOLIO						( "Folio"                     )
		,FRONTIER_BANK				( "FrontierBank"              )
		,HCDENISON					( "Denison"                   )
		,HILLIARD_LYONS				( "HilliardLyons"             )		
		,LEGENT						( "Legent"                    )
		,LOYAL3						( "LOYAL3"                    )
		,MESIROW					( "Mesirow"                   )
		,MISSION_TRUST				( "MissionTrustBank"          )
		,NONE						( "NONE"                      )
		,OPTIONS_XPRESS				( "OptionsXpress"             )
		,PENSON_TEXAS				( "PensonTX"                  )
		,PERSHING					( "Pershing"                  )
		,PLAINS_CAPITAL	    		( "PlainsCapital"             )
		,PRIMEVEST					( "Primevest"                 )
		,PULASKI					( "PulaskiBank"               )
		,RBC						( "RBC"                       )
		,RIDGE						( "PensonNY"                  )
		,SI_TRUST					( "SITrustBank"               )			
		,SMITH_MOORE				( "SmithMoore"                )
		,STERNE						( "Sterne"                    )
		,TERRA_NOVA					( "TerraNova"                 )
		,UCB						( "UnitedCommercialBank"      )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public enum Field
	{
		 ACCOUNT_NUMBER_LENGTH
		,CODE
		,DEFAULT_PAYOUT_TYPE_ID
		,FOLDER_NAME
		,ID
		,INTERNAL_CODE
		,IS_INTEREST_CREDITED_EARLY_ON_FW
		,IS_PAD_ACCOUNT_WHEN_SHORT
		,JOINT_TAX_ID_HANDLING_ID
		,NAME
		,OFFSET_ACCOUNT
		,PROCESSOR_IN_DIR
		,PROCESSOR_OUT_DIR
		,PROGRAM_ID
		,REPORTS_OUT_DIR
		,SI_IN_DIR
		,SI_OUT_DIR
		,STATEMENT_PROVIDER_OUT_DIR
		,USE_DEFAULT_PAYOUT_TYPE_AS_OVERRIDE
		,USE_FAKE_TAX_ID2
		;
	}

	public SourceInstitution()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ----------------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName							Database_FieldName          		Database_DataType                   Size    IsIdentity
		// ----------------------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ACCOUNT_NUMBER_LENGTH,					"AccountNumberLength",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.CODE,									"Code",								DatabaseDataType.CHAR,				4,		false);
		super.addField(Field.DEFAULT_PAYOUT_TYPE_ID,				"InterestPayout_Type_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.FOLDER_NAME,							"SourceInstitutionFolderName",		DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.ID,									"SourceInstitution_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.INTERNAL_CODE,							"InternalCode",						DatabaseDataType.CHAR,				30,		false);
		super.addField(Field.IS_INTEREST_CREDITED_EARLY_ON_FW,		"IsInterestCreditedEarlyOnFullWithdrawal",	DatabaseDataType.BIT,		1,		false);
		super.addField(Field.IS_PAD_ACCOUNT_WHEN_SHORT,				"IsPadAccountWhenShort",			DatabaseDataType.BIT,				1,		false);
		super.addField(Field.JOINT_TAX_ID_HANDLING_ID,				"JointTaxIDHandling_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.NAME,									"Name",								DatabaseDataType.CHAR,				50,		false);
		super.addField(Field.OFFSET_ACCOUNT,						"OffsetAccountNumber",				DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.PROCESSOR_IN_DIR,						"ProcessorInputDir",				DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.PROCESSOR_OUT_DIR,						"ProcessorOutputDir",				DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.PROGRAM_ID,							"Program_PK",						DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.REPORTS_OUT_DIR,						"ReportsOutputDir",					DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.SI_IN_DIR,								"SourceInstitutionInputDir",		DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.SI_OUT_DIR,							"SourceInstitutionOutputDir",		DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.STATEMENT_PROVIDER_OUT_DIR,			"StatementProviderOutputDir",		DatabaseDataType.CHAR,				200,	false);
		super.addField(Field.USE_DEFAULT_PAYOUT_TYPE_AS_OVERRIDE,	"UseDefaultPayoutTypeAsOverride",	DatabaseDataType.BIT,				1,		false);
		super.addField(Field.USE_FAKE_TAX_ID2,						"UseFakeTaxID2",					DatabaseDataType.BIT,				1,		false);
	}

	// Getters
	public int getAccountNumberLength()
	{
		return this.getField(Field.ACCOUNT_NUMBER_LENGTH).getIntegerValue();
	}

	public String getCode()
	{
		return this.getField(Field.CODE).getStringValue();
	}

	public long getDefaultPayoutTypeId()
	{
		return this.getField(Field.DEFAULT_PAYOUT_TYPE_ID).getLongValue();
	}
	
	public String getFolderName()
	{
		return this.getField(Field.FOLDER_NAME).getStringValue();
	}
	
	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
	public String getInternalCode()
	{
		return this.getField(Field.INTERNAL_CODE).getStringValue();
	}

	public boolean isPadAccountWhenShort()
	{
		return this.getField(Field.IS_PAD_ACCOUNT_WHEN_SHORT).getBooleanValue();
	}
	
	public boolean isUseDefaultPayoutTypeAsOverride()
	{
		return this.getField(Field.USE_DEFAULT_PAYOUT_TYPE_AS_OVERRIDE).getBooleanValue();
	}

	public boolean isUseFakeTaxId2()
	{
		return this.getField(Field.USE_FAKE_TAX_ID2).getBooleanValue();
	}
	
	public String getName()
	{
		return this.getField(Field.NAME).getStringValue();
	}
	
	public String getOffsetAccount()
	{
		return this.getField(Field.OFFSET_ACCOUNT).getStringValue();
	}
	
	public String getProcessorInputDir()
	{
		return this.getField(Field.PROCESSOR_IN_DIR).getStringValue();
	}
	
	public String getProcessorOutputDir()
	{
		return this.getField(Field.PROCESSOR_OUT_DIR).getStringValue();
	}
	
	public long getProgramId()
	{
		return this.getField(Field.PROGRAM_ID).getLongValue();
	}
	
	public String getReportsOutputDir()
	{
		return this.getField(Field.REPORTS_OUT_DIR).getStringValue();
	}
	
	public String getSIInputDir()
	{
		return this.getField(Field.SI_IN_DIR).getStringValue();
	}
	
	public String getSIOutputDir()
	{
		return this.getField(Field.SI_OUT_DIR).getStringValue();
	}
	
	public String getStatementProviderOutputDir()
	{
		return this.getField(Field.STATEMENT_PROVIDER_OUT_DIR).getStringValue();
	}

	// Setters
	public void setId(long id)
	{
		this.getField(Field.ID).setValue(id);
	}

	public void setCode(String code)
	{
		this.getField(Field.CODE).setValue(code);
	}
	
	public void setName(String name)
	{
		this.getField(Field.NAME).setValue(name);
	}

}
