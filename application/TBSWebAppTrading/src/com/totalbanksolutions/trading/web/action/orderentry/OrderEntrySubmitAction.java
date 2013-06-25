package com.totalbanksolutions.trading.web.action.orderentry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class OrderEntrySubmitAction implements Action
{
	protected final Log log = LogFactory.getLog( OrderEntrySubmitAction.class );
	private DataService ds;

	public OrderEntrySubmitAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);
		boolean status = true;

		try
		{
			TradingUserOrderView t = (TradingUserOrderView)h.getFormData1();
			
			boolean isOrderLocked = this.ds.trading.checkIsOrderLocked();
			h.setIsOrderLocked(isOrderLocked);

			if(!isOrderLocked)
			{
				t.addRow( request.getParameterMap() );
				log.debug( t.toString() );
			
				String orderNumber = this.ds.trading.insertUserOrder( t, h.getUser().getUserId() );
				h.setOrderNumber(orderNumber);
				h.setMessageSuccess( "Order successfuly submitted." );
			}
			else
			{
				h.setMessageError( "Order locked." );
				status = false;
			}
		}
		catch( Exception e )
		{
			h.setMessageError( "Unexpected error while submitting Order. Please try again.");
			h.setMessageDebug( ExceptionUtils.getMessage(e) );
			log.error( h.getMessageError() + "\n" + h.getMessageDebug() + "\n" + ExceptionUtils.getStackTrace(e) );
			status = false;
		}
		
		return status;
	}

}
