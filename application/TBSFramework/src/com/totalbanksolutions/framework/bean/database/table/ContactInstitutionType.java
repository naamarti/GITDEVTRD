package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class ContactInstitutionType extends AbstractDatabaseBean<ContactInstitutionType.Field>
{
	//private boolean	isSelected = false;

	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "ContactInstitutionType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		 SOURCE_INSTITUTION			( "Source Institution"      )
		,DEPOSIT_INSTITUTION		( "Deposit Institution"     )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public enum Field
	{
		 ID
		,CONTACTINSTITUTION_TYPE
		;
	}

	public ContactInstitutionType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// -------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName          	Database_DataType                   Size    IsIdentity
		// -------------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.ID,							"ContactType_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.CONTACTINSTITUTION_TYPE,		"ContactInstitutionType",	DatabaseDataType.CHAR,				50,		false);
	}


	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}
	
}

