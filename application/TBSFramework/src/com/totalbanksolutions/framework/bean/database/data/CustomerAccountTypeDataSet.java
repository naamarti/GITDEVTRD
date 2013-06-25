package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.CustomerAccountType;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class CustomerAccountTypeDataSet extends AbstractBeanSet<CustomerAccountType>
{

	public CustomerAccountTypeDataSet(List<CustomerAccountType> dataList)
	{
		super(dataList);
		super.setIdField(CustomerAccountType.Field.ACCOUNT_TYPE_ID.name());
		super.setNameField(CustomerAccountType.Field.ACCOUNT_TYPE.name());
	}
	
	
}
