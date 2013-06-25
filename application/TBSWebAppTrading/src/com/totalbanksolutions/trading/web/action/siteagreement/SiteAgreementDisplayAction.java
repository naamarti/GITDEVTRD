package com.totalbanksolutions.trading.web.action.siteagreement;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class SiteAgreementDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( SiteAgreementDisplayAction.class );
	
	private DataService ds;

	public SiteAgreementDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("### SiteAgreementDisplayAction::processAction ###");
		ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("siteAgreement");
		h.setScreenName("Site Agreement");

		log.debug("### getting active user ID from model helper ... ###");
		long userId = h.getUser().getUserId();
		log.debug("### userId " + userId + " ###");
		
		boolean b = this.ds.trading.isDisclaimerApproved(userId);
		
		log.debug("### user has accepted site agreement? " + b + " ###");
		
		return true;
	}

}
