jScript.siteAgreement = (function() {

	function checkForScrollEnd ()
	{
		var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
	    var divTotalHeight = $(this)[0].scrollHeight 
	                          + parseInt($(this).css('padding-top'), 10) 
	                          + parseInt($(this).css('padding-bottom'), 10)
	                          + parseInt($(this).css('border-top-width'), 10)
	                          + parseInt($(this).css('border-bottom-width'), 10);

//	    alert('scrollPosition[' + scrollPosition + '], divTotalHeight[' + divTotalHeight + ']');
	    
	    if( scrollPosition >= (divTotalHeight-10) )
	    {
			jQuery("#btnContinue").removeAttr("disabled");
	    }

	}
	
	function continueClicked ()
	{
		jScript.common.clearStatusMessages();
		jQuery("#loginStep").val("saveSiteAgreement");
		runAction("continue");
	}
	
	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		jQuery("#btnContinue").on("click", continueClicked);
		jQuery("#siteAgreementDiv").on("scroll", checkForScrollEnd);

	}
	
	function runAction (action)
	{
		jScript.common.clearStatusMessages();
		jQuery("#sessionUserAction").val(action);
		jQuery("#mainForm").submit();
		jScript.common.showWaiting();
	}
	

	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		init	:	init
	};
	
}());
