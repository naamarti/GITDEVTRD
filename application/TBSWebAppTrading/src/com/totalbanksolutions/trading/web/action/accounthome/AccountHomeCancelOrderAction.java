package com.totalbanksolutions.trading.web.action.accounthome;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;

public class AccountHomeCancelOrderAction implements Action
{
	protected final Log log = LogFactory.getLog( AccountHomeCancelOrderAction.class );
	private DataService ds;

	public AccountHomeCancelOrderAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");

		String orderId = WebUtils.getStringParameterValue( request, "orderId" );		
		log.debug("### order Id to be canceled from request object is [" + orderId + "] ###");
		
		this.ds.trading.cancelUserOrder(orderId);
		
		return true;
	}

}
