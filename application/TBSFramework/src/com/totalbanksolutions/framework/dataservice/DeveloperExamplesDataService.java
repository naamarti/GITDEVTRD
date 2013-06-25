package com.totalbanksolutions.framework.dataservice;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.cache.CacheDataRetrievalMethod;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.DeveloperExamplesDAO;
import com.totalbanksolutions.framework.dao.jdbc.DeveloperExamplesJDBC;
import com.totalbanksolutions.framework.dataservice.util.DataCacheType;
import com.totalbanksolutions.framework.model.ModelTable;
import com.totalbanksolutions.framework.model.database.table.DeveloperExamplesConfigurationTable;
import com.totalbanksolutions.framework.model.database.table.DeveloperExamplesLocationTable;

/**
 * =================================================================================================
 * Created:   21 Apr 2010 VC
 * Modified:  
 *
 * =================================================================================================
 */
public class DeveloperExamplesDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private DeveloperExamplesDAO developerExamplesDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;
    
    public DeveloperExamplesDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.developerExamplesDAO = new DeveloperExamplesJDBC(dataSource);
    	this.cacheManager = cacheManager;    	
    	this.ds = ds;
    }

	public void close()
	{
		this.developerExamplesDAO.close();
		this.developerExamplesDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

	//============================================================================
	//
    // DeveloperExamples_Configuration
    //
	//============================================================================
    public DeveloperExamplesConfigurationTable getDeveloperExamplesConfiguration()
    {
    	return developerExamplesDAO.getDeveloperExamplesConfiguration();
    }

    public DeveloperExamplesConfigurationTable getDeveloperExamplesConfigurationSQLError()
    {
    	return developerExamplesDAO.getDeveloperExamplesConfigurationSQLError();
    }
    
    protected DeveloperExamplesConfigurationTable getDeveloperExamplesConfigurationCache()
    {
       	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return developerExamplesDAO.getDeveloperExamplesConfiguration();
    		}
    	};
    	return (DeveloperExamplesConfigurationTable)cacheManager.get(DataCacheType.DEVEOPER_EXAMPLES_CONFIGURATION, retrieveMethod);
    }
    
    public void updateDeveloperExamplesConfiguration(DeveloperExamplesConfigurationTable t)
    {
    	developerExamplesDAO.updateDeveloperExamplesConfiguration(t);
    }

	//============================================================================
	//
    // DeveloperExamples_Files
    //
	//============================================================================
    public ModelTable getDeveloperExamplesFiles()
    {
    	return developerExamplesDAO.getDeveloperExamplesFiles();
    }

	//============================================================================
	//
    // DeveloperExamples_Locations
    //
	//============================================================================
    public DeveloperExamplesLocationTable getDeveloperExamplesLocations()
    {
    	return developerExamplesDAO.getDeveloperExamplesLocations();
    }
    
	//============================================================================
	//
    // DeveloperExamples_Servers
    //
	//============================================================================
    public ModelTable getDeveloperExamplesServers()
    {
    	return developerExamplesDAO.getDeveloperExamplesServers();
    }
    
}
