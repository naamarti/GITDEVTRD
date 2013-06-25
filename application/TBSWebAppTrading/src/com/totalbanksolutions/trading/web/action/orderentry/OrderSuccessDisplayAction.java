package com.totalbanksolutions.trading.web.action.orderentry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class OrderSuccessDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( OrderSuccessDisplayAction.class );
	private DataService ds;

	public OrderSuccessDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);

		h.setViewName("orderSuccess");
		h.setScreenName("Order Submitted");

		return true;
	}

}
