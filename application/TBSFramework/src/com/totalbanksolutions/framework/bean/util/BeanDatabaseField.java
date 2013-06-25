package com.totalbanksolutions.framework.bean.util;

import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.JavaDataType;

public class BeanDatabaseField extends BeanField
{
    protected final Log log = LogFactory.getLog(BeanDatabaseField.class);

	private String					databaseFieldName;
	private String					databaseTableName;
	private DatabaseDataType		databaseDataType;
	private boolean					isIdentityAutoSequence;
	
	public BeanDatabaseField(BeanDatabaseField field)
	{
		this(field.getContainerClassName(), field.getName(), field.getDatabaseFieldName(), field.getDatabaseTableName(), field.getDatabaseDataType(), field.getJavaDataType(), field.getSize(), field.isIdentityAutoSequence());
	}
		
	public BeanDatabaseField(String containerClassName, String name, String databaseFieldName, String databaseTableName, DatabaseDataType databaseDataType, JavaDataType javaDataType, int size, boolean isIdentityAutoSequence)
	{
		super(containerClassName, name, javaDataType, size);
		this.databaseFieldName = databaseFieldName;
		this.databaseTableName = databaseTableName;
		this.databaseDataType = databaseDataType;
		if(javaDataType == JavaDataType.AUTO_FROM_DATABASE_TYPE)
		{
			setAutoJavaDataType();
		}
		this.isIdentityAutoSequence = isIdentityAutoSequence;
	}
	
	private void setAutoJavaDataType()
	{
		if(this.databaseDataType == DatabaseDataType.BIT)
		{
			this.javaDataType = JavaDataType.BOOLEAN;
		}
		else if(this.databaseDataType == DatabaseDataType.CHAR)
		{
			this.javaDataType = JavaDataType.STRING;
		}
		else if(this.databaseDataType == DatabaseDataType.DATETIME)
		{
			this.javaDataType = JavaDataType.DATE;
		}
		else if(this.databaseDataType == DatabaseDataType.DATETIME2)
		{
			this.javaDataType = JavaDataType.DATETIME;
		}
		else if(this.databaseDataType == DatabaseDataType.DECIMAL_AMOUNT)
		{
			this.javaDataType = JavaDataType.DOUBLE;
		}
		else if(this.databaseDataType == DatabaseDataType.DECIMAL_LONGINT)
		{
			this.javaDataType = JavaDataType.LONG;
		}
		else if(this.databaseDataType == DatabaseDataType.INT)
		{
			this.javaDataType = JavaDataType.INT;
		}
		else
		{
			throw new RuntimeException("Invalid API Usage. Invalid DatabaseDataType specified for the field.");
		}
	}

	public void setValueFromResultset(ResultSet rs)
	{
		try
		{
			if(javaDataType == JavaDataType.BOOLEAN)
			{
				this.booleanValue = rs.getBoolean(this.databaseFieldName);
			}
			else if((javaDataType == JavaDataType.DATE) || (javaDataType == JavaDataType.DATETIME) )
			{
				this.dateValue = rs.getTimestamp(this.databaseFieldName);
			}
			else if(javaDataType == JavaDataType.DOUBLE)
			{
				this.doubleValue = rs.getDouble(this.databaseFieldName);
			}
			else if(javaDataType == JavaDataType.INT)
			{
				this.intValue = rs.getInt(this.databaseFieldName);
			}
			else if(javaDataType == JavaDataType.LONG)
			{
				this.longValue = rs.getLong(this.databaseFieldName);
			}
			else if(javaDataType == JavaDataType.STRING)
			{
				this.stringValue = StringUtils.defaultString(rs.getString(this.databaseFieldName)).trim();
			}
			else
			{
				throw new RuntimeException("Invalid API Usage. Invalid JavaDataType specified for the field.");
			}		
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}						
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	public String getDatabaseTableName() {
		return databaseTableName;
	}
	public void setDatabaseTableName(String databaseTableName) {
		this.databaseTableName = databaseTableName;
	}
	public DatabaseDataType getDatabaseDataType() {
		return databaseDataType;
	}
	public void setDatabaseDataType(DatabaseDataType databaseDataType) {
		this.databaseDataType = databaseDataType;
	}
	public boolean isIdentityAutoSequence() {
		return isIdentityAutoSequence;
	}
	public void setIdentityAutoSequence(boolean isIdentityAutoSequence) {
		this.isIdentityAutoSequence = isIdentityAutoSequence;
	}
	public String getDatabaseFieldName() {
		return databaseFieldName;
	}
	public void setDatabaseFieldName(String databaseFieldName) {
		this.databaseFieldName = databaseFieldName;
	}
	
}
