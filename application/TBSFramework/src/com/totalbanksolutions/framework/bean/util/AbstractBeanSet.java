package com.totalbanksolutions.framework.bean.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractBeanSet <T extends Bean<?>> implements BeanSet<T>
{
    protected final Log log = LogFactory.getLog(AbstractBeanSet.class);

    private List<T> 			dataList 	= null;
	private Map<Long,T> 		mapById 	= new HashMap<Long,T>();
	private Map<String,T>		mapByName 	= new HashMap<String,T>();
	private Map<String,T>		mapByCode 	= new HashMap<String,T>();
	private String				idField		= null;
	private String				nameField	= null;
	private String				codeField	= null;
	
	public AbstractBeanSet(List<T> dataList)
	{
		super();
		this.dataList = dataList;
	}

	protected void setIdField(String idField)
	{
		this.idField = idField;
		createMapById();
	}
	
	protected void setNameField(String nameField)
	{
		this.nameField = nameField;
		createMapByName();
	}

	protected void setCodeField(String codeField)
	{
		this.codeField = codeField;
		createMapByCode();
	}

	private void createMapById()
	{
		for(T bean : this.dataList)
		{
			Long id = bean.getField(this.idField).getLongValue();
			this.mapById.put(id, bean);
		}
	}

	private void createMapByName()
	{
		for(T bean : this.dataList)
		{
			String name = bean.getField(this.nameField).getStringValue().toUpperCase();
			this.mapByName.put(name, bean);
		}
	}

	protected void createMapByCode()
	{
		for(T bean : this.dataList)
		{
			String code = bean.getField(this.codeField).getStringValue().toUpperCase();
			this.mapByCode.put(code, bean);
		}
	}

	@Override
	public boolean contains(long id) 
	{		
		return this.mapById.containsKey(id);
	}

	@Override
	public long getIdByName(String name) 
	{
		name = name.toUpperCase();
		if(this.mapByName.containsKey(name))
		{
			T bean = this.mapByName.get(name);
			return bean.getField(this.idField).getLongValue();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public long getIdByCode(String code) 
	{
		code = code.toUpperCase();
		if(this.mapByCode.containsKey(code))
		{
			T bean = this.mapByCode.get(code);
			return bean.getField(this.idField).getLongValue();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public String getName(long id)
	{
		if(this.mapById.containsKey(id))
		{
			T bean = this.mapById.get(id);
			return bean.getField(this.nameField).getStringValue();
		}
		else
		{
			return "";
		}
	}
	
	@Override
	public String getCode(long id)
	{
		if(this.mapById.containsKey(id))
		{
			T bean = this.mapById.get(id);
			return bean.getField(this.codeField).getStringValue();
		}
		else
		{
			return "";
		}
	}

	@Override
	public T getBean(long id) 
	{
		return this.mapById.get(id);
	}

	@Override
	public T getBeanByName(String name) 
	{
		return this.mapByName.get(name.toUpperCase());
	}

	@Override
	public T getBeanByCode(String code) 
	{
		return this.mapByCode.get(code.toUpperCase());
	}
	
	@Override
	public List<T> getList() 
	{		
		return this.dataList;
	}

	@Override
	public List<T> getList(Long... idList)
	{		
		List<T> list = new ArrayList<T>();
		for(Long id : idList)
		{
			list.add(getBean(id));
		}
		return list;
	}
	
}
