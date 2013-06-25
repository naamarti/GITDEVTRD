package com.totalbanksolutions.framework.dao.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class NonContainerDataSource 
{
	private final Logger log = Logger.getLogger(NonContainerDataSource.class);
	
	public DataSource getDataSource()
	{
		try
		{
			Properties prop = new Properties();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			String url = prop.getProperty("DB_URL");
			String driver = prop.getProperty("DB_DRIVER");
			String user = prop.getProperty("DB_USER");
			String password = prop.getProperty("DB_PASSWORD");
	
			log.debug( "DB_URL=" + url );
			log.debug( "DB_DRIVER=" + driver );
			log.debug( "DB_USER=" + user );
			log.debug( "DB_PASSWORD=" + password );
	
			PoolProperties p = new PoolProperties();
		    p.setUrl(url);
		    p.setDriverClassName(driver);
		    p.setUsername(user);
		    p.setPassword(password);

			p.setMaxActive(5);
			p.setMaxIdle(5);
			p.setMinIdle(1);
			p.setMaxWait(30000);
			p.setInitialSize(1);
			p.setRemoveAbandoned(false);
			//p.setRemoveAbandonedTimeout(120);
			p.setLogAbandoned(false);
			p.setValidationQuery("SELECT 1");
			p.setValidationInterval(30000);
			p.setTestWhileIdle(false);
			p.setTestOnBorrow(false);
			p.setTestOnReturn(false);
			p.setTimeBetweenEvictionRunsMillis(60000);
			p.setJmxEnabled(false);
			p.setJdbcInterceptors("ConnectionState; StatementFinalizer");
		    DataSource datasource = new DataSource();
		    datasource.setPoolProperties(p); 
		    return datasource;
		}
        catch (Exception e)
		{
			log.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));		
			throw new RuntimeException(e);
		}
	}
}
