jScript.accountHome = (function() {

	function deleteRow ( row, cell )
	{
		
		var isLockedOut = jQuery('#isLockedOut').val();
		
		if(isLockedOut != "true")
		{
			var orderId = jScript.ui.tableGetColumnValue( row, "orderId" );
			var security = jScript.ui.tableGetColumnValue( row, "security" );
			var action = jScript.ui.tableGetColumnValue( row, "orderType" );
			var quantity = jScript.ui.tableGetColumnValue( row, "quantity" );
			var price = jScript.ui.tableGetColumnValue( row, "price" );
			var msg  = "<div style='width:400px;'>";
			msg  = msg + "<div>Are you sure you want to delete the following Order ?</div>";
			msg  = msg + "<div style='width:300px;'>&nbsp;</div>";
			msg  = msg + "<div style='width:300px;'><div style='width:150px; float:left;'>Order ID: </div><div style='width:150px; float:left;'>" + orderId + "</div></div>";
			msg  = msg + "<div style='width:300px;'><div style='width:150px; float:left;'>Security: </div><div style='width:150px; float:left;'>" + security + "</div></div>";
			msg  = msg + "<div style='width:300px;'><div style='width:150px; float:left;'>Action:   </div><div style='width:150px; float:left;'>" + action + "</div></div>";
			msg  = msg + "<div style='width:300px;'><div style='width:150px; float:left;'>Quantity: </div><div style='width:150px; float:left;'>" + quantity + "</div></div>";
			msg  = msg + "<div style='width:300px;'><div style='width:150px; float:left;'>Price:    </div><div style='width:150px; float:left;'>" + price + "</div></div>";
			msg  = msg + "<div style='width:300px;'>&nbsp;</div>";
			msg  = msg + "<div style='width:300px;'>Press OK to continue or CANCEL to abort.</div>";
			msg  = msg + "</div>";
			jScript.common.clearStatusMessages();
			jScript.popUp.question("Order Delete Confirmation", msg, 
				function () {
					jQuery("#sessionUserAction").val("cancelOrder");
					jQuery("#orderId").val(orderId);
					jQuery("#mainForm").submit();
					jScript.common.showWaiting();
					jScript.common.setStatusMessageSuccess("Deleted Order # <B>" + orderId + " </B>");
				}
			);
		}
		else
		{
			jScript.popUp.warning('Notice', 'Orders are now Locked until after this month’s Trade Execution process has been completed.');
		}
	}
	
	function init ()
	{
	
		
		jScript.ui.init();
		jScript.common.initStatusMessages();

		jScript.ui.tableRegisterCellClickEvent( "tableOpenOrders", "orderId", navigateToOrder );
		jScript.ui.tableRegisterCellClickEvent( "tableOpenOrders", "actionDelete", deleteRow );

//		jScript.ui.tableRegisterCellClickEvent( "tableTransactions", "transactionId", navigateToTransaction );
		
		jQuery("#mainForm").attr("action", "accountHome.htm");
	}
	
	function navigateToOrder( row, cell )
	{
	   	var orderId = jScript.ui.tableGetColumnValue( row, "orderId" );
		jQuery("#orderId").val(orderId);
		//alert(orderId);
		jScript.menu.navigateToURL( 'orderDetails.htm' );
	}

	function navigateToTransaction( row, cell )
	{
	   	var tranId = jScript.ui.tableGetColumnValue( row, "transactionId" );
		jQuery("#transactionId").val(tranId);
		alert(tranId);
//		jScript.menu.navigateToURL( 'TransactionReview.htm' );
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

