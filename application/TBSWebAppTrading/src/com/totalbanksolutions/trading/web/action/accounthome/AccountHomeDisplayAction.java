package com.totalbanksolutions.trading.web.action.accounthome;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.view.TradingAccountSummaryView;
import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.model.database.view.TradingUserPositionView;
import com.totalbanksolutions.framework.model.database.view.TradingUserTransactionView;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.bean.WebTableColumn;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.AccountInfoModelHelper;

/*
 * Modified: AW, Mar 21, 2013, GEM# 2283
 */	

public class AccountHomeDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( AccountHomeDisplayAction.class );
	private DataService ds;

	public AccountHomeDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		AccountInfoModelHelper mh = new AccountInfoModelHelper(modelAndView);
		
		mh.setViewName("accountHome");
		mh.setScreenName("Account Home");

		log.debug("### getting active user ID from model helper ... ###");
		long userId = mh.getUser().getUserId();
		log.debug("### userId " + userId + " ###");

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
		
		boolean isOrderLocked = this.ds.trading.checkIsOrderLocked();
		mh.setIsOrderLocked(isOrderLocked);
		
		mh.setPositionsTable(getPositionsWebTable( userId ));
		mh.setOpenOrdersTable(getOpenOrdersWebTable( userId, startDate, endDate));
		mh.setTransactionsTable(getTransactionsWebTable(userId));
		mh.setAccountSummaryTable(getAccountSummaryWebTable(userId));
		
		return true;
	}

	
	private WebTable getAccountSummaryWebTable(long userId)
	{
		TradingAccountSummaryView t = this.ds.trading.getTradingAccountSummary(userId);
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		w.setShowRowNumbers(false);
		w.setDisplayRowCount( w.getRowCount() );
		w.setHasTotalRow(false);
		
		c = w.addColumn( t.USER_NAME, 250, Align.LEFT );
//		c = w.addColumn( t.QUANTITY, 200, Align.LEFT );
//		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		
		return w;
	}
	
	private WebTable getPositionsWebTable(long userId)
	{
		TradingUserPositionView t = this.ds.trading.getTradingUserOpenPositions(userId);
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		log.debug("### openPositions count " + w.getRowCount() + " ###");
		
		w.setShowRowNumbers(false);
		w.setDisplayRowCount( w.getRowCount() );
		w.setHasTotalRow(false);
		
		c = w.addColumn( t.SECURITY, 250, Align.LEFT );
		c = w.addColumn( t.QUANTITY, 150, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		c = w.addColumn( t.PENDING_QUANTITY, 150, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);

		
		return w;
	}

	private WebTable getOpenOrdersWebTable(long userId, Date startDate, Date endDate )
	{
		TradingUserOrderView t = this.ds.trading.getTradingActiveUserOrders(userId, startDate, endDate);
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		log.debug("### openOrders count " + w.getRowCount() + " ###");
		w.setDisplayRowCount( ( w.getRowCount() > 3 ? 3 : w.getRowCount() ) );
		
		w.setShowRowNumbers(false);
		w.setHasTotalRow(false);
	
		c = w.addColumn( t.ORDER_ID, 100, Align.LEFT );
		c.setIsLink(true);
		
		c = w.addColumn( t.ENTRY_DATE, 150, Align.LEFT );
		c.setCellFormatType(WebTableCellFormatType.DATE);
		c.setCellDateTimeFormat("MM/dd/yyyy HH:mm:ss");
		
		c = w.addColumn( t.SECURITY, 100, Align.LEFT );
		c = w.addColumn( t.ORDER_TYPE, 100, Align.LEFT );

		c = w.addColumn( t.ORIGINAL_QUANTITY, 100, Align.LEFT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);

		c = w.addColumn( t.QUANTITY, 100, Align.LEFT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);
		
		c = w.addColumn( t.PRICE, 100, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);

		c = w.addColumn( t.STATUS, 100, Align.LEFT );

		c = w.addWideColumnActionDelete();
		
		return w;
	}
	
	private WebTable getTransactionsWebTable(long userId)
	{
		TradingUserTransactionView t = this.ds.trading.getTradingUserTransactions(userId);
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		log.debug("### Transactions count " + w.getRowCount() + " ###");
		
		w.setShowRowNumbers(false);
		w.setDisplayRowCount( w.getRowCount() );
		w.setHasTotalRow(false);

		c = w.addColumn( t.TRANSACTION_ID, 100, Align.LEFT );
//		c.setIsLink(true);
		
		c = w.addColumn( t.SECURITY, 100, Align.LEFT );
		c = w.addColumn( t.TRANSACTION_TYPE, 100, Align.LEFT );
		c = w.addColumn( t.QUANTITY, 100, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_0DIGIT_PRECISION);

		c = w.addColumn( t.PRICE, 100, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);

		c = w.addColumn( t.EFFECTIVE_DATE, 150, Align.LEFT );
		c.setCellFormatType(WebTableCellFormatType.DATE);
		
		return w;
	}

	
}
