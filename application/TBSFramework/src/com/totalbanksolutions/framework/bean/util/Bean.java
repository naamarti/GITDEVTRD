package com.totalbanksolutions.framework.bean.util;

public interface Bean <E extends Enum<E>>
{	
	public BeanField getField(String fieldName);
	public BeanField getField(Enum<E> fieldName);
}
