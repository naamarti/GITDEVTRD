package com.totalbanksolutions.trading.web.action.sitedocuments;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class SiteDocumentsDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( SiteDocumentsDisplayAction.class );
	private DataService ds;

	public SiteDocumentsDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);

		h.setViewName("siteDocuments");
		h.setScreenName("Site Documents");

		return true;
	}

}
