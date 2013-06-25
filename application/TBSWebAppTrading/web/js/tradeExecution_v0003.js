jScript.tradeExecution = (function() {
	
	function executeTrade ()
	{
		jScript.common.clearStatusMessages();
		jQuery("#sessionUserAction").val("ExecuteTradeMatch");
		jQuery("#mainForm").submit();
	}

	function editRow ( row, cell )
	{
		jScript.common.clearStatusMessages();
		var transactionId = jScript.ui.tableGetColumnValue( row, "transactionId" );
		var transactionStatus = jScript.ui.tableGetColumnValue( row, "transactionStatus" );
		setStatusDropDown(transactionStatus);
		jQuery("#transactionId").val(transactionId);
		jScript.popUp.show({
			'type'				:	'question',
			'title'				:	'ChangeStatus',
			'width'				:	300,
			'height'			:	200,
			'elementIdToMove' 	:	'divMessageTypes',
			'fn'				:	function () 
									{
										var statusId=$("#dropDownListStatus option:selected").val();
										//alert(transactionId);
										jQuery("#statusId").val(statusId);
										jQuery("#sessionUserAction").val("UpdateStatus");
										jQuery("#mainForm").submit();
									}
		});
	}
	
	function setStatusDropDown(theText)
	{
		jQuery("#dropDownListStatus option").each(function() {
			if(jQuery(this).text() == theText) {
				jQuery(this).attr('selected', 'selected');
			}
		});
	}

	function init ()
	{
		jScript.ui.init();
		jScript.common.initStatusMessages();

		jQuery("#buttonSubmit").on("click", executeTrade);
		jScript.ui.tableRegisterCellClickEvent( "tableTradeExecution", "actionEdit", editRow );
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

