package com.totalbanksolutions.trading.web.action.transactions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.model.database.view.TradingUserTransactionView;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.bean.WebTableColumn;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.TransactionsModelHelper;

public class TransactionsDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( TransactionsDisplayAction.class );
	private DataService ds;

	public TransactionsDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("processAction()...");
		TransactionsModelHelper mh = new TransactionsModelHelper(modelAndView);
		
		mh.setViewName("transactions");
		mh.setScreenName("Transactions");

		mh.setTransactionsTable(getTransactionsWebTable());
		
		return true;
	}

	
		
	private WebTable getTransactionsWebTable()
	{
		TradingUserTransactionView t = this.ds.trading.getTradingMarketTransactionHistory();
		WebTable w = new WebTable(t);
		WebTableColumn c = null;

		log.debug("### Transactions count " + w.getRowCount() + " ###");
		
		w.setShowRowNumbers(false);
		w.setDisplayRowCount( w.getRowCount() );
		w.setHasTotalRow(false);

		c = w.addColumn( t.TRANSACTION_ID, 100, Align.LEFT );
//		c.setIsLink(true);
		
		c = w.addColumn( t.SECURITY, 100, Align.LEFT );
	
		c = w.addColumn( t.QUANTITY, 100, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.DECIMAL_2DIGIT_PRECISION);

		c = w.addColumn( t.PRICE, 100, Align.RIGHT );
		c.setCellFormatType(WebTableCellFormatType.CURRENCY);

		c = w.addColumn( t.EFFECTIVE_DATE, 150, Align.LEFT );
		c.setCellFormatType(WebTableCellFormatType.DATE);
		
		return w;
	}

	
}
