jScript.tabs = (function() {

	function expandTab (num)
	{
		selectTab(num);
		selectTabContent(num);
	}

	function selectTab (num)
	{
		var row;
		var rows = document.getElementsByTagName("tr"); 
		for (var i = 0; i < rows.length; i++) 
		{
			var className = rows[i].className;
			if(className == "tab")
			{
				row = rows[i];
			}
		}
		var cells = row.getElementsByTagName("td"); 
		var foundNum = 0;
		for (var i = 0; i < cells.length; i++) 
		{
			var className = cells[i].className;
			if(className == "tab" || className == "tabSelected" || className == "tabUnselected")
			{
				foundNum++;
				if(foundNum == num)
				{
					cells[i].className = "tabSelected";
				}
				else
				{
					cells[i].className = "tabUnselected";
				}      
			} 
		}
	}

	function selectTabContent (num)
	{
		var divs = document.getElementsByTagName("div");
		for (var i = 0; i < divs.length; i++)
		{
			var className = divs[i].className;
			if(className == "tab")
			{
				divs[i].style.display = "none";
			}
		}
		document.getElementById("tab" + num).style.display="block";
	}

	/* *********************************************************
	 * 
	 * Return Public Interface:
	 *   
	 * *********************************************************/
	return {
		expandTab		:	expandTab
	};

}());

