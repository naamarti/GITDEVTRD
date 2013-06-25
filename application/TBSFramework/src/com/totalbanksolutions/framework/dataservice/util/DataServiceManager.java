package com.totalbanksolutions.framework.dataservice.util;

import org.apache.log4j.Logger;

import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.util.DataSourceManager;
import com.totalbanksolutions.framework.dataservice.DataService;

/**
 * =================================================================================================
 * Modified:  10 Oct 2012 VC #1916: Java Caching issue - new Eagle bank not showing up in DI drop-downs
 * =================================================================================================
 */

public class DataServiceManager 
{
	private final Logger log = Logger.getLogger(DataServiceManager.class);
	private int cacheDefaultExpiresInSeconds = 0;
	
	public DataService getDataService()
	{
		DataSourceManager dataSourceManager = new DataSourceManager();
		CacheManager cacheManager = new CacheManager();
		cacheManager.setDefaultExpiresInSeconds( this.cacheDefaultExpiresInSeconds );
		DataService dataService = new DataService();
		dataService.setCacheManager(cacheManager);
		dataService.setDataSource( dataSourceManager.getDataSource() );
		return dataService;
	}

	public int getCacheDefaultExpiresInSeconds() {
		return cacheDefaultExpiresInSeconds;
	}

	public void setCacheDefaultExpiresInSeconds(int cacheDefaultExpiresInSeconds) {
		this.cacheDefaultExpiresInSeconds = cacheDefaultExpiresInSeconds;
	}

	
}
