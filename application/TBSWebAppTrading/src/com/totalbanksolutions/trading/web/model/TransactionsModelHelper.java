package com.totalbanksolutions.trading.web.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.model.ModelAndView;

public class TransactionsModelHelper extends ModelHelper {

	protected final Log log = LogFactory.getLog( TransactionsModelHelper.class );

	private final String TABLE_TRANSACTIONS				= "tableTransactions";
	
	public TransactionsModelHelper( ModelAndView mv )
	{
		super(mv);
		if( !mv.getModel().containsKey("TransactionsModelHelper") )
		{
			init();
			mv.getModel().put("TransactionsModelHelper", true);
		}
	}

	public void init()
	{
		this.setTransactionsTable(null);
	}
	
	// Getters
		public WebTable getTransactionsTable() {
		return (WebTable)get( this.TABLE_TRANSACTIONS );
	}
	
	
	// Setters
		public void setTransactionsTable( WebTable t ) {
		put( this.TABLE_TRANSACTIONS, t);
	}
	
}
