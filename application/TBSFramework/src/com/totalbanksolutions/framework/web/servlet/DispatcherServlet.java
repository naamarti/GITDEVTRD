package com.totalbanksolutions.framework.web.servlet;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.controller.AbstractControllerFactory;
import com.totalbanksolutions.framework.web.controller.Controller;
import com.totalbanksolutions.framework.web.model.ModelAndView;

/**
 * 
 * @author vcatrini
 *
 */
public class DispatcherServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
{
	protected final Log log = LogFactory.getLog( DispatcherServlet.class );
	private static final long serialVersionUID = 1L;
	
	private DataService dataService;
	//private AbstractControllerFactory controllerFactory;
	private String controllerClassRoot = "";
	private Map<String,RequestDispatcher> requestDispatcherMap = new HashMap<>();
	private Map<String,String> controllerMap = new HashMap();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet");
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost");
		performTask(request, response);
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			log.debug("performTask");
			response.setContentType("text/html");
			String page = getPage(request);
			
			log.debug( "URI=" + request.getRequestURI() );
			log.debug( "URL=" + request.getRequestURL() );
			log.debug( "ContextPath=" + request.getContextPath() );
			log.debug( "QueryString=" + request.getQueryString() );
			log.debug( "Page=" + page );

			RequestDispatcher rd = null;
			String viewName = "error";
			boolean cont = true;
			while(cont)
			{
				//Controller c = controllerFactory.getController(page);
				Controller c = getController(page);
				
				c.setDataService(this.dataService);
				ModelAndView mv = c.handleRequest(request, response);
				viewName = mv.getViewName();
				if( viewName.startsWith("forward:") )
				{
					String oldPage = page;
					page = StringUtils.substringAfter( viewName, "forward:" );
					log.debug("forwarding from page '" + oldPage + "' to page '" + page + "' ...");
					
					//rd = getServletContext().getRequestDispatcher("/" + page);
					//rd.forward(request, response);
					//return;
					
				}
				else
				{
					request.setAttribute(mv.getModelId(), mv.getModel());
					cont = false;
				}
			}
			
			log.debug("Get RequestDispatcher for '" + viewName + "'...");
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/" + viewName + ".jsp");
			//RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/" + viewName + ".jsp");
			
			if( this.requestDispatcherMap.containsKey(viewName) )
			{
				log.debug("Found in Map, RequestDispatcher for '" + viewName + "'");
				rd = this.requestDispatcherMap.get(viewName);
			}
			else
			{
				log.debug("Not Found in Map, RequestDispatcher for '" + viewName + "'");
				rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/" + viewName + ".jsp");
				if( rd != null )
				{
					this.requestDispatcherMap.put( viewName, rd );
				}
				else
				{
					rd = this.requestDispatcherMap.get("error");
				}
			}
			
			log.debug("RequestDispatcher.forward to '" + viewName + "'...");
			rd.forward(request, response);
			//response.getWriter().flush();
			//response.getWriter().close();
			//response.flushBuffer();
			
			log.debug("RequestDispatcher.forward to '" + viewName + "' done.");
		}
		catch(Exception e)
		{
			log.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));		
		}
	}

	public void init(ServletConfig config) throws ServletException
    {
		try
		{
			super.init(config);
			log.debug("init");
			
			//String controllerFactory = config.getInitParameter("controllerFactory");
			//log.debug( "controllerFactory=" + controllerFactory );

			this.controllerClassRoot = config.getInitParameter("controllerClassRoot");
			log.debug( "controllerClassRoot=" + this.controllerClassRoot );
			
			this.dataService = (DataService)getServletContext().getAttribute("dataService");
			
			//this.controllerFactory = (AbstractControllerFactory)Class.forName(controllerFactory).newInstance();
			
			initializeRequestDispatchers();
		}
		catch(Exception e)
		{
			log.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));		
		}
    }

	private String getPage( HttpServletRequest request )
	{
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String page = uri.substring( contextPath.length() + 1 );
		int sessionIndex = page.indexOf(";jsessionid");
		if( sessionIndex > 0 )
		{
			String newPage = page.substring(0, sessionIndex);
			log.debug( "page=" + page + "; newPage=" + newPage );
			page = newPage;
		}
		return page;
	}
	
	private Controller getController( String page )
	{
		Controller c = null;
		String controllerName = "";
		try
		{
			if( this.controllerMap.containsKey(page) )
			{
				controllerName = this.controllerMap.get(page);
			}
			else
			{
				controllerName = page.replace(".htm", "");
				controllerName = controllerName.substring(0, 1).toUpperCase() + controllerName.substring(1) + "Controller";
				this.controllerMap.put( "page", controllerName );
			}
			log.debug( "controllerName=" + controllerName );
			c = (Controller)Class.forName(this.controllerClassRoot + "." + controllerName).newInstance();
		}
		catch(Exception e)
		{
			log.error("getController() : " + ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));
			try
			{
				c = (Controller)Class.forName(this.controllerClassRoot + ".ErrorController").newInstance();				
			}
			catch(Exception e1) { }
		}
		return c;
	}
	
	private void initializeRequestDispatchers()
	{
		ServletContext sc = getServletContext();
		String dirJSP = getServletContext().getRealPath("/WEB-INF/jsp/");
		Path p = Paths.get(dirJSP);
		try ( DirectoryStream<Path> ds = Files.newDirectoryStream( p, "*.jsp" ) )
		{
			for (Path path : ds) 
			{
				String f = path.getFileName().toString();
				log.debug( "Initializing RequestDispatcher for '" + f + "' ");
				this.requestDispatcherMap.put( StringUtils.substringBefore(f, ".jsp"), sc.getRequestDispatcher( "/WEB-INF/jsp/" + f ) );
			}
		} 
		catch ( IOException e ) 
		{
			log.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));		
		}
	}
}
