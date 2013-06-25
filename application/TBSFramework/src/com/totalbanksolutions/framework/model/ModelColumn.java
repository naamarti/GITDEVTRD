package com.totalbanksolutions.framework.model;

import java.sql.ResultSet;
import java.util.Date;

import com.totalbanksolutions.framework.enums.JavaDataType;

public interface ModelColumn 
{
	String getColumnName();
	JavaDataType getDataType();
	String getDescription();
	String getLabel();
	int getSize();

	Object getValue();
	Boolean getValueAsBoolean();
	Date getValueAsDate();
	Double getValueAsDouble();
	Integer getValueAsInteger();
	Long getValueAsLong();
	String getValueAsString();

	void setValue( Object obj );
	void setValueFromResultset( ResultSet rs, String databaseColumnName );
}
