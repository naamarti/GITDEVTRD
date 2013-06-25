package com.totalbanksolutions.framework.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheManager 
{
	
	// ----------------------------------------------------
	// Public Methods
	// ----------------------------------------------------	

    public void setDefaultExpiresInSeconds(int expireInSeconds)
    {
    	this.defaultExpiresInSeconds = expireInSeconds;
    }

    public void setExpiresInSecondsMap(Map<String,Integer> expiresInSecondsMap)
    {
    	this.expiresInSecondsMap = expiresInSecondsMap;
    }

    public void put(CacheType cacheType, Object value)
	{
		internalPutCache(cacheType, value, internalGetDefaultExpiresInSeconds(cacheType.getCacheName()));
	}

	public void put(CacheType cacheType, Object value, int expiresInSeconds)
	{
		internalPutCache(cacheType, value, expiresInSeconds);
	}

	public Object get(CacheType cacheType)
	{
		return internalGetCache(cacheType);
	}

	public Object get(CacheType cacheType, CacheDataRetrievalMethod method)
    {
		return internalGetCache(cacheType, method, null);
    }	

	public Object get(CacheType cacheType, CacheDataRetrievalMethod method, CacheDataRetrievalParams params)
    {
		return internalGetCache(cacheType, method, params);
    }

	public void setExpired(CacheType cacheType)
	{
		internalSetExpired(cacheType, null);
	}
	
	public void setExpired(CacheType cacheType, CacheDataRetrievalParams params)
	{
		internalSetExpired(cacheType, params);
	}

	// ----------------------------------------------------
	// Private Implementation
	// ----------------------------------------------------	

	protected final Log log = LogFactory.getLog(getClass());

	private ConcurrentMap<String,CacheElement> elementMap = new ConcurrentHashMap<String,CacheElement>();

	private int defaultExpiresInSeconds = 0;

	private Map<String,Integer> expiresInSecondsMap = new HashMap<String,Integer>();
	
	private String internalGetCacheKey(CacheType cacheType)
	{
		String key = cacheType.getCacheName();
		return key;
	}
	
	private String internalGetCacheKey(CacheType cacheType, CacheDataRetrievalParams params)
	{
		String key = cacheType.getCacheName();
		if(params != null)
		{
	    	String paramKey = "";
			if(params != null)
	    	{
	    		for(Map.Entry<String,String> entry : params.getParams().entrySet())
	    		{
	    			paramKey += entry.getKey() + "=" + entry.getValue() + "|";
	    		}
	    	}
	    	key = key + ";" + paramKey;
		}
		return key;
	}

	private int internalGetDefaultExpiresInSeconds(String key)
	{
		if(expiresInSecondsMap.containsKey(key))
		{
			return expiresInSecondsMap.get(key);
		}
		else
		{
			return this.defaultExpiresInSeconds;
		}
	}
	
	private void internalPutCache(CacheType cacheType, Object value, int expiresInSeconds)
	{
		String key = internalGetCacheKey(cacheType);
		CacheElement element = new CacheElement(key, value, expiresInSeconds);
		elementMap.put(element.getKey(), element);
	}
	
	private Object internalGetCache(CacheType cacheType)
	{
		String key = internalGetCacheKey(cacheType);
		CacheElement element = elementMap.get(key);
		if(element == null)
		{
			return null;
		}
		else if(!element.isReady())
		{
			return null; 
		}
		return element.getValue();
	}

    private Object internalGetCache(CacheType cacheType, CacheDataRetrievalMethod method, CacheDataRetrievalParams params)
    {
		String key = internalGetCacheKey(cacheType, params);
		CacheElement element = elementMap.get(key);
		if(element == null)
		{
			element = new CacheElement(key, null, internalGetDefaultExpiresInSeconds(key));
			elementMap.putIfAbsent(element.getKey(), element);
			element = elementMap.get(key);
		}
		if(!element.isReady())
		{
			//if(!element.isInitialized()) log.debug("Cache Not Initialized");
			if(element.isExpired()) log.debug("Cache Expired [" + key + "]");
			internalRetrieveData(element, method);
    	}
    	return element.getValue();
    }	
	
    private void internalRetrieveData(CacheElement element, CacheDataRetrievalMethod method)
    {
    	try
    	{
    		element.getWriteLockSemaphore().acquire();			// Single thread access, additional threads will block here.
        	if(!element.isReady())								// Recheck Cache after lock is obtained, in case another thread already updated it
        	{
        		//log.debug("***** Retrieving Cache Data [" + element.getKey() + "] *****");
	    		Object data = method.retrieveData();
	    		element.setValue(data);
	    		element.setCreateDateTime(new Date());
	    		element.setInitialized(true);
	    		element.setExpired(false);
        	}
    	}
    	catch (InterruptedException e)
    	{
    		Thread.currentThread().interrupt();					// reassert interrupt, since we won't handle the exception here
    	}
    	finally
    	{
    		element.getWriteLockSemaphore().release();			// release lock
    	}
    }

    private void internalSetExpired(CacheType cacheType, CacheDataRetrievalParams params)
    {
		String key = internalGetCacheKey(cacheType, params);
		CacheElement element = elementMap.get(key);
		if(element != null)
		{
	    	try
	    	{
	    		element.getWriteLockSemaphore().acquire();		// Single thread access, additional threads will block here.
	    		element.setValue(null);
	    		element.setExpired(true);
	    	}
	    	catch (InterruptedException e)
	    	{
	    		Thread.currentThread().interrupt();				// reassert interrupt, since we won't handle the exception here
	    	}
	    	finally
	    	{
	    		element.getWriteLockSemaphore().release();		// release lock
	    	}
		}
    }

    
}
