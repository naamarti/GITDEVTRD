package com.totalbanksolutions.trading.web.model;

import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.model.ModelAndView;

public class MarketModelHelper extends ModelHelper {

	private final String TABLE_MARKET_SUMMARY				= "tableMarketSummary";
	
	public MarketModelHelper( ModelAndView mv )
	{
		super(mv);
		if( !mv.getModel().containsKey("MarketModelHelper") )
		{
			init();
			mv.getModel().put("MarketModelHelper", true);
		}
	}

	public void init()
	{
		this.setMarketSummaryTable(null);
	}
	
	// Getters
	public WebTable getMarketSummaryTable() {
		return (WebTable)this.get( this.TABLE_MARKET_SUMMARY );
	}

	
	// Setters
	public void setMarketSummaryTable( WebTable t ) {
		this.put( this.TABLE_MARKET_SUMMARY, t);
	}

}
