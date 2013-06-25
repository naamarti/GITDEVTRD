jScript.login = (function() {
	
	function login ()
	{
		jQuery("#mainForm").submit();
	}

	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		jQuery("#buttonLogin").on("click", login);
		jQuery("#mainForm").submit( function (e) { submit(e); } );
		setFocus();
	}
	
	function setFocus ()
	{
		if( jQuery("#loginStep").val() == "login" )
		{
			jQuery("#userName").focus();		
		}
		else
		{
			jQuery("#challengeQuestion").focus();					
		}
	}
	
	function submit (e)
	{
		if( validate() )
		{
			jQuery("#sessionUserAction").val("login");
			jScript.common.showWaiting();
			return true;
		}
		else
		{
			e.preventDefault();
			return false;
		}
	}
	
	function validate ()
	{
		jScript.common.clearStatusMessages();
		var errorMessages = "";
		errorMessages += jScript.ui.formValidate("loginForm");
		if ( errorMessages.length > 0 )
		{
			jScript.common.setStatusMessageError(errorMessages);
			return false;
		}
		else
		{
			return true;
		}
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

