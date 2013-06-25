package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessOperationType;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class DailyProcessOperationTypeDataSet extends AbstractBeanSet<DailyProcessOperationType>
{

	public DailyProcessOperationTypeDataSet(List<DailyProcessOperationType> dataList)
	{
		super(dataList);
		super.setIdField(DailyProcessOperationType.Field.OPERATION_TYPE_ID.name());
		super.setNameField(DailyProcessOperationType.Field.OPERATION_TYPE.name());
	}
	
}
