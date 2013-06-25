package com.totalbanksolutions.framework.bean.web;

import com.totalbanksolutions.framework.bean.util.AbstractBean;
import com.totalbanksolutions.framework.enums.JavaDataType;

public class ForecastFilter extends AbstractBean<ForecastFilter.Field>
{
	public enum Field
	{
		 PROGRAM_ID
		,IS_SHOW_ZERO_BALANCE
		;
	}
		
	public ForecastFilter()
	{
		super();
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Java_DataType				Size
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.PROGRAM_ID,				JavaDataType.LONG,			0);
		super.addField(Field.IS_SHOW_ZERO_BALANCE,		JavaDataType.BOOLEAN,		0);
	}

	public long getProgramId()
	{
		return this.getField(Field.PROGRAM_ID).getLongValue();
	}
	public void setProgramId(long programId)
	{
		this.getField(Field.PROGRAM_ID).setValue(programId);
	}
	public boolean isShowZeroBalance()
	{
		return this.getField(Field.IS_SHOW_ZERO_BALANCE).getBooleanValue();		
	}

	public String getProgramIdBindId()
	{
		return this.getField(Field.PROGRAM_ID).getBindId();
	}
	public String getShowZeroBalanceBindId()
	{
		return this.getField(Field.IS_SHOW_ZERO_BALANCE).getBindId();
	}

}
