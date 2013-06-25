package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.AppRole;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class AppRoleDataSet extends AbstractBeanSet<AppRole>  
{

	public AppRoleDataSet(List<AppRole> dataList)
	{
		super(dataList);
		super.setIdField(AppRole.Field.ID.name());
		super.setNameField(AppRole.Field.NAME.name());
	}
	
	
}
