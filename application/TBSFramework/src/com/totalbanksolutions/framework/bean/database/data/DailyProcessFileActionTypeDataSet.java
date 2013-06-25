package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessFileActionType;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class DailyProcessFileActionTypeDataSet extends AbstractBeanSet<DailyProcessFileActionType> 
{

	public DailyProcessFileActionTypeDataSet(List<DailyProcessFileActionType> dataList)
	{
		super(dataList);
		super.setIdField(DailyProcessFileActionType.Field.FILE_ACTION_TYPE_ID.name());
		super.setNameField(DailyProcessFileActionType.Field.FILE_ACTION_TYPE.name());
	}
	
}
