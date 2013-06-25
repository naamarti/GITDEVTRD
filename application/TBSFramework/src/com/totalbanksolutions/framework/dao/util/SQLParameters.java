package com.totalbanksolutions.framework.dao.util;

import java.util.ArrayList;
import java.util.List;

public class SQLParameters 
{
	private List<Object> paramList = new ArrayList<Object>();
	
	public void addValue(Object param)
	{
		this.paramList.add(param);
	}
	
	public List<Object> getParamList()
	{
		return paramList;
	}

}
