package com.totalbanksolutions.trading.web.action.orderentry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.model.database.view.TradingUserPositionView;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.bean.WebFormControl;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.bean.WebTableColumn;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebControlType;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/*
 * Modified: AW, Mar 21, 2013, GEM# 2283
 */	

public class OrderEntryDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( OrderEntryDisplayAction.class );
	private DataService ds;

	public OrderEntryDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		
		
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("orderEntry");
		h.setScreenName("Order Entry");

		TradingUserOrderView t = (TradingUserOrderView)h.getFormData1();
		
		boolean isOrderLocked = this.ds.trading.checkIsOrderLocked();
		h.setIsOrderLocked(isOrderLocked);
		
		
		buildForm( h.getForm1(), t,isOrderLocked );
		
		h.setTable1( getOpenPositionsTable( h.getUser().getUserId() ) );
		
		return true;
	}

	private void buildForm( WebForm f, TradingUserOrderView t,boolean isOrderLocked )
	{
		//log.debug(t.isOrderLocked);
		f.setModelTable(t);
		f.setDisplayEditButton(false);
		WebFormControl c = null;
		c = f.addControl( t.SECURITY_ID,				WebControlType.INPUT_SELECT );
		c.setRequired(true);
		c.addDropDownValue("1", "A");
		
		if(isOrderLocked){c.setReadOnly(true);}
//		c.addDropDownValue("2", "B");
		
		c = f.addControl( t.ORDER_TYPE_ID,				WebControlType.INPUT_SELECT );
		c.setRequired(true);
		c.addDropDownValue("1", "Buy");
		c.addDropDownValue("2", "Sell");
		if(isOrderLocked){c.setReadOnly(true);}
		
		c = f.addControl( t.QUANTITY,					WebControlType.INPUT_TEXT );
		c.setRequired(true);
		c.setFormatType( WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION );
		if(isOrderLocked){c.setReadOnly(true);}
		
		c = f.addControl( t.PRICE,						WebControlType.INPUT_TEXT );
		c.setRequired(true);
		c.setFormatType( WebTableCellFormatType.CURRENCY );
		if(isOrderLocked){c.setReadOnly(true);}
	
	
	}

	private WebTable getOpenPositionsTable( long userId )
	{
		TradingUserPositionView t = this.ds.trading.getTradingUserOpenPositions(userId);
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		w.setShowRowNumbers(false);
		w.setDisplayRowCount( ( w.getRowCount() > 15 ? 15 : w.getRowCount() ) );
		
		c = w.addColumn( t.SECURITY, 95, Align.LEFT );
		//c.setIsLink(true);
		
		c = w.addColumn( t.QUANTITY, 140, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		
		c = w.addColumn( t.PENDING_QUANTITY, 140, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		
		return w;
	}
	
}
