jScript.popUp = (function() {

	var isModalPopUpOpen = false;
	
	function getWindowHeight()
	{
		var height = 0;
		if (typeof window.innerHeight != 'undefined')
		{
			viewportheight = window.innerHeight;
		}
		else
		{
			viewportheight = document.documentElement.clientHeight;
		}
		if ((viewportheight > document.body.parentNode.scrollHeight) && (viewportheight > document.body.parentNode.clientHeight))
		{
			height = viewportheight;
		}
		else
		{
			if (document.body.parentNode.clientHeight > document.body.parentNode.scrollHeight)
			{
				height = document.body.parentNode.clientHeight;
			}
			else
			{
				height = document.body.parentNode.scrollHeight;
			}
		}
		if ( height > 800 ) height = 800;
		return height;
	}

	function getWindowWidth()
	{
		var width = 0;
		if (typeof window.innerWidth != 'undefined')
		{
			viewportwidth = window.innerWidth;
		}
		else
		{
			viewportwidth = document.documentElement.clientWidth;
		}
		if ((viewportwidth > document.body.parentNode.scrollWidth) && (viewportwidth > document.body.parentNode.clientWidth))
		{
			width = viewportwidth;
		}
		else
		{
			if (document.body.parentNode.clientWidth > document.body.parentNode.scrollWidth)
			{
				width = document.body.parentNode.clientWidth;
			}
			else

			{
				width = document.body.parentNode.scrollWidth;
			}
		}
		if ( width > 1200 ) width = 1200;
		return width;
	}
	
	function centerPopUpInWindow (elementId)
	{
		var popUp = document.getElementById(elementId);
		var windowHeight = getWindowHeight();
		var windowWidth = getWindowWidth();
		var popUpHeight = parseInt(popUp.style.height);
		var popUpWidth = parseInt(popUp.style.width);
		var top = (windowHeight / 2) - (popUpHeight / 2);
		var left = (windowWidth / 2) - (popUpWidth / 2);
		popUp.style.top = top + 'px';
		popUp.style.left = left + 'px';
	}

	function blanketHide ()
	{
		jQuery("#divPopUpMessageBlanket").hide();
	}
	
	function blanketShow ()
	{
		//var windowHeight = getWindowHeight();
		//jQuery("#divPopUpBlanket").height(windowHeight);
		jQuery("#divPopUpMessageBlanket").show();
	}
	
	function popUpHide ()
	{
		jQuery("#divPopUpMessage").hide();
		blanketHide();
		isModalPopUpOpen = false;
	}

	function popUpShow (options)
	{
		var defaultOptions = {
			type			:	'warning',
			title			:	'Attention',
			content			:	'',
			elementIdToMove		:	'',
			fn			:	null,
			width			:	500,
			height			:	255
		}
		jScript.common.applyMethodDefaultOptions(defaultOptions, options);

		var type			= options['type'];
		var title			= options['title'];
		var content 			= options['content'];
		var elementIdToMove 		= options['elementIdToMove'];
		var fn				= options['fn'];
		var width			= options['width'];
		var height			= options['height'];
		
		// resets
		jQuery("#popUpMessageImageInfo").hide();
		jQuery("#popUpMessageImageQuestion").hide();
		jQuery("#popUpMessageImageWait").hide();
		jQuery("#popUpMessageImageWarning").hide();
		jQuery("#popUpMessageImageZoom").hide();
		jQuery("#popUpMessageButtonCancel").hide();
		jQuery("#popUpMessageButtonClose").hide();
		jQuery("#popUpMessageButtonOK").hide();
		jQuery("#popUpMessageButtonCancel").off("click");
		jQuery("#popUpMessageButtonClose").off("click");
		jQuery("#popUpMessageButtonOK").off("click");
		jQuery("#popUpMessageWindowClose").off("click");

		if(type == 'info')
		{
			jQuery("#popUpMessageImageInfo").show();
			jQuery("#popUpMessageButtonClose").show();
			//jQuery("#popUpMessageContent").attr("class", "tbs-ui-message-info");
		}
		else if(type == 'question')
		{
			jQuery("#popUpMessageImageQuestion").show();
			jQuery("#popUpMessageButtonCancel").show();
			jQuery("#popUpMessageButtonOK").show();
			jQuery("#popUpMessageButtonOK").on("click", fn);
		}
		else if(type == 'warning')
		{
			jQuery("#popUpMessageImageWarning").show();
			jQuery("#popUpMessageButtonClose").show();
			//jQuery("#popUpMessageContent").attr("class", "tbs-ui-message-warning");
		}
		else if(type == 'zoom')
		{
			jQuery("#popUpMessageImageZoom").show();
			jQuery("#popUpMessageButtonClose").show();
		}

		// set message
		jQuery("#popUpMessageTitle").html(title);
		jQuery("#popUpMessageContent").html(content);

		// attach close events
		jQuery("#popUpMessageButtonCancel").on("click", popUpHide);
		jQuery("#popUpMessageButtonClose").on("click", popUpHide);
		jQuery("#popUpMessageButtonOK").on("click", popUpHide);
		jQuery("#popUpMessageWindowClose").on("click", popUpHide);
		
		//Move element if passed
		if(elementIdToMove.length > 0)
		{
			jQuery("#popUpMessageContent").append( jQuery("#" + elementIdToMove) );

			//reset element on window close
			jQuery("#popUpMessageButtonCancel").on("click", {elementId : elementIdToMove}, resetElementIdToMove );
			jQuery("#popUpMessageButtonClose").on("click", {elementId : elementIdToMove}, resetElementIdToMove );
			jQuery("#popUpMessageButtonOK").on("click", {elementId : elementIdToMove}, resetElementIdToMove );
			jQuery("#popUpMessageWindowClose").on("click", {elementId : elementIdToMove}, resetElementIdToMove );
			
			jQuery("#" + elementIdToMove).show();
		}

		// window dimensions
		if(width < 150) width = 150;
		if(height < 100) height = 100;
		var contentHeight = height - 75; // fixed with for headers & footer
		jQuery("#divPopUpMessage").width(width);
		jQuery("#divPopUpMessage").height(height);
		jQuery("#popUpMessageContent").height(contentHeight);
		
		// center and open window
		centerPopUpInWindow("divPopUpMessage");
		if(type != 'zoom')
		{
			isModalPopUpOpen = true;
			blanketShow();
		}
		jQuery("#divPopUpMessage").show();
	}
	
	function resetElementIdToMove(event)
	{
		elementIdToMove = event.data.elementId;
		jQuery("#mainForm").append( jQuery("#" + elementIdToMove) );
		jQuery("#" + elementIdToMove).hide();
	}

	function formBlanketHide ()
	{
		jQuery("#divPopUpFormBlanket").hide();
	}
	
	function formBlanketShow ()
	{
		jQuery("#divPopUpFormBlanket").show();
	}
	
	function formHide ()
	{
		jQuery("#divPopUpForm").hide();
		formBlanketHide();
		isModalPopUpOpen = false;
	}
	
	function formLoading (title)
	{
		// attach close events
		jQuery("#popUpFormWindowClose").on("click", formHide);
		
		// set message
		jQuery("#popUpFormTitle").html(title);

		// move page status inside the pop-up
		jQuery("#popUpFormFooter").prepend( jQuery("#pageStatusMessage") );
		
		// open window
		isModalPopUpOpen = true;
		formBlanketShow();
		jQuery("#divPopUpForm").show();
	}
	
	function formShow (divId, fnClose)
	{
		// resets
		jQuery("#popUpFormWindowClose").off("click");

		// attach close events
		jQuery("#popUpFormWindowClose").on("click", fnClose);

		// move page status inside the pop-up
		jQuery("#popUpFormFooter").prepend( jQuery("#pageStatusMessage") );

		// move form div inside the pop-up
		jQuery("#popUpFormContent").html("");
		jQuery("#popUpFormContent").append( jQuery("#" + divId) );
		
		jQuery("#popUpFormWaiting").hide();
		jQuery("#" + divId).show();
	}
	
	function info (title, content)
	{
		show({
			'type'		:	'info',
			'title'		:	title,
			'content'	:	content
		});
	}

	function question (title, content, fn)
	{
		show({
			'type'		:	'question',
			'title'		:	title,
			'content'	:	content,
			'fn'		:	fn
		});
	}

	function show (options)
	{
		popUpShow(options);
	}

	function warning (title, content)
	{
		show({
			'type'		:	'warning',
			'title'		:	title,
			'content'	:	content
		});
	}
	
	
	function zoom (title, content)
	{
		show({
			'type'		:	'zoom',
			'title'		:	title,
			'content'	:	content
		});
	}

	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		info				:	info,
		question			:	question,
		show				:	show,
		warning				:	warning,
		zoom				:	zoom,
		formLoading			:	formLoading,
		formShow			:	formShow,

		isModalOpen			: function() 
		{ 
			return isModalPopUpOpen; 
		}
		
	};
	
}());
