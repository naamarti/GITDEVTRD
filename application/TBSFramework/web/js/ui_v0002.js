jScript.ui = (function() {

	function init ()
	{
		jQuery("td.tbs-ui-tablecell-zoomable")
			.on("click", function () { jScript.popUp.zoom( "Zoom", jQuery(this).html() ); } );




		jQuery(".tbsStyleWidgetButton")
			.mousedown(function () {
				jQuery(this).find("div").each(function() {
					var className = jQuery(this).attr('class');
					var i = className.indexOf('Down');
					if(i == -1)
					{
						className = className + 'Down';
						jQuery(this)
							.removeClass()
							.addClass(className);
					}
				});
			})
			.mouseup(function () {
				jQuery(this).find("div").each(function() {
					var className = jQuery(this).attr('class');
					var i = className.indexOf('Down');
					if(i > -1)
					{
						className = className.substring(0, i);
						jQuery(this)
							.removeClass()
							.addClass(className);
					}
				});
			});
	}


	function formControlSetFocus( control, isFocusSet )
	{
		if( !isFocusSet )
		{
			jQuery(control).focus();
			return true;
		}
		return false;
	}
	
	function formValidate( formId )
	{
		var errorMessages = "";
		var isFocusSet = false;
		jQuery("#divForm" + formId).children("div").each(function()
		{
			var id = jQuery(this).attr("data-controlId");
			var label = jQuery(this).attr("data-controlLabel");
			var required = jQuery(this).attr("data-controlRequired");
			var min = parseFloat(jQuery(this).attr("data-controlRangeMin"));
			var max = parseFloat(jQuery(this).attr("data-controlRangeMax"));
			var control = jQuery("#" + id);
			var value = jQuery(control).val();
			var imageError = jQuery("#controlImageError" + id);
			var imageSuccess = jQuery("#controlImageSuccess" + id);
			var isSuccess = true;
			//var message = jQuery("#controlImageSuccess" + id);
			jQuery(imageError).hide();
			jQuery(imageSuccess).hide();
			if ( required == 'true' && jScript.common.isBlank(value) )
			{
				errorMessages += "Please Enter the '" + label + "' !</br>";
				isFocusSet = formControlSetFocus( control, isFocusSet );
				//jQuery(message).html("Entry Required");
				//jQuery(imageError).show();
				isSuccess = false;
			}
			if ( (min > 0 && value < min) || (max > 0 && value > max) )
			{
				errorMessages += "'" + label + "' must be between '" + min + "' and '" + max + "' !</br>";
				isFocusSet = formControlSetFocus( control, isFocusSet );
				//jQuery(message).html("Enter '" + min + "' to '" + max + "' ");
				//jQuery(imageError).show();
				isSuccess = false;
			}
			if( isSuccess == true )
			{
				jQuery(imageSuccess).show();
			}
			else
			{
				jQuery(imageError).show();
			}
		});
		return errorMessages;
	}
	
	function tableGetColumnValue ( row, columnName )
	{
		var value = jQuery(row).find("td[data-columnName='" + columnName + "']").text();
		return jQuery.trim(value);
	}
	
	function tableRegisterCellClickEvent ( tableName, columnName, fn )
	{
		var t = jQuery("#tableDataCols" + tableName);
		jQuery(t)
			.find("td[data-columnName='" + columnName + "']")
			.on("click", function () { fn( jQuery(this).parent(), this ); } )
			.addClass("tbs-ui-tablecell-clickable");
	}
	

	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		formValidate					:	formValidate,
		init							:	init,
		tableGetColumnValue				:	tableGetColumnValue,
		tableRegisterCellClickEvent		:	tableRegisterCellClickEvent
	};
	
}());
