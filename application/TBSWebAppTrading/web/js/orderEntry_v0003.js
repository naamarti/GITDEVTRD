jScript.orderEntry = (function() {
	
	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		
		var isLockedOut = jQuery('#isLockedOut').val();
		if(isLockedOut != "true") {
			jQuery("#buttonPreview").on("click", previewOrder);
		}
		setFocus();
	}

	function previewOrder ()
	{
		var errorMessages = "";
		errorMessages += jScript.ui.formValidate('form1');		
		if ( errorMessages.length > 0 )
		{
			jScript.common.setStatusMessageError(errorMessages);			
		}
		else
		{
			runAction("previewOrder");
		}
	}
	
	function runAction (action)
	{
		jScript.common.clearStatusMessages();
		jQuery("#sessionUserAction").val(action);
		jQuery("#mainForm").submit();
		jScript.common.showWaiting();
	}
	
	function setFocus ()
	{
		jQuery("#security").focus();		
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

