package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DepositInstitution;
import com.totalbanksolutions.framework.bean.database.table.SettlementInstitution;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class SettlementInstitutionDataSet extends AbstractBeanSet<SettlementInstitution>
{

	public SettlementInstitutionDataSet(List<SettlementInstitution> dataList)
	{
		super(dataList);
		super.setIdField(SettlementInstitution.Field.ID.name());
		super.setNameField(SettlementInstitution.Field.NAME.name());
	}
		
}
