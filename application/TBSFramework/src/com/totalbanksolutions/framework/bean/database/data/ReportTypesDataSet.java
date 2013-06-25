/***********************************************************************************
* ReportTypesDataSet.Java
* 7-Nov-2011 NAM #1051 - orig file
* 
***********************************************************************************/
package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.ReportTypes;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class ReportTypesDataSet extends AbstractBeanSet<ReportTypes>
{
	public ReportTypesDataSet(List<ReportTypes> dataList)
	{
		super(dataList);
		super.setIdField(ReportTypes.Field.REPORT_TYPE_PK.name());
		super.setNameField(ReportTypes.Field.TYPE.name());
	}
	
}
