/***********************************************************************************
* EnvelopesDataSet.Java
* 7-Nov-2011 NAM #1051 - orig file
* 
***********************************************************************************/
package com.totalbanksolutions.framework.bean.database.data;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;



public class EnvelopesDataSet extends AbstractBeanSet<Envelopes>
{
	public EnvelopesDataSet(List<Envelopes> dataList)
	{
		super(dataList);
		super.setIdField(Envelopes.Field.ENVELOPE_ID.name());
		super.setNameField(Envelopes.Field.NAME.name());
	}
	
}
