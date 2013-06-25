package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DepositInstitution;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class DepositInstitutionDataSet extends AbstractBeanSet<DepositInstitution>
{

	public DepositInstitutionDataSet(List<DepositInstitution> dataList)
	{
		super(dataList);
		super.setIdField(DepositInstitution.Field.ID.name());
		super.setNameField(DepositInstitution.Field.NAME.name());
		super.setCodeField(DepositInstitution.Field.CODE.name());
	}
		
}
