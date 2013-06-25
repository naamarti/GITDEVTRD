package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.ContactInstitutionType;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class ContactInstitutionTypeDataSet extends AbstractBeanSet<ContactInstitutionType>
{

	public ContactInstitutionTypeDataSet(List<ContactInstitutionType> dataList)
	{
		super(dataList);
		super.setIdField(ContactInstitutionType.Field.ID.name());
		super.setNameField(ContactInstitutionType.Field.CONTACTINSTITUTION_TYPE.name());
	}		
	
}
