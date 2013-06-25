package com.totalbanksolutions.framework.file;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IOUtility 
{
	protected static final Log log = LogFactory.getLog(IOUtility.class);

	public static String getWindowsAbsolutePath(String path)
	{
		String windowsPath = null;
		File file = new File(path);
		
		windowsPath = file.getAbsolutePath();
		return windowsPath;
	}
	
	public static String getDirNameWithPathSepSuffix(String dirName)
	{
		String fixDirName = dirName;
		if(dirName != null && dirName.length() > 0)
		{
			if(!dirName.endsWith("/"))
			{
				fixDirName = dirName + "/";
			}
		}
		return fixDirName;
	}

	public static void createDirectoryIfNotExists(String dirName)
	{
		File dir = new File(dirName);
		if(!dir.isDirectory())
		{
			if(!dir.mkdir())
			{
				throw new RuntimeException("Unable to create directory, '" + dirName + "'.");
			}
		}
	}

	public static boolean directoryExists(String dirName)
	{
		File dir = new File(dirName);
		if(dir.isDirectory())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean fileExists(String fileName)
	{
		File f = new File(fileName);
		if(f.isFile())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
