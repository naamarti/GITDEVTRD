package com.totalbanksolutions.framework.cache;

import java.util.Date;
import java.util.concurrent.Semaphore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheElement 
{
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	private String 		key;
	private Object 		value;
	private Date		createDateTime;
	private	Date		lastAccessDateTime;
	private	int			expiresInSeconds = 0;
    private Semaphore 	writeLockSemaphore = new Semaphore(1);
    private boolean		isInitialized = false;
    private boolean		isExpired = false;
    
	public CacheElement(String key, Object value)
	{
		this(key, value, 0);
	}

	public CacheElement(String key, Object value, int expiresInSeconds)
	{
		this.key = key;
		this.value = value;
		this.expiresInSeconds = expiresInSeconds;
		this.createDateTime = new Date();
	}
	
	public boolean calculateIsExpired()
	{
		boolean expired = false;
		if(this.isExpired)
		{
			expired = true;
		}
		else if(expiresInSeconds > 0)
		{
			long secondsSinceCreate = (new Date().getTime() - createDateTime.getTime()) / 1000;
			if(secondsSinceCreate > expiresInSeconds) 
			{
				//log.debug(key + " secondsSinceCreate = " + secondsSinceCreate + "; expiresInSeconds = " + expiresInSeconds + "; Created = " + createDateTime);
				expired = true;
			}
		}
		return expired;
	}

	public boolean isReady()
	{
		boolean isReady = false;
		if(isInitialized && !calculateIsExpired())
		{
			isReady = true;
		}
		return isReady;
	}
	
	// --------------------------------------------
	// Getters & Setters (auto generated)
	// --------------------------------------------
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getLastAccessDateTime() {
		return lastAccessDateTime;
	}

	public void setLastAccessDateTime(Date lastAccessDateTime) {
		this.lastAccessDateTime = lastAccessDateTime;
	}

	public int getExpireSeconds() {
		return expiresInSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expiresInSeconds = expireSeconds;
	}

	public Semaphore getWriteLockSemaphore() {
		return writeLockSemaphore;
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
	
}
