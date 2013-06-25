package com.totalbanksolutions.trading.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.util.DateUtility;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.orderentry.OrderEntryDisplayAction;
import com.totalbanksolutions.trading.web.action.orderentry.OrderPreviewDisplayAction;
import com.totalbanksolutions.trading.web.action.orderentry.OrderEntrySubmitAction;
import com.totalbanksolutions.trading.web.action.orderentry.OrderEntryValidateAction;
import com.totalbanksolutions.trading.web.action.orderentry.OrderSuccessDisplayAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Val Catrini
 */
public class OrderEntryController extends AbstractControllerNew 
{
	public OrderEntryController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
    	ModelHelper h = new ModelHelper(modelAndView);
    	boolean status = true;
    	String view = "";
    	
		if( h.getFormData1() == null )
		{
			h.setFormData1( new TradingUserOrderView() );
		}
    	
		Date nextOrderTradingDate = this.ds.trading.getNextOrderTradingDate();
		DateUtility dtUtil = new DateUtility();
		h.setNextOrderTradingDate(dtUtil.convertDateToString(nextOrderTradingDate, "MMMM d, yyyy") );

		/******************************************************************
		 * Process incoming request:
		 ******************************************************************/
		if ( h.getSessionUserAction().equalsIgnoreCase("previewOrder") )
		{
			status = new OrderEntryValidateAction( this.ds ).processAction( request, modelAndView );
			if(status)
			{
				status = new OrderPreviewDisplayAction( this.ds ).processAction( request, modelAndView );
			}
			else
			{
				status = new OrderEntryDisplayAction( this.ds ).processAction( request, modelAndView );
			}
		}
		else if ( h.getSessionUserAction().equalsIgnoreCase("editPreOrder") )
		{
			status = new OrderEntryValidateAction( this.ds ).processAction( request, modelAndView );
			status = new OrderEntryDisplayAction( this.ds ).processAction( request, modelAndView );
		}		
		else if ( h.getSessionUserAction().equalsIgnoreCase("submitOrder") )
		{
			status = new OrderEntryValidateAction( this.ds ).processAction( request, modelAndView );
			if(status)
			{
				status = new OrderEntrySubmitAction( this.ds ).processAction( request, modelAndView );
				if(status)
				{
					status = new OrderSuccessDisplayAction( this.ds ).processAction( request, modelAndView );
				}
				else
				{
					status = new OrderEntryDisplayAction( this.ds ).processAction( request, modelAndView );
				}
			}
			else
			{
				status = new OrderEntryDisplayAction( this.ds ).processAction( request, modelAndView );
			}
		}
		else
		{
			status = new OrderEntryDisplayAction( this.ds ).processAction( request, modelAndView );
		}
		

		/******************************************************************
		 * Display appropriate view:
		 ******************************************************************/
	}

}
