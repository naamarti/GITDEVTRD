package com.totalbanksolutions.trading.web.action.financialdocuments;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class FinancialDocumentsDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( FinancialDocumentsDisplayAction.class );
	private DataService ds;

	public FinancialDocumentsDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);

		h.setViewName("financialDocuments");
		h.setScreenName("Financial Documents");

		return true;
	}

}
