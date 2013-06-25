package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessStatus;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class DailyProcessStatusDataSet extends AbstractBeanSet<DailyProcessStatus>
{

	public DailyProcessStatusDataSet(List<DailyProcessStatus> dataList)
	{
		super(dataList);
		super.setIdField(DailyProcessStatus.Field.PROCESS_STATUS_ID.name());
		super.setNameField(DailyProcessStatus.Field.PROCESS_STATUS.name());
	}
	
	
}
