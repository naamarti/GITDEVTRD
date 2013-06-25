package com.totalbanksolutions.framework.bean.util;

import java.sql.ResultSet;

public interface BeanResultSetMapper 
{

	public void bindValuesFromResultset(ResultSet rs);
	public BeanResultSetMapper newInstance();
	public String[] getValues();
	public String[] getBindList();
	public void setBindList(String[] bindList);
	public BeanField getField(String fieldName);
	public boolean containsField(String fieldName);
}
