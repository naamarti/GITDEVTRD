package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.RG_Report;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class RG_ReportDataSet extends AbstractBeanSet<RG_Report>
{
	public RG_ReportDataSet(List<RG_Report> dataList)
	{
		super(dataList);
		super.setIdField(RG_Report.Field.REPORT_ID.name());
		super.setNameField(RG_Report.Field.DESCRIPTION.name());
	}		
}