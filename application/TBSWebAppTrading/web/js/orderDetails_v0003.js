jScript.orderDetails = (function() {
	
	//function editOrder ()
	//{
	//	jScript.common.clearStatusMessages();
	//	jQuery("#sessionUserAction").val('editOrder');
	//	jScript.menu.navigateToURL( 'orderEntry.htm' );
	//}

	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		//jQuery("#form1ButtonEdit").on("click", editOrder);
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

