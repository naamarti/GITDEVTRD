package com.totalbanksolutions.framework.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.util.AppUtility;

public class WebUtils 
{
	/** Logger for this class and subclasses */
    static protected final Log log = LogFactory.getLog(WebUtils.class);
	
	public static void setHostMode(HttpServletRequest request)
	{	
		if(request.getRequestURL().indexOf("localhost") > 0)
		{
			AppUtility.setLocalHostURLOverride(true);
			AppUtility.setAppHttpPort( request.getLocalPort() );
		}
	}
	
	public static boolean getBooleanParameterValue(HttpServletRequest request, String parameterName)
	{
		boolean value = false;
		String param = null;
		param = request.getParameter(parameterName);
		if ( param != null && param.length() > 0 )
	    {
			value = true;
	    }
		return value;
	}

	public static int getIntParameterValue(HttpServletRequest request, String parameterName)
	{
		int value = 0;
		String param = null;
		param = request.getParameter(parameterName);
		if ( param != null && param.length() > 0 )
	    {
			value = Integer.parseInt(param);
	    }
		return value;
	}

	public static long getLongParameterValue(HttpServletRequest request, String parameterName)
	{
		long value = 0;
		String[] param = request.getParameterValues(parameterName);
		if ( param != null && param[0].length() > 0 )
	    {
			value = Long.parseLong(param[0]);
	    }
		return value;
	}

	public static String getStringParameterValue(HttpServletRequest request, String parameterName)
	{
		String defaultValue = "";
		return getStringParameterValue(request, parameterName, defaultValue);
	}

	public static String getStringParameterValue(HttpServletRequest request, String parameterName, String defaultValue)
	{
		String value = defaultValue;
		String param = null;
		param = request.getParameter(parameterName);
		if (param != null)
	    {
			value = param.trim();
	    }
		return value;
	}

	public static List<Long> getSelectedIdsList(Map<?,?> paramMap, String prefix)
	{
		List<Long> dataList = new ArrayList<>();
		for(Map.Entry<?,?> entry : paramMap.entrySet())
		{
			String paramName = (String)entry.getKey();
			if(paramName.startsWith(prefix))
			{
				String selectedId = paramName.substring(prefix.length());
				dataList.add(Long.parseLong(selectedId));
			}
		}
		return dataList;
	}
	
}
