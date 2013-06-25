package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class DepositInstitution extends AbstractDatabaseBean<DepositInstitution.Field>
{
	//private boolean	isSelected = false;

	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DepositInstitutions";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	public enum Field
	{
		 CODE
		,FDIC_CERT_NUMBER
		,ID
		,NAME
		;
	}

	public DepositInstitution()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// ------------------------------------------------------------------------------------------------------------------------
		// 				ConstantName			Database_FieldName          Database_DataType                   Size    IsIdentity
		// ------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.CODE,				"Code",						DatabaseDataType.CHAR,				4,		false);
		super.addField(Field.FDIC_CERT_NUMBER,	"FDICCertificateNumber",	DatabaseDataType.CHAR,				20,		false);
		super.addField(Field.ID,				"DepInst_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		true);
		super.addField(Field.NAME,				"Name",						DatabaseDataType.CHAR,				24,		false);
	}

	// Getters
	public String getCode()
	{
		return this.getField(Field.CODE).getStringValue();
	}
	
	public String getFDICCertificateNumber()
	{
		return this.getField(Field.FDIC_CERT_NUMBER).getStringValue();
	}
	
	public long getId()
	{
		return this.getField(Field.ID).getLongValue();
	}

	public String getName()
	{
		return this.getField(Field.NAME).getStringValue();
	}

}
