package com.totalbanksolutions.trading.web.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppRole;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.bean.web.UserSession;
import com.totalbanksolutions.framework.web.util.WebUtils;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.util.ConvertUtility;

/**
 * @author vcatrini
 * Modified: 28 Sep 2011 VC #1010: Modify the way DMS distinguishes between SECURE and NON-SECURE environments.
 */  
public class ControllerUtility 
{
	/** Logger for this class and subclasses */
    static protected final Log log = LogFactory.getLog(ControllerUtility.class);
	
	public static void addUserSession(HttpServletRequest request, UserSession userSession)
	{
		//Get the existing session if one exists, if not create a new one
		HttpSession session = request.getSession(true);
		session.setAttribute("userSession", userSession);
	}

	public static UserSession getUserSession(HttpServletRequest request)
	{
		UserSession userSession = null;
		// Get the existing session if one exists, but DO NOT create a new one
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		userSession = (UserSession)session.getAttribute("userSession");
		return userSession;
	}

	//public static void updateUserSession(HttpServletRequest request, User user)
	//{
		// Get the existing session if one exists, but DO NOT create a new one
		//HttpSession session = request.getSession(false);
	    //session.setAttribute("userSession", user);
	//}

	public static void removeUserSession(HttpServletRequest request)
	{
		// Get the existing session if one exists, but DO NOT create a new one
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("userSession");
			session.invalidate();
		}
	}
	
	public static ViewAppUserSession getUserSessionDetails(HttpServletRequest request)
	{
		ViewAppUserSession user = new ViewAppUserSession();
		try
		{
			long sessionUserId = WebUtils.getLongParameterValue(request, "sessionUserId");
			user.setUserId( sessionUserId );
	
			String sessionKey = WebUtils.getStringParameterValue(request, "sessionKey");
			user.setSessionKey( sessionKey );
	
			long sessionNavigationId = WebUtils.getLongParameterValue(request, "sessionNavigationId");
			user.setNavigationId( sessionNavigationId );
	
			long programId = WebUtils.getLongParameterValue(request, "programId");
			if (programId > 0)
		    {
				user.setWorkingProgramId( programId );
		    }
			
			long sourceInstitutionId = WebUtils.getLongParameterValue(request, "sourceInstitutionId");
			if(sourceInstitutionId > 0)
			{
				user.setWorkingSourceInstitutionId( sourceInstitutionId );
			}

			String programDate = WebUtils.getStringParameterValue(request, "programDate");
			if (programDate.length() > 0)
		    {
				Date d = ConvertUtility.parseDate(programDate);				
				user.setWorkingDate( d );
		    }
		
		}
		catch(Exception e)
		{
			String errorMessage = "Error getting session details.";
			String errorStack = ExceptionUtils.getStackTrace(e);
			log.error(errorMessage + "\n" + errorStack);
			return null;
		}
		return user;
	}

	public static String getSecureURLPrefix(HttpServletRequest request)
	{	
//		log.debug("SECURE HOST CHECK URL = " + request.getRequestURL() + "; URI = " + request.getRequestURI());
		if(request.getRequestURL().indexOf(".secure.") > 0)
		{
			return "secure";
		}
		else if(request.getRequestURL().indexOf(".sekure.") > 0)
		{
			return "sekure";
		}
		else
		{
			return "";
		}	
	}

}
