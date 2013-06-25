jScript.transactions = (function() {

	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();

		jQuery("#mainForm").attr("action", "transactions.htm");
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

