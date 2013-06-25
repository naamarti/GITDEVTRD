package com.totalbanksolutions.framework.dao.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.totalbanksolutions.framework.util.AppUtility;

/**
 * Gets the appropriate dataSource depending on which environment you are running:
 * 1. WebLogic - gets the dataSource (connection pool) from WebLogic JNDI
 * 2. Tomcat - gets the dataSource (connection pool) from Tomcat JNDI
 * 3. Stand Alone - gets the dataSource (no connection pool)
 * @author vcatrini
 */
public class DataSourceManager
{
	/**
	 * Instance to Log4J logger.
	 */
	private final Logger log = Logger.getLogger(DataSourceManager.class);

    /**
     * DataSource to Connection Pool in J2EE server.
     */
    private DataSource ds = null;

    /**
	 * JNDI Name to DataSource in J2EE server.
	 */
    private final String jndiName = "jdbc/appDataSource";

    /**
     * Constructor that initializes the DataSource
     */
    public DataSourceManager() {
        log.info("DataSourceManager init...");
        try
		{			
            if (AppUtility.isTomcatContainer())
            {
				log.info("Tomcat container");
            	Context ctx = new InitialContext();
    			String jndiURL = jndiName;
				// Tomcat requires this prefix, while WebLogic has it by default
				jndiURL = "java:comp/env/" + jndiName;
				ds = (DataSource)ctx.lookup(jndiURL);
            }
            else
            {
                if (AppUtility.isWLContainer())
                {
                	Context ctx = new InitialContext();
        			String jndiURL = jndiName;
			        log.info("WL container");
			        ds = (DataSource)ctx.lookup(jndiURL);
                }
                else
                {
			        log.info("NO container, creating JDBC connection from config.properties");
			        ds = new NonContainerDataSource().getDataSource();
                }
            }
		}
		catch (Exception e)
		{
			log.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
    }

	/**
	 * Returns DataSource retrieved from JNDI.
	 */
    public DataSource getDataSource() {
        return ds;
    }
    
}
