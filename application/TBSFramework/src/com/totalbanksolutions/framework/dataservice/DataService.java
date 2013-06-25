package com.totalbanksolutions.framework.dataservice;

import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.totalbanksolutions.framework.cache.CacheManager;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 * =================================================================================================
 */
public class DataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private CacheManager cacheManager = null;
    
    public AutomationDataService automation = null;
    public DeveloperExamplesDataService developerExamples = null;
    public FileLoaderDataService fileLoader = null;
    public FileWriterDataService fileWriter = null;
    public ProcessingDataService processing = null;
    public ReportsDataService reports = null;
    public SecurityDataService security = null;
    public TradingDataService trading = null;
    public UtilDataService util = null;
    public DashboardDataService dashboard = null;
    
    public void setCacheManager(CacheManager cacheManager)
    {
    	this.cacheManager = cacheManager;
    }

    public void setDataSource(DataSource ds) 
    {
		this.dataSource = ds;
		this.automation = new AutomationDataService(dataSource, cacheManager, this);
		this.developerExamples = new DeveloperExamplesDataService(dataSource, cacheManager, this);
		this.fileLoader = new FileLoaderDataService(dataSource, cacheManager, this);
		this.fileWriter = new FileWriterDataService(dataSource, cacheManager, this);
		this.processing = new ProcessingDataService(dataSource, cacheManager, this);
		this.reports = new ReportsDataService(dataSource, cacheManager, this);
		this.security = new SecurityDataService(dataSource, cacheManager, this);
		this.util = new UtilDataService(dataSource, cacheManager, this);
		this.dashboard = new DashboardDataService(dataSource, cacheManager, this);
		this.trading = new TradingDataService(dataSource, cacheManager, this);
    }

	public void close()
	{
		this.automation.close();
		this.developerExamples.close();
		this.fileLoader.close();
		this.fileWriter.close();
		this.processing.close();
		this.reports.close();
		this.security.close();
		this.util.close();
		this.dashboard.close();
		this.trading.close();
		
		this.automation= null;
		this.developerExamples = null;
		this.fileLoader = null;
		this.fileWriter = null;
		this.processing = null;
		this.reports = null;
		this.security = null;
		this.util = null;
		this.dashboard = null;
		this.cacheManager = null;
		this.trading = null;
	}

}
