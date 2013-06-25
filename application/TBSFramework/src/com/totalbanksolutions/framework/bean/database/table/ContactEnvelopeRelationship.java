package com.totalbanksolutions.framework.bean.database.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.bean.util.BeanResultSetMapper;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author vcatrini
 */
public class ContactEnvelopeRelationship extends AbstractDatabaseBean<ContactEnvelopeRelationship.Field>
{		
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Contacts";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;

	private	AppUser			appUser			= null;
	private List<Long>		envelopeIdByDepositInstitutionList = null;
	private List<Long>		envelopeIdBySourceInstitutionList = null;
	
	public enum Field
	{
		  RELATIONSHIP_ID
		 ,CONTACT_ID
		 ,ENVELOPE_ID
		;
	}

	public ContactEnvelopeRelationship()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName			Database_DataType						Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.RELATIONSHIP_ID,			"Relationship_PK",			DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.CONTACT_ID,				"Contact_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		true);
		super.addField(Field.ENVELOPE_ID,				"Envelope_FK",				DatabaseDataType.DECIMAL_LONGINT,		0,		true);
	}

	public ContactEnvelopeRelationship(String[] bindList)
	{
		this();
		this.setBindList(bindList);
	}
	
	@Override
	public BeanResultSetMapper getBindObject(String s)
	{
		if(s.equalsIgnoreCase("AppUser")) return this.getAppUser();
		return null;
	}
	
	public AppUser getAppUser() {
		if(this.appUser == null) this.appUser = new AppUser();		
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}


	public String getEnvelopeIdByDepositInstitutionDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getEnvelopeIdByDepositInstitutionList().toArray());
		list = list.replace(", ", "][");
		return list;
	}

	public String getEnvelopeIdBySourceInstitutionDelimitedString()
	{
		String list = "";
		list = Arrays.toString(getEnvelopeIdBySourceInstitutionList().toArray());
		list = list.replace(", ", "][");
		return list;
	}

	public List<Long> getEnvelopeIdByDepositInstitutionList() 
	{
		if(this.envelopeIdByDepositInstitutionList == null) this.envelopeIdByDepositInstitutionList = new ArrayList<Long>();
		return this.envelopeIdByDepositInstitutionList;
	}

	public List<Long> getEnvelopeIdBySourceInstitutionList() 
	{
		if(this.envelopeIdBySourceInstitutionList == null) this.envelopeIdBySourceInstitutionList = new ArrayList<Long>();
		return this.envelopeIdBySourceInstitutionList;
	}

	public void setEnvelopeIdByDepositInstitutionList(List<Long> envelopeIdByDepositInstitutionList) 
	{
		this.envelopeIdByDepositInstitutionList = envelopeIdByDepositInstitutionList;
	}

	public void setEnvelopeIdBySourceInstitutionList(List<Long> envelopeIdBySourceInstitutionList) 
	{
		this.envelopeIdBySourceInstitutionList = envelopeIdBySourceInstitutionList;
	}

}
