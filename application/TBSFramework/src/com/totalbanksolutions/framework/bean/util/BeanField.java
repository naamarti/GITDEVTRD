package com.totalbanksolutions.framework.bean.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.JavaDataType;

public class BeanField 
{
    protected final Log log = LogFactory.getLog(BeanField.class);

    protected String			containerClassName;
    protected String			name;
	protected JavaDataType		javaDataType;
	protected int				size;
	
	protected String			stringValue = "";
	protected Double			doubleValue = new Double(0);
	protected Long				longValue = new Long(0);
	protected Integer			intValue = new Integer(0);
	protected Boolean			booleanValue = false;
	protected Date				dateValue;

	public BeanField(BeanField field)
	{
		this(field.getContainerClassName(), field.getName(), field.getJavaDataType(), field.getSize());
	}
		
	public BeanField(String containerClassName, String name, JavaDataType javaDataType, int size)
	{
		this.containerClassName = containerClassName;
		this.name = name;
		this.javaDataType = javaDataType;
		this.size = size;
	}

	public String getBindId()
	{
		return (this.containerClassName + "_" + this.name).toUpperCase();
	}
	
	public void setValue(Object value)
	{
		//this.value = value;
		try
		{
			if(javaDataType == JavaDataType.BOOLEAN)
			{
				//log.debug("***** " + this.name + "; " + value );
				if(value.toString().equalsIgnoreCase("on"))
				{
					this.booleanValue = true;
				}
				else
				{
					this.booleanValue = (Boolean)value;					
				}
			}
			else if( (javaDataType == JavaDataType.DATE) || (javaDataType == JavaDataType.DATETIME) )
			{
				this.dateValue = defaultDate((Date)value);
			}
			else if(javaDataType == JavaDataType.DOUBLE)
			{
				this.doubleValue = (Double)value;
			}
			else if(javaDataType == JavaDataType.INT)
			{
				this.intValue = (Integer)value;
			}
			else if(javaDataType == JavaDataType.LONG)
			{
				if(value instanceof String)
				{
					this.longValue = Long.parseLong((String)value);
				}
				else
				{
					this.longValue = (Long)value;
				}
			}
			else if(javaDataType == JavaDataType.STRING)
			{
				String s = StringUtils.defaultString((String)value);
				if(this.size > 0)
				{
					s = StringUtils.substring(s, 0, size);
				}
				this.stringValue = s;
			}
			else
			{
				throw new RuntimeException("Invalid API Usage. Invalid JavaDataType specified for the field.");
			}
		}
		catch(Exception e)
		{
			String errorMessage = "Field='" + this.name + "', DataType='" + this.javaDataType + "'. " + ExceptionUtils.getMessage(e);
			throw new RuntimeException(errorMessage , e);
		}		
	}

	public Object getValue()
	{
		if(javaDataType == JavaDataType.BOOLEAN)
		{
			return this.booleanValue;
		}
		else if( (javaDataType == JavaDataType.DATE) || (javaDataType == JavaDataType.DATETIME) )
		{
			return defaultDate(this.dateValue);
		}
		else if(javaDataType == JavaDataType.DOUBLE)
		{
			return this.doubleValue;
		}
		else if(javaDataType == JavaDataType.INT)
		{
			return this.intValue;
		}
		else if(javaDataType == JavaDataType.LONG)
		{
			return this.longValue;
		}
		else if(javaDataType == JavaDataType.STRING)
		{
			return StringUtils.defaultString(this.stringValue);
		}
		else
		{
			throw new RuntimeException("Invalid API Usage. Invalid JavaDataType specified for the field.");
		}
	}

	public String getValueAsString()
	{
		if(javaDataType == JavaDataType.BOOLEAN)
		{
			return this.booleanValue.toString();
		}
		else if(javaDataType == JavaDataType.DATE)
		{
			return DateFormatUtils.format(defaultDate(this.dateValue), "yyyyMMdd");
		}
		else if(javaDataType == JavaDataType.DATETIME)
		{
			return DateFormatUtils.format(defaultDate(this.dateValue), "MM/dd/yyyy HH:mm:ss");
		}
		else if(javaDataType == JavaDataType.DOUBLE)
		{
			return this.doubleValue.toString();
		}
		else if(javaDataType == JavaDataType.INT)
		{
			return this.intValue.toString();
		}
		else if(javaDataType == JavaDataType.LONG)
		{
			return this.longValue.toString();
		}
		else if(javaDataType == JavaDataType.STRING)
		{
			return StringUtils.defaultString(this.stringValue);
		}
		else
		{
			throw new RuntimeException("Invalid API Usage. Invalid JavaDataType specified for the field.");
		}
	}

	private Date defaultDate(Date d)
	{
		Date newDate = d;
		try
		{
			if(d == null)
			{
				String[] formats = new String[] {"dd-MMM-yyyy", "d-MMM-yyyy", "MM/dd/yyyy"};
				String date = "01-JAN-1900";
				newDate = DateUtils.parseDate(date, formats);
			}
		}
		catch(Exception e) {}
		return newDate;
	}

	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	public String getContainerClassName() {
		return containerClassName;
	}
	public void setContainerClassName(String containerClassName) {
		this.containerClassName = containerClassName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JavaDataType getJavaDataType() {
		return javaDataType;
	}
	public void setJavaDataType(JavaDataType javaDataType) {
		this.javaDataType = javaDataType;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getStringValue() {
		return stringValue;
	}
	public Double getDoubleValue() {
		return doubleValue;
	}
	public Long getLongValue() {
		return longValue;
	}
	public Integer getIntegerValue() {
		return intValue;
	}
	public Boolean getBooleanValue() {
		return booleanValue;
	}
	public Date getDateValue() {
		return defaultDate(dateValue);
	}

}
