package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.SourceInstitution;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class SourceInstitutionDataSet extends AbstractBeanSet<SourceInstitution>
{

	public SourceInstitutionDataSet(List<SourceInstitution> dataList)
	{
		super(dataList);
		super.setIdField(SourceInstitution.Field.ID.name());
		super.setNameField(SourceInstitution.Field.NAME.name());
		super.setCodeField(SourceInstitution.Field.INTERNAL_CODE.name());
	}
		
}
