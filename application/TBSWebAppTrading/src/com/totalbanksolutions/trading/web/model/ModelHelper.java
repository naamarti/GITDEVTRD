package com.totalbanksolutions.trading.web.model;

import com.totalbanksolutions.framework.web.model.AbstractModelHelper;
import com.totalbanksolutions.framework.web.model.ModelAndView;

public class ModelHelper extends AbstractModelHelper {

	private final String IS_FINANCE_ROLE				= "isFinanceRole";
	private final String IS_ORDER_LOCKED				= "isOrderLocked";	
	private final String NEXT_ORDER_TRADING_DATE		= "nextOrderTradingDate";
	private final String ORDER_NUMBER					= "orderNumber";
	
	public ModelHelper( ModelAndView mv )
	{
		super(mv);
		if( !mv.getModel().containsKey("ModelHelper") )
		{
			init();
			mv.getModel().put("ModelHelper", true);
		}
	}

	public void init()
	{
		this.setIsFinanceRole(false);
		this.setIsOrderLocked(false);
		this.setNextOrderTradingDate("");
		this.setOrderNumber("");
	}
	
	// Getters	
	public boolean getIsFinanceRole() {
		return (boolean)this.get( this.IS_FINANCE_ROLE );
	}
	
	public boolean getIsOrderLocked() {
		return (boolean)this.get( this.IS_ORDER_LOCKED );
	}
	
	public String getNextOrderTradingDate() {
		return (String)this.get( this.NEXT_ORDER_TRADING_DATE );
	}

	public String getOrderNumber() {
		return (String)this.get( this.ORDER_NUMBER );
	}
	
	// Setters
	public void setIsFinanceRole( boolean isFinanceRole ) {
		this.put( this.IS_FINANCE_ROLE, isFinanceRole );
	}
	
	public void setIsOrderLocked( boolean isOrderLocked ) {
		this.put( this.IS_ORDER_LOCKED, isOrderLocked );
	}
	
	public void setNextOrderTradingDate( String nextOrderTradingDate ) {
		this.put( this.NEXT_ORDER_TRADING_DATE, nextOrderTradingDate );
	}

	public void setOrderNumber( String s ) {
		this.put( this.ORDER_NUMBER, s);
	}

}
