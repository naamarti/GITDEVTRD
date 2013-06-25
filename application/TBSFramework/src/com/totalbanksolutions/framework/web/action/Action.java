package com.totalbanksolutions.framework.web.action;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.model.ModelAndView;

/**
 * 
 * @author vcatrini
 *
 */
public interface Action
{	

	//public void setDataService( DataService ds );
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView );
	
}
