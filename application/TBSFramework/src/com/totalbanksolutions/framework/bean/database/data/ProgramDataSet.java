package com.totalbanksolutions.framework.bean.database.data;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.Program;
import com.totalbanksolutions.framework.bean.util.AbstractBeanSet;

public class ProgramDataSet extends AbstractBeanSet<Program>
{

	public ProgramDataSet(List<Program> dataList)
	{
		super(dataList);
		super.setIdField(Program.Field.ID.name());
		super.setNameField(Program.Field.NAME.name());
		super.setCodeField(Program.Field.INTERNAL_CODE.name());
	}
		
}
