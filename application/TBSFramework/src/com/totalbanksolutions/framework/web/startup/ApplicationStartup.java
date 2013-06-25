package com.totalbanksolutions.framework.web.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.dataservice.util.DataServiceManager;
import com.totalbanksolutions.framework.service.BackgroundService;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 *            10 Oct 2012 VC #1916: Java Caching issue - new Eagle bank not showing up in DI drop-downs
 * =================================================================================================
 */
public class ApplicationStartup implements ServletContextListener 
{
	protected final Log log = LogFactory.getLog( ApplicationStartup.class );
	private BackgroundService backgroundService = null;
	private Thread serviceThread = null;
	
	public void contextInitialized(ServletContextEvent event) 
	{
		try
		{
			// Do stuff during webapp's startup.
			log.debug("contextInitialized");
			
			String cacheDefaultExpiresInSeconds = event.getServletContext().getInitParameter("cacheDefaultExpiresInSeconds");
			log.debug( "cacheDefaultExpiresInSeconds=" + cacheDefaultExpiresInSeconds );

			String backgroundServiceClass = event.getServletContext().getInitParameter("backgroundServiceClass");
			log.debug( "backgroundServiceClass=" + backgroundServiceClass );
			
			DataServiceManager dsm = new DataServiceManager();
			dsm.setCacheDefaultExpiresInSeconds( Integer.parseInt(cacheDefaultExpiresInSeconds) );
			DataService ds = dsm.getDataService();
			
			event.getServletContext().setAttribute("dataService", ds);
			
			if( backgroundServiceClass != null && backgroundServiceClass.length() > 0 )
			{
				if( backgroundService == null )
				{
					backgroundService = (BackgroundService)Class.forName(backgroundServiceClass).newInstance();
					if ( (serviceThread == null) || (!serviceThread.isAlive()) ) 
					{
						serviceThread = new Thread(backgroundService);
						serviceThread.start();
					}
				}
			}
		}
		catch(Exception e)
		{
			log.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));		
		}
	}

	public void contextDestroyed(ServletContextEvent event) 
	{
		// Do stuff during webapp's shutdown.
		log.debug("contextDestroyed");
		try 
		{
			if( backgroundService != null )
			{
				backgroundService.doShutDown();
			}
			serviceThread.interrupt();
        } catch (Exception ex) { }
	}

}
