jScript.challengeQuestions = (function() {
	
	function save ()
	{
		jQuery("#mainForm").submit();
	}
	
	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();
		jQuery("#buttonSave").on("click", save);
		jQuery("#mainForm").submit( function (e) { submit(e); } );
		setFocus();
	}
	
	function setFocus ()
	{
		jQuery("#challengeResponse1").focus();
	}
	
	function submit (e)
	{
		if( validate() )
		{
			jQuery("#sessionUserAction").val("save");
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
		var questionIdList = "";
		for(var i = 1; i <= 5; i++)
		{
			var questionId = jQuery("#question" + i).val();
			if(questionId == 0)
			{
				jScript.common.setStatusMessageError("Please select a Question and enter a response for each row!");
				jQuery("#challengeResponse" + i).focus();
				return false;
			}
			else
			{
				if(questionIdList.indexOf("(" + questionId + ")") > -1)
				{
					jScript.common.setStatusMessageError("You must select a different Question for each row!");
					jQuery("#challengeResponse" + i).focus();
					return false;
				}
				questionIdList += "(" + questionId + ")";
			}
			var reply = jQuery("#challengeResponse" + i).val();
			if ( jScript.common.isBlank(reply) )
			{
				jScript.common.setStatusMessageError("Please select a Question and enter a response for each row!");
				jQuery("#challengeResponse" + i).focus();
				return false;
			}
		}
		return true;
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

