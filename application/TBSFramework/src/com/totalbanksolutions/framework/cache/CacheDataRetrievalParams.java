package com.totalbanksolutions.framework.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheDataRetrievalParams 
{

	private Map<String,String> params = new HashMap<String,String>();
	
	public void addValue(String name, String value)
	{
		params.put(name, value);
	}

	protected Map<String, String> getParams() {
		return params;
	}
	
}
