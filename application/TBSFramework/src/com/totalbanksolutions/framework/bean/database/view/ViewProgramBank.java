package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Modified:  23 Apr 2012 VC  #966: All Programs / Bank Centric checklist - add First Niagara quarterly report
 *
 * =================================================================================================
 */
public class ViewProgramBank extends AbstractDatabaseBean<ViewProgramBank.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  DEPOSIT_INSTITUTION_ID
		, PROGRAM_ID
		;
	}

	public ViewProgramBank()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.DEPOSIT_INSTITUTION_ID,			"DepositInstitution_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
		super.addField(Field.PROGRAM_ID,						"Program_FK",					DatabaseDataType.DECIMAL_LONGINT,	0,		false);	
	}

	// Getters
	public long getDepositInstitutionId()
	{
		return this.getField(Field.DEPOSIT_INSTITUTION_ID).getLongValue();
	}
	
	public long getProgramId()
	{
		return this.getField(Field.PROGRAM_ID).getLongValue();
	}
	
}
