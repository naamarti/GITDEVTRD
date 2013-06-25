package com.totalbanksolutions.framework.model;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.JavaDataType;

public interface ModelColumnSpecification 
{
	String getColumnName();
	JavaDataType getDataType();
	String getDescription();
	String getLabel();
	int getSize();
	
//	String getDatabaseColumnName();
//	DatabaseDataType getDatabaseDataType();
//	boolean getDatabaseIsIdentityAutoSequence();
}
