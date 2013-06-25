jScript.siteDocuments = (function() {

	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
	}
	
	function popUpPDF (docName)
	{
		var url = "";
		var ranNum= Math.floor(Math.random()*99999999); 
		url += "?PDF=" + ranNum; 

		jQuery("#sessionUserAction").val(docName);
		jQuery("#mainForm").attr("target", "pdf");
		jQuery("#mainForm").submit();

		jQuery("#mainForm").attr("target", "");
		jQuery("#sessionUserAction").val("");
	}
	
	
	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		init		:	init,
		popUpPDF	:	popUpPDF
	};
	
}());
