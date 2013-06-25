package com.totalbanksolutions.trading.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.totalbanksolutions.framework.web.controller.Controller;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.trading.web.model.ModelHelper;
import com.totalbanksolutions.trading.web.util.ControllerUtility;

/**
 * @author vcatrini
 * Modified: 28 Sep 2011 VC #1010: Modify the way DMS distinguishes between SECURE and NON-SECURE environments.
 */  
public abstract class AbstractControllerNew implements Controller 
{    
	public abstract void processRequest(HttpServletRequest request, ModelAndView mv);

    protected final Log log = LogFactory.getLog(getClass());
    protected boolean isValidateUser = false;
    protected boolean isSetMenuList = false;
    
    protected DataService ds = null;
    protected HttpServletResponse response = null;
    
    public AbstractControllerNew(boolean isValidateUser, boolean isSetMenuList) 
    {
		super();
		this.isValidateUser = isValidateUser;
		this.isSetMenuList = isSetMenuList;
	}

	public void setDataService(DataService ds)
    {
    	this.ds = ds;
    }

	public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
	{
		log.debug("handleRequest...");
		ModelAndView modelAndView = new ModelAndView();
		ModelHelper h = new ModelHelper(modelAndView);
		this.response = response;
		
		try
		{
			WebUtils.setHostMode(request);
			h.setSessionUserAction( WebUtils.getStringParameterValue(request, "sessionUserAction") );
			h.setEnvironmentName( ds.trading.getAppConfigEnvironmentName() );
			h.setVersionNumber( ds.trading.getAppConfigVersionNumber() );
			h.setUser( ControllerUtility.getUserSessionDetails(request) );
			
			h.setAjaxQueryType( WebUtils.getStringParameterValue(request, "ajaxQueryType") );
		    if( h.getAjaxQueryType().length() > 0 )
		    {
		    	//webBean.setAjaxRequest(true);
				response.setContentType("text/xml");
				h.setViewName("ajaxQueryResults");
				h.setAjaxStatus( false );
		    }
			
			if( this.isValidateUser && !h.getAjaxQueryType().equalsIgnoreCase("get") )
			{
				log.debug( "validating user " + h.getUser().getUserId() + "," + h.getUser().getSessionKey() );
				h.setUser( ds.trading.validateAppUserSession(h.getUser()) );
			    if( h.getUser() == null )
			    {
			    	//response.sendRedirect("login.htm?sessionUserAction=timeout");
			    	//return null;
			    	return new ModelAndView( "sessionTimeout", "model", modelAndView.getModel() );
			    }
			}

			try
			{
				log.debug("processRequest...");
				processRequest( request, modelAndView );
			}
			catch(Exception e)
			{
				h.setMessageError( "An unexpected error has occured. Please try again.");
				h.setMessageDebug( ExceptionUtils.getMessage(e) );
				log.error( h.getMessageError() + "\n" + h.getMessageDebug() + "\n" + ExceptionUtils.getStackTrace(e) );
			}

			if( h.getAjaxQueryType().length() > 0 )
			{
				h.setAjaxStatus(true);
			}
//			else if( webBean.isForwardRequest() )
//			{
//				h.setViewName( "forward:" + h.getViewName() + ".htm" );
//			}
			else
			{
				if( this.isSetMenuList )
				{
					log.debug("setting menu lists...");
					if( h.getUser().getUserId() > 0 )
					{
//						h.setMenuPrimary( ds.security.getAppUserNavigationListNew( h.getUser().getUserId(), ControllerUtility.getSecureURLPrefix(request)) );
					}
//					ds.security.setMenuSecondary( h.getViewName(), h.getMenuSecondaryLevel1(), h.getMenuSecondaryLevel2() );
					log.debug("setting menu lists done.");
				}
			}
		}
		catch(Exception e)
		{
			h.setMessageError( "An unexpected error has occured. Please try again.");
			h.setMessageDebug( ExceptionUtils.getMessage(e) );
			log.error( h.getMessageError() + "\n" + h.getMessageDebug() + "\n" + ExceptionUtils.getStackTrace(e) );
		}
		
		return modelAndView;
	}

	public boolean isSetMenuList() {
		return isSetMenuList;
	}

	public void setMenuList(boolean isSetMenuList) {
		this.isSetMenuList = isSetMenuList;
	}
	

}
