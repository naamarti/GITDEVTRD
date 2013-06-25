package com.totalbanksolutions.trading.web.action.orderentry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.FormValidationView;
import com.totalbanksolutions.framework.model.ModelRow;
import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class OrderEntryValidateAction implements Action
{
	protected final Log log = LogFactory.getLog( OrderEntryValidateAction.class );
	private DataService ds;

	public OrderEntryValidateAction(DataService ds)
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

			t.addRow( request.getParameterMap() );
			log.debug( t.toString() );
			
			String errorMessages = "";
			FormValidationView v = this.ds.trading.validateUserOrder( t, h.getUser().getUserId() );
			for( ModelRow r : v.getRowList() )
			{
				String columnName = r.getColumn(v.COLUMN_NAME).getValueAsString();
				String message = r.getColumn(v.MESSAGE).getValueAsString();
				h.getForm1().setStateInvalid( columnName );
				if( message.length() > 0 ) errorMessages += message + "<br>";
				status = false;
			}
			h.setMessageError( errorMessages );	
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
