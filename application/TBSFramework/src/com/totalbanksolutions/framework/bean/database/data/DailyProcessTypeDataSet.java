package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessType;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class DailyProcessTypeDataSet extends AbstractBeanSet<DailyProcessType>
{

	public DailyProcessTypeDataSet(List<DailyProcessType> dataList)
	{
		super(dataList);
		super.setIdField(DailyProcessType.Field.PROCESS_TYPE_ID.name());
		super.setNameField(DailyProcessType.Field.PROCESS_TYPE.name());
	}
	
}
