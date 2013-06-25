package com.totalbanksolutions.framework.bean.util;

import java.util.List;

public interface BeanSet <T>
{	
	public long getIdByName(String name);
	public long getIdByCode(String code);

	public String getName(long id);
	public String getCode(long id);
	
	public T getBean(long id);
    public T getBeanByName(String name);
    public T getBeanByCode(String code);
    
	public List<T> getList();
	public List<T> getList(Long... idList);
    
	public boolean contains(long id);
}
