package com.totalbanksolutions.trading.web.action.sitemap;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;

import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class SiteMapDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( SiteMapDisplayAction.class );
	private DataService ds;

	public SiteMapDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("display site map...");
		ModelHelper h = new ModelHelper(modelAndView);

		h.setViewName("siteMap");
		h.setScreenName("Site Map");
		long userId = h.getUser().getUserId();
		log.debug("### userId " + userId + " ###");
		
		boolean isFinanceRole = this.ds.trading.isAppUserInRole(userId, 2);  		//TODO: get real ROLE_FK for finance here
		h.setIsFinanceRole(isFinanceRole);
		
	    return true;
	}

}
