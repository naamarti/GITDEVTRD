package com.totalbanksolutions.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.log4j.Logger;

import com.totalbanksolutions.framework.dao.util.DAO;

/**
 * @author vcatrini
 */
public class AppUtility
{
	private static Properties			applicationProps;

	private static Logger				log						= Logger.getLogger(AppUtility.class);

	private static boolean				startupPropsLoadedFlg	= false;

	private static boolean				isTomcatContainer		= false;

	private static boolean				isWLContainer			= false;

	private static boolean				isSeparateVM			= true;

    private static boolean 				isLocalHostURLOverride 	= false;
    
    private static int					appHttpPort				= 80;

	static
	{
		if (System.getProperty("STANDALONE_JVM") != null)
		{
			isSeparateVM = true;
			log.info("Standalone JVM");
		}
		else
		{
			//Check if running in WL
			if (System.getProperty("java.vm.vendor") != null
					&& (new StringBuffer(System.getProperty("java.vm.vendor"))
							.indexOf("BEA") >= 0) )
			{
				isWLContainer = true;
				log.info("Weblogic container");
			}
			else
			{
				//Check if it is Tomcat Container by checking catalina.base.
				if (System.getProperty("catalina.base") != null )
				{
					isTomcatContainer = true;
					log.info("Tomcat container");
				}
				else
				{
					isSeparateVM = true;
					log.info("Standalone JVM");
				}
			}
		}
	}

	/**
	 * @return Returns the isSeparateVM.
	 */
	public static boolean isSeparateVM ( )
	{
		return isSeparateVM;
	}

	/**
	 * @return Returns the isTomcatContainer.
	 */
	public static boolean isTomcatContainer ( )
	{
		return isTomcatContainer;
	}

	/**
	 * @return Returns the isWLContainer.
	 */
	public static boolean isWLContainer ( )
	{
		return isWLContainer;
	}

	/**
	 * Returns current time in a printable format
	 * 
	 * @return String
	 */
	public static String getCurrentTime ( )
	{
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE, d MMM yyyy hh:mm:ss:S aaa");
		df.setTimeZone(TimeZone.getDefault());

		return df.format(dt);
	}

	/**
	 * Set properties for application
	 * 
	 * @param props
	 *            is the properties to be set
	 */
	public static void setApplicationProperties ( Properties props )
	{
		if (props == null )
			return;
		if (applicationProps == null )
		{
			applicationProps = props;
		}
		else
		{
			//If the global props already exist, append to it
			Enumeration<Object> keyEnum = props.keys();
			while ( keyEnum.hasMoreElements() )
			{
				String key = keyEnum.nextElement().toString();
				applicationProps.put(key, props.getProperty(key));
			}
		}
	}

	/**
	 * Retreive application properties
	 * 
	 * @return Properties set for this application
	 */
	public static Properties getApplicationProperties ( ) throws Exception
	{
		if (startupPropsLoadedFlg == false )
		{
			//Assumes that there is a default properties file in directory conf
			try
			{
				if (AppUtility.isSeparateVM )
				{
					log.info("Loading default conf/global.properties");
					loadStartupProps("../conf/global.properties");
				}
			}
			catch ( Exception e )
			{
				throw e;
			}
		}
		return applicationProps;
	}

	/**
	 * Load the properties for a file
	 * 
	 * @param fileName
	 *            has the name of the file to read from
	 * @return Properties load from the file
	 */
	public static Properties loadProperties ( String fileName )
			throws Exception
	{
		if (fileName == null )
			return null;
		InputStream propsFile;
		Properties tempProp = new Properties();

		try
		{
			propsFile = new FileInputStream(fileName);
			tempProp.load(propsFile);
			propsFile.close();

		}
		catch ( IOException ioe )
		{
			log.error("Error reading configuration file. " + ioe);
			throw ioe;
		}

		return tempProp;
	}

	/**
	 * Load the properties file at application start time
	 * 
	 * @param fileName
	 *            is the name of the properties file
	 */
	public static void loadStartupProps ( String fileName ) throws Exception
	{
		if (fileName == null )
			return;
		try
		{
			Properties p = AppUtility.loadProperties(fileName);
			setApplicationProperties(p);
			if (p == null )
			{
				log.warn("Global properties files not found...");
			}
			else
			{
				startupPropsLoadedFlg = true;
				log.info("Loaded properties file " + fileName);
			}
		}
		catch ( Exception e )
		{
			throw e;
		}
	}

	public static void runGC ()
    {
        // It helps to call Runtime.gc()
        // using several method calls:
        for (int r = 0; r < 4; ++ r) 
        {
        	try
			{
				_runGC ();
			}
			catch ( Exception e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private static void _runGC () throws Exception
    {
        long usedMem1 = usedMemory (), usedMem2 = Long.MAX_VALUE;
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++ i)
        {
            s_runtime.runFinalization ();
            s_runtime.gc ();
            Thread.yield ();            
            usedMem2 = usedMem1;
            usedMem1 = usedMemory ();
        }
    }

    public static long usedMemory ()
    {
        return s_runtime.totalMemory () - s_runtime.freeMemory ();
    }
    
    public static String getMemoryUsageDetails()
    {
		long maxMemory = s_runtime.maxMemory();
		long totalMemory = s_runtime.totalMemory(); 
		long freeMemory = s_runtime.freeMemory(); 
		NumberFormat nf = NumberFormat.getNumberInstance();
	    nf.setMinimumFractionDigits(0);
	    nf.setMaximumFractionDigits(0);
	    nf.setGroupingUsed(true);
    	StringBuffer sb = new StringBuffer();
    	sb.append( "  MAX_MEMORY   =" + nf.format(maxMemory) )
    	  .append( ", TOTAL_MEMORY =" + nf.format(totalMemory) )
  	  	  .append( ", FREE_MEMORY  =" + nf.format(freeMemory) );
    	return sb.toString();
    }
    
    private static final Runtime s_runtime = Runtime.getRuntime ();

	public static boolean isLocalHostURLOverride() 
	{
		return AppUtility.isLocalHostURLOverride;
	}

	public static void setLocalHostURLOverride(boolean isLocalHostURLOverride) 
	{
		AppUtility.isLocalHostURLOverride = isLocalHostURLOverride;
	}

	public static int getAppHttpPort()
	{
		return AppUtility.appHttpPort;
	}
	
	public static void setAppHttpPort( int port )
	{
		AppUtility.appHttpPort = port;
	}

}