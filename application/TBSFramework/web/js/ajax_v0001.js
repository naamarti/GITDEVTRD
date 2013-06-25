jScript.ajax = (function() {
	
	var isDebugMode = false;

	function init ()
	{
		// This global event is broadcast if an Ajax request is started and no other Ajax requests are currently running.
		jQuery("#pageStatusMessageProcessing").ajaxStart(function(){
			jScript.common.showWaiting();
		});
	
		// This global event is broadcast whenever an Ajax request completes.
		jQuery("#pageStatusMessageProcessing").ajaxComplete(function(e, xhr, settings){
			if( isDebugMode ) {
				debugPopUp(xhr.responseText, settings.url);
			}
		});
	}
	
	function setDebugMode (mode)
	{
		isDebugMode = mode;
	}
	
	function debugPopUp (xml, url)
	{
		xml = xml.replace(/</g, "&lt;");
		xml = xml.replace(/>/g, "&gt;");
		jScript.popUp.show({
			'type'		:	'info',
			'title'		:	'Debug XML',
			'content'	:	"<h3>" + url + "</h3><br/><pre>" + xml + "</pre>",
			'width'		:	800,
			'height'	:	450
		});
	}
	
	function onError (jqXHR, textStatus, errorThrown)
	{
		var error = "Unexpected ajax error.";
		if (jqXHR.status === 0) {
			error = "Unable to connect. Verify Network.";
		} else if (jqXHR.status == 404) {
			error = "Requested page not found. [404]";
		} else if (jqXHR.status == 500) {
			error = "Internal Server Error [500].";
		} else if (textStatus.length > 0) {
			error = textStatus;
		}
		jScript.common.setStatusMessageError("Error in ajax call.<br/>" + error);
		jScript.common.hideWaiting();
	}
				
	function onGetSuccess (xml, callback)
	{
		var status = jQuery(xml).find("ajaxStatus").text();
		var error = jQuery(xml).find("sessionMessageError").text();
		if( error.length > 0 )
		{
			jScript.common.setStatusMessageError( "Error in ajax call.<br/>" + error );
		}
		else if( callback != null )
		{
			callback(xml);
		}
		jScript.common.hideWaiting();
	}

	function onPostSuccess (xml)
	{
		alert(xml);
		var status = jQuery(xml).find("ajaxStatus").text();
		var success = jQuery(xml).find("sessionMessageSuccess").text();
		var error = jQuery(xml).find("sessionMessageError").text();
		var details = jQuery(xml).find("sessionMessageErrorDetails").text();
		if( success.length > 0 )
		{
			jScript.common.setStatusMessageSuccess(success);
		}
		if( error.length > 0 || details.length > 0 )
		{
			jScript.common.setStatusMessageError("Error in ajax call.<br/>" + error + "<br/>" + details);
		}
		
		jScript.common.hideWaiting();
	}
	
	function getXML2 (options)
	{
		var defaultOptions = {
			'url'		:	window.location.pathname,
			'action'	:	'',
			'params'	:	'',
			'callback'	:	null,
		}
		jScript.common.applyMethodDefaultOptions(defaultOptions, options);

		var p = {
			ajaxQueryType		: 'get',
			sessionUserAction	: options['action']
		};

		jQuery.ajax({
			type		: "GET",
			data		: jQuery.param(options['params']) + "&" + jQuery.param(p),
			url			: options['url'],
			dataType	: "xml",
			error		: onError,
			success		: function (xml) {
							onGetSuccess(xml, options['callback']);
						  }
		});
	}

	function getXML (url, action, params, callback)
	{
		var p = {
			ajaxQueryType		: 'get',
			sessionUserAction	: action
		};

		jQuery.ajax({
			type		: "GET",
			data		: jQuery.param(params) + "&" + jQuery.param(p),
			url			: url,
			dataType	: "xml",
			error		: onError,
			success		: function (xml) { onGetSuccess(xml, callback); }
		  });
	}

	function postForm (url, action)
	{
		var p = {
			ajaxQueryType		: 'post'
		};

		var form = jQuery("#mainForm").clone();
		jQuery(form).find("#sessionUserAction").val(action);
		var params = jQuery(form).serialize() + "&" + jQuery.param(p);
		
		jQuery.ajax({
			type		: "POST",
			data		: params,
			url			: url,
			dataType	: "xml",
			error		: onError,
			success		: function (xml) { onPostSuccess(xml); }
		  });
	}

	function bindFields (xml)
	{
		var row = jQuery(xml).find("row");
		jQuery("#mainForm").find(".tbs-ajax-bind").each(function ()
		{
			var id = jQuery(this).attr("id");
			var value = jQuery(row).find(id).text();
			if(jQuery(this).is(":input"))
			{
				if(jQuery(this).is(":checkbox"))
				{
					jQuery(this).prop("checked", value);
				}
				else if(jQuery(this).is(":radio"))
				{
					if(jQuery(this).val() == value)
					{
						jQuery(this).prop("checked", true);
					}
				}
				else
				{
					jQuery(this).val(value);
				}
			}
			else
			{
				jQuery(this).html(value);
			}
		});
	}
	
	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		bindFields		:	bindFields,
		getXML			:	getXML,
		getXML2			:	getXML2,
		init			:	init,
		postForm		:	postForm,
		setDebugMode	:	setDebugMode
	};

}());
