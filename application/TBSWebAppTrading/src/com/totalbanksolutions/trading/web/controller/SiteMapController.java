package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.sitemap.SiteMapDisplayAction;

/**
 * @author Val Catrini
 */
public class SiteMapController extends AbstractControllerNew 
{
	public SiteMapController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		new SiteMapDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
