package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessStep;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class DailyProcessStepDataSet extends AbstractBeanSet<DailyProcessStep>
{

	public DailyProcessStepDataSet(List<DailyProcessStep> dataList)
	{
		super(dataList);
		super.setIdField(DailyProcessStep.Field.STEP_ID.name());
	}
	
	
}
