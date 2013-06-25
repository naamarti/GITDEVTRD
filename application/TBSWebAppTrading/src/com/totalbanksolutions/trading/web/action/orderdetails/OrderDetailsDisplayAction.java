package com.totalbanksolutions.trading.web.action.orderdetails;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.util.DateUtility;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.bean.WebFormControl;
import com.totalbanksolutions.framework.web.enums.WebControlType;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class OrderDetailsDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( OrderDetailsDisplayAction.class );
	private DataService ds;

	public OrderDetailsDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);

		h.setViewName("orderDetails");
		h.setScreenName("Order Details");

		String orderId = WebUtils.getStringParameterValue(request, "orderId");
		
		Date nextOrderTradingDate = this.ds.trading.getNextOrderTradingDate();
		DateUtility dtUtil = new DateUtility();
		h.setNextOrderTradingDate(dtUtil.convertDateToString(nextOrderTradingDate, "MMMM d, yyyy") );

		TradingUserOrderView t = this.ds.trading.getTradingUserOrder( h.getUser().getUserId(), orderId );
		
		h.setFormData1( new TradingUserOrderView() );
		
		buildForm( h.getForm1(), t, orderId );

		return true;
	}

	private void buildForm( WebForm f, TradingUserOrderView t, String orderId )
	{
		f.setModelTable(t);
		//log.debug("table"+ t);
		//f.setDisplayEditButton(true);
		WebFormControl c = null;

		c = f.addControl( t.ORDER_ID,					WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		
		c = f.addControl( t.ENTRY_DATE,					WebControlType.INPUT_TEXT );
		c.setFormatType( WebTableCellFormatType.DATE );
		c.setFormatPattern("MM/dd/yyyy HH:mm:ss");
		c.setReadOnly(true);
		
		c = f.addControl( t.SECURITY,					WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		
		c = f.addControl( t.ORDER_TYPE,					WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		
		c = f.addControl( t.ORIGINAL_QUANTITY,			WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		c.setFormatType( WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION );
		
		c = f.addControl( t.QUANTITY,					WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		c.setFormatType( WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION );
		
		c = f.addControl( t.PRICE,						WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		c.setFormatType( WebTableCellFormatType.CURRENCY );

		c = f.addControl( t.ESTIMATED_VALUE,			WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		c.setFormatType( WebTableCellFormatType.CURRENCY );

		c = f.addControl( t.STATUS,						WebControlType.INPUT_TEXT );
		c.setReadOnly(true);
		
	}
	
}
