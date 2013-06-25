jScript.menu = (function() {
	var redirectTimerId = 0;

	function highlightMenu (menuCell)
	{
		var className = menuCell.className;
		var i = className.indexOf('Hover');
		if(i == -1)
		{
			menuCell.className = className + 'Hover';
		}
	}
	
	function unHighlightMenu (menuCell) 
	{
		var className = menuCell.className;
		var i = className.indexOf('Hover');
		if(i > -1)
		{
			menuCell.className = className.substring(0, i);
		}
	}
	
	function navigateToURL (url)
	{
		openURL(url, 0);
	}
	
	function openURL (url, menuId)
	{
		document.mainForm.action = url;
	    if(menuId != 0)
	    {
	      document.mainForm.sessionNavigationId.value = menuId;
	    }
		document.mainForm.submit();
		if(document.mainForm.target == "")
		{
			jScript.common.showWaiting();
		}
		else
		{
			document.mainForm.target = "";
		}
	}

	function expandGroup (groupId)
	{
		var divPrefix = "divMenuPrimaryGroup";
		var divs = document.getElementsByTagName("div");
		for( i = 0; i < divs.length; i++ )
		{
			var div = divs[i];
			if( div.id.indexOf(divPrefix) > -1 )
			{
				if( div.id.substr(divPrefix.length) == groupId && div.style.display == 'none' )
				{
					div.style.display = 'block';
				}
				else
				{
					div.style.display = 'none';
				}
			}
		}
	}

	function navigateToMenu (menuName)
	{
		var element = document.getElementById("menuPrimary_" + menuName);
		if (element != null)
		{
			element.onclick();
		}
	}
	
	function openMenuInNewWindow (menuName)
	{
		var element = document.getElementById( "menuPrimary_" + menuName );
		if (element != null)
		{
			document.mainForm.sessionUserAction.value = "newWindow";
			document.mainForm.target = "_blank";
			element.onclick();
		}
	}
	
	function redirectToMenu (menuName)
	{
		var timeInSeconds = 3;
		jScript.common.setStatusMessageInfo( "Redirecting to screen '" + menuName + "' in " + timeInSeconds + " seconds..." );
		redirectTimerId = setTimeout("jScript.menu.navigateToMenu('" + menuName + "');", timeInSeconds * 1000);
	}
	
	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		expandGroup			:	expandGroup,
		highlightMenu		:	highlightMenu,
		navigateToMenu		:	navigateToMenu,
		navigateToURL		:	navigateToURL,
		openMenuInNewWindow	:	openMenuInNewWindow,
		openURL				:	openURL,
		redirectToMenu		:	redirectToMenu,
		unHighlightMenu		:	unHighlightMenu
	};

}());
