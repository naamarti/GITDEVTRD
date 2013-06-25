package com.totalbanksolutions.trading.web.action.market;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.view.TradingMarketOrderView;
import com.totalbanksolutions.framework.util.DateUtility;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.bean.WebTableColumn;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.MarketModelHelper;
import com.totalbanksolutions.trading.web.model.ModelHelper;
//import com.totalbanksolutions.framework.util.DateUtility;
//import com.totalbanksolutions.framework.util.MailSender;

public class MarketDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( MarketDisplayAction.class );
	private DataService ds;

	public MarketDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		ModelHelper h = new ModelHelper(modelAndView);
		
		MarketModelHelper mh = new MarketModelHelper(modelAndView);

		h.setViewName("market");
		h.setScreenName("Market");
		long userId = h.getUser().getUserId();
		log.debug("### userId " + userId + " ###");
		Date nextOrderTradingDate = this.ds.trading.getNextOrderTradingDate();
		DateUtility dtUtil = new DateUtility();
		h.setNextOrderTradingDate(dtUtil.convertDateToString(nextOrderTradingDate, "MMMM d, yyyy") );
		
		Date startDate = null;
		Date endDate = null;
		try
		{
			String[] formats = new String[] {"dd-MMM-yyyy", "d-MMM-yyyy", "MM/dd/yyyy"};
			startDate = DateUtils.parseDate("01/1/2013", formats);
			endDate = DateUtils.parseDate("12/31/2013", formats);			
		}
		catch(Exception e)
		{
			// do nothing
			log.debug("### error parsing dates! ###");
		}
		
		mh.setMarketSummaryTable(getMarketOrdersWebTable( userId, startDate, endDate));
	    return true;
	}

	
	private WebTable getMarketOrdersWebTable(long userId, Date startDate, Date endDate )
	{
		TradingMarketOrderView t = this.ds.trading.getTradingMarketOrderSummary(userId, startDate, endDate);
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		w.setDisplayRowCount( ( w.getRowCount() > 9 ? 9 : w.getRowCount() ) );

		w.setShowRowNumbers(false);
		w.setHasTotalRow(false);
		
		
		c = w.addColumn( t.BID_ORDER_ID, 110, Align.CENTER );
		c.setIsLink(true);
		
		c = w.addColumn( t.BID_TIME, 150, Align.LEFT );
		c.setIsDashedZero(true);
	
		
		c = w.addColumn( t.BID_QTY, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		c.setIsDashedZero(true);
		
		
		c = w.addColumn( t.BID_PRICE, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);
		c.setIsDashedZero(true);
		
		
		c = w.addColumn( t.BLANK, 25, Align.CENTER );
		c = w.addColumn( t.CONVERGENCE_ROW, 0, Align.CENTER );

		
		c = w.addColumn( t.ASK_PRICE, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);
		c.setIsDashedZero(true);
		
		c = w.addColumn( t.ASK_QTY, 80, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		c.setIsDashedZero(true);
		
		c = w.addColumn( t.ASK_TIME, 150, Align.LEFT );
		c.setIsDashedZero(true);

		c = w.addColumn( t.ASK_ORDER_ID, 110, Align.CENTER );
		c.setIsLink(true);
		
		return w;
	}
}
