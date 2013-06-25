package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.siteagreement.SiteAgreementDisplayAction;
import com.totalbanksolutions.trading.web.action.siteagreement.SiteAgreementContinueAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author DJM
 */
public class SiteAgreementController extends AbstractControllerNew
{    
	public SiteAgreementController() 
    {
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

    @Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
    	ModelHelper h = new ModelHelper(modelAndView);
		if ( h.getSessionUserAction().equalsIgnoreCase("continue") )
		{				
			new SiteAgreementContinueAction( this.ds ).processAction( request, modelAndView );
		}
		
		new SiteAgreementDisplayAction( this.ds ).processAction( request, modelAndView );
	}
}
