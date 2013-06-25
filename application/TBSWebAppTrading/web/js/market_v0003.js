jScript.market = (function() {
	
	function navigateToAskOrderConfirmation( row, cell )
	{
	   	var orderId = jScript.ui.tableGetColumnValue( row, "MarketAskOrderId" );
		jQuery("#orderId").val(orderId);
		if( orderId != '' )
		{
			jScript.menu.navigateToURL( 'orderDetails.htm' );
		}
	}
	
	function navigateToBidOrderConfirmation( row, cell )
	{
	   	var orderId = jScript.ui.tableGetColumnValue( row, "MarketBidOrderId" );
		jQuery("#orderId").val(orderId);
		if( orderId != '' )
		{
			jScript.menu.navigateToURL( 'orderDetails.htm' );
		}
	}
	
	
	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		
		var totalRows = $("#tableDataColstableMarketSummary > tbody > tr").length;
		
		//TODO: this piece should work, but is not returning a valid piece of info...
		//var centerRow = jQuery("#tableDataColstableMarketSummary").filter(function() {
		//               return $(this).find("td[data-columnName='" + "HighlightRow" + "']").text() == "true"; }).closest("tr");

		for (var row = 1;row<(totalRows+1);row++)
		{
			var isCenterRow = jScript.ui.tableGetColumnValue( "#tableDataRowtableMarketSummary" + row, "HighlightRow" );
			if(isCenterRow=="true")
			{
				var scrollToRowID = (row-4);
				if( scrollToRowID > 0 )
				{
					jQuery('#divTableDataColstableMarketSummary').scrollTop( jQuery("#tableDataRowtableMarketSummary" + scrollToRowID).position().top );
				}			
			}			
		}
		
		jScript.ui.tableRegisterCellClickEvent( "tableMarketSummary", "MarketAskOrderId", navigateToAskOrderConfirmation );		
		jScript.ui.tableRegisterCellClickEvent( "tableMarketSummary", "MarketBidOrderId", navigateToBidOrderConfirmation );		
		
	}
	
	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		init		:	init
	};

}());

