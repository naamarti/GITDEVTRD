jScript.orderPreview = (function() {
	
	function cancelOrder ()
	{
		jScript.menu.navigateToURL("accountHome.htm");
	}
	
	function editOrder ()
	{
		runAction("editPreOrder");
	}

	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		jQuery("#buttonCancel").on("click", cancelOrder);
		jQuery("#form1ButtonEdit").on("click", editOrder);
		
		var isLockedOut = jQuery('#isLockedOut').val();
		if(isLockedOut != "true") {
			jQuery("#buttonSubmit").on("click", submitOrder);
		}
		/* setFocus(); */
	}

	function runAction (action)
	{
		jScript.common.clearStatusMessages();
		jQuery("#sessionUserAction").val(action);
		jQuery("#mainForm").submit();
		jScript.common.showWaiting();
	}
	
	function submitOrder ()
	{
		runAction("submitOrder");
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

