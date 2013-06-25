package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DepositInstitution;
import com.totalbanksolutions.framework.bean.database.table.SettlementAccount;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class SettlementAccountDataSet extends AbstractBeanSet<SettlementAccount>
{

	public SettlementAccountDataSet(List<SettlementAccount> dataList)
	{
		super(dataList);
		super.setIdField(SettlementAccount.Field.ID.name());
		super.setNameField(SettlementAccount.Field.DESCRIPTION.name());
	}
		
}
