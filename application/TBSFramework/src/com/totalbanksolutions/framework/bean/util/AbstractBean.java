package com.totalbanksolutions.framework.bean.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.JavaDataType;

public abstract class AbstractBean <E extends Enum<E>> implements Bean<E>
{
    protected final Log log = LogFactory.getLog(AbstractBean.class);

    private String 					containerClassName;
	private Map<String,BeanField>	fields = new LinkedHashMap<String,BeanField>();
		
	protected AbstractBean()
	{
		super();
		this.containerClassName = ClassUtils.getShortClassName(this.getClass().getName());
	}
	
	public void addField(Enum<E> enumType, JavaDataType javaDataType, int size)
	{
		BeanField beanField = new BeanField(this.containerClassName, enumType.name(), javaDataType, size);
		addField(beanField);
	}

	protected void addField(BeanField beanField)
	{
		if(fields.containsKey(beanField.getName()))
		{
			throw new RuntimeException("Invalid API Usage. A field has already been defined with name = '" + beanField.getName() + "'.");
		}
		fields.put(beanField.getName(), beanField);
	}

	@Override
	public BeanField getField(Enum<E> fieldName)
	{
		return getField(fieldName.name());
	}

	@Override
	public BeanField getField(String fieldName)
	{
		BeanField field = fields.get(fieldName);
		if(field == null)
		{
			throw new RuntimeException("Invalid API Usage. No field defined with name = '" + fieldName + "'.");
		}
		return field;		
	}

	public String toString()
	{
		String tmp = "";
		for(Map.Entry<String, BeanField> fieldEntry : fields.entrySet())
		{
			BeanField field = fieldEntry.getValue();
			tmp += field.getName() + "=" + field.getValue() + " ; ";
		}
		return tmp;
	}
	
	public void bindValuesFromParameterMap(Map<?,?> paramMap)
	{
		String prefix = this.containerClassName.toUpperCase() + "_";
		for(Map.Entry<?,?> entry : paramMap.entrySet())
		{
			String paramName = (String)entry.getKey();
			if(paramName.startsWith(prefix))
			{
				String fieldName = paramName.substring(prefix.length());
				if(fields.containsKey(fieldName))
				{
					String paramValue = ((String[])entry.getValue())[0];
					
					//log.debug("fieldName=" + fieldName + "; value=" + paramValue);
					
					BeanField field = fields.get(fieldName);
					field.setValue(paramValue);
				}
			}
		}
	}

	public List<BeanField> getFieldList()
	{
		return new ArrayList<BeanField>(fields.values());
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	public Map<String, BeanField> getFields() {
		return fields;
	}

	public void setFields(Map<String, BeanField> fields) {
		this.fields = fields;
	}
	
}