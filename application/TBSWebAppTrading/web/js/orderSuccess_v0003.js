jScript.orderSuccess = (function() {
	
	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
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

