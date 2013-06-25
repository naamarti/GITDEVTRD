jScript.common = (function() {
	
	var selectedRowIDs = new Array();
	
	function clearStatusMessages ()
	{
		jQuery("#pageStatusMessageDebug").html("");
		jQuery("#pageStatusMessageError").html("");
		jQuery("#pageStatusMessageInfo").html("");
		jQuery("#pageStatusMessageSuccess").html("");
		jQuery("#pageStatusMessageWarning").html("");

		jQuery("#pageStatusMessageDebug").hide();
		jQuery("#pageStatusMessageError").hide();
		jQuery("#pageStatusMessageInfo").hide();
		jQuery("#pageStatusMessageSuccess").hide();
		jQuery("#pageStatusMessageWarning").hide();
	}

	function disableButton (buttonId)
	{
		var button = document.getElementById(buttonId);
		if(button != null) { button.disabled = true; button.style.className = 'buttondisabled'; }
	}
	
	function formatNumber (n)
	{
		var isNegative = n < 0 ? true : false;
		var negativePrefix = isNegative == true ? "(" : "";
		var negativeSuffix = isNegative == true ? ")" : "";
		var num = Math.abs(n) + "";
		var decimalIndex = num.indexOf(".");
		decimalIndex = decimalIndex < 0 ? num.length : decimalIndex;
		var intPart = num.substring(0,decimalIndex);
		var decPart = num.substring(decimalIndex+1) + "00";
		decPart = decPart.substr(0,2);
		var intPartFormatted = "";
		while(true)
		{
			if(intPart.length > 3)
			{
				var x = intPart.slice(-3);
				intPartFormatted = "," + x + intPartFormatted;
				intPart = intPart.substring(0,intPart.length-3);
			}
			else
			{
				intPartFormatted = intPart + intPartFormatted;
				break;
			}
		}
		return negativePrefix + intPartFormatted + "." + decPart + negativeSuffix;
	}

	function getNumberFromText (txt)
	{
		var isNegative = false;
		if(txt.indexOf("-") > -1 || txt.indexOf("(") > -1) { isNegative = true };
		txt = txt.replace(/,/g,"");
		txt = txt.replace(/-/g,"");
		txt = txt.replace(/\(/g,"");
		txt = txt.replace(/\)/g,"");
		if(!jScript.common.isNumber(txt)) { txt = "0"; }
		var num = parseFloat(txt);
		num = isNegative == true ? num * -1 : num;
		return num;
	}

	function hideWaiting ()
	{
	  jQuery("#pageStatusMessageProcessing").html("");
	  jQuery("#divPageStatusPleaseWait").hide();
	}
	
	function initStatusMessages ()
	{
		if( !jScript.common.isBlank(jQuery("#sessionMessageError").val()) )
		{
			jScript.common.setStatusMessageError( jQuery("#sessionMessageError").val() );
		}
		if( !jScript.common.isBlank(jQuery("#sessionMessageInfo").val()) )
		{
			jScript.common.setStatusMessageInfo( jQuery("#sessionMessageInfo").val() );
		}
		if( !jScript.common.isBlank(jQuery("#sessionMessageSuccess").val()) )
		{
			jScript.common.setStatusMessageSuccess( jQuery("#sessionMessageSuccess").val() );
		}
		if( !jScript.common.isBlank(jQuery("#sessionMessageWarning").val()) )
		{
			jScript.common.setStatusMessageWarning( jQuery("#sessionMessageWarning").val() );
		}
	}
	
	function isBlank (s) 
	{
		var mchar = new String(s);
		for (var i=0; i<mchar.length; i++) 
		{
			if (mchar.charAt(i) != ' ') return false;
		}
		return true;
	}
	
	function isNumber (n) 
	{
		return !isNaN(parseFloat(n)) && isFinite(n);
	}

	function setStatusMessageError ( msg )
	{
		jQuery("#pageStatusMessageError").html(msg);
		jQuery("#pageStatusMessageError").show();
	}

	function setStatusMessageInfo ( msg )
	{
		jQuery("#pageStatusMessageInfo").html(msg);
		jQuery("#pageStatusMessageInfo").show();
	}

	function setStatusMessageSuccess ( msg )
	{
		jQuery("#pageStatusMessageSuccess").html(msg);
		jQuery("#pageStatusMessageSuccess").show();
	}

	function setStatusMessageWarning ( msg )
	{
		jQuery("#pageStatusMessageWarning").html(msg);
		jQuery("#pageStatusMessageWarning").show();
	}
	
	function showDeveloperNotes ()
	{
		jQuery("#divDeveloperNotes").html(jQuery("#devNotes").html());
	}
	
	function setWaitingMessage (msg)
	{
		jQuery("#pageStatusMessageProcessing").html(msg);
	}

	function showWaiting ()
	{
	  jQuery("#pageStatusMessageProcessing").html("Please Wait...");
	  jQuery("#divPageStatusPleaseWait").show();
	}

	function applyMethodDefaultOptions ( defaultOptions, options )
	{
		for(var a in defaultOptions) {
			if(typeof options[a] == "undefined") options[a] = defaultOptions[a];
		}		
	}
	
	function highlightRow (row)
	{
		var className = row.className;
		var i = className.indexOf('Selected');
		if(i > -1)
		{
			return;
		}
		i = className.indexOf('Hover');
		if(i == -1)
		{
			row.className = className + 'Hover';
		}
	}

	function unHighlightRow (row)
	{
		var className = row.className;
		var i = className.indexOf('Hover');
		if(i > -1)
		{
			row.className = className.substring(0, i);
		}
	}

	function round ( n, decimals )
	{
		var num = n * Math.pow(10,decimals);
		num = Math.round(num);
		num = num / Math.pow(10,decimals);
		return num;
	}

	function selectRow (row)
	{
		var tableId = row.parentNode.parentNode.id;
		toggleSelectedRow(row, selectedRowIDs[tableId]);
		selectedRowIDs[tableId] = row.id;
	}
	
	function syncXScroll ( div1Name, div2Name )
	{
		var div1 = document.getElementById(div1Name);
		var div2 = document.getElementById(div2Name);
		div2.scrollLeft = div1.scrollLeft;
	}

	function syncXYScroll ( divName, divXName, divYName )
	{
		var div = document.getElementById(divName);
		var divX = document.getElementById(divXName);
		var divY = document.getElementById(divYName);
		divY.scrollTop = div.scrollTop;  
		divX.scrollLeft = div.scrollLeft;  
	}
	
	function toggleSelectedRow ( row, previousRowID )
	{
		unSelectPreviousRow(previousRowID);
		var className = row.className;
		var i = className.indexOf('Selected');
		if(i == -1)
		{
			row.className = className + 'Selected';
		}
	}

	function unSelectPreviousRow (rowID)
	{
		var row = document.getElementById(rowID);
		if (row != null)
		{
			var className = row.className;
			var i = className.indexOf('Selected');
			if(i > -1)
			{
				row.className = className.substring(0, i);
			}
		}
	}
	
	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		applyMethodDefaultOptions	:	applyMethodDefaultOptions,
		clearStatusMessages			:	clearStatusMessages,
		disableButton				:	disableButton,
		formatNumber				:	formatNumber,
		getNumberFromText			:	getNumberFromText,
		hideWaiting					:	hideWaiting,
		highlightRow				:	highlightRow,
		initStatusMessages			:	initStatusMessages,
		isBlank						:	isBlank,
		isNumber					:	isNumber,
		round						:	round,
		selectRow					:	selectRow,
		setStatusMessageError		:	setStatusMessageError,
		setStatusMessageInfo		:	setStatusMessageInfo,
		setStatusMessageSuccess		:	setStatusMessageSuccess,
		setStatusMessageWarning		:	setStatusMessageWarning,
		setWaitingMessage			:	setWaitingMessage,
		showDeveloperNotes			:	showDeveloperNotes,
		showWaiting					:	showWaiting,
		syncXScroll					:	syncXScroll,
		syncXYScroll				:	syncXYScroll,
		unHighlightRow				:	unHighlightRow
	};

}());
