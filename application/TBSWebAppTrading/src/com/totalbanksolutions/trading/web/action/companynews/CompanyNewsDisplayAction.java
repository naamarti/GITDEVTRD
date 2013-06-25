package com.totalbanksolutions.trading.web.action.companynews;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class CompanyNewsDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( CompanyNewsDisplayAction.class );
	private DataService ds;

	public CompanyNewsDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);

		h.setViewName("companyNews");
		h.setScreenName("Company News");

		return true;
	}

}
