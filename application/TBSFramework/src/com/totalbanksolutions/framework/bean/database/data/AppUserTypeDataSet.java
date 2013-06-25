package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.AppUserType;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class AppUserTypeDataSet extends AbstractBeanSet<AppUserType> 
{

	public AppUserTypeDataSet(List<AppUserType> dataList)
	{
		super(dataList);
		super.setIdField(AppUserType.Field.USER_TYPE_ID.name());
		super.setNameField(AppUserType.Field.USER_TYPE.name());
	}
	
}
