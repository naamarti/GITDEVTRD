package com.totalbanksolutions.trading.web.action.siteagreement;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class SiteAgreementContinueAction implements Action
{
	protected final Log log = LogFactory.getLog( SiteAgreementContinueAction.class );
	private DataService ds;
	
    public SiteAgreementContinueAction(DataService ds)
    {
    	this.ds = ds;
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("### Site Agreement Continue action ###");
		LoginModelHelper h = new LoginModelHelper(modelAndView);
		boolean status = true;

		try
		{
			this.ds.trading.setDisclaimerApproved(h.getUser().getUserId());
			h.setIsDisclaimerApproved(true);
			h.setMessageSuccess( "Site Agreement Disclaimer Approved." );
    	}
		catch(Exception e)
		{					
			h.setMessageError( "Unexpected error while saving site agreement flag. Please try again.<br>" + ExceptionUtils.getMessage(e) );
			log.error( h.getMessageError() + "\n" + ExceptionUtils.getStackTrace(e) );
			status = false;
		}
		return status;
	}
}
