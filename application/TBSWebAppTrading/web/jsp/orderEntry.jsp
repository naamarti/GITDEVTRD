<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="orderEntry_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.orderEntry.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Trade</span>
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/computer2_48.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<div style="margin-top:9px;" >
			<span class="tbs-ui-pagefilter-screentitle">Trade</span>
		</div>
	</div>

	<div style="margin-left:9px; margin-top:15px;" >
		<div style="width:520px; height:300px; float:left; border:1px solid black;" >
			<div class="tbs-ui-page-section-bar" >
				Order Entry
			</div>
			
			<%-- ********** ORDER ENTRY FORM ********** --%>
			<c:set var="formId" value="form1" />
			<c:set var="formMarginLeft" value="30" />
			<c:set var="formMarginTop" value="15" />
			<%@ include file="template/form.jspf" %>
		
			<%-- ********** BUTTONS ********** --%>
			<div style="margin-top:6px; width:492px; height:38px; position:relative;" >
				<div style="width:140px; float:right;" >
					<%@ include file="template/buttonPreview.jspf" %>
				</div>
			</div>
			
			<div class="tbs-ui-message-disclaimer" style="margin-left:12px; margin-top:15px; margin-right:12px;" >
				Upon submission you will be asked to preview the order with calculated totals before confirming. 
				This order goes into the Market as an anonymous bid/ask, and will be acted upon at the next Month End cycle.
				<br>Please be sure to read the <a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('siteDocuments.htm')">TBS Units Trading Execution Rules</a> before placing any orders in the Market.
				<br/>All orders must be entered by 5:00PM EST on the Trade Execution date, to be eligible for that month's trade matching process.
			</div>
		</div>

		<div style="margin-left:15px; width:410px; float:left;" >
			<div style="width:405px;" >
				<div id="divNotice" name="divNotice" class="tbs-ui-message-info tbsStyleRounded" >
					Next Trade Execution date will be on ${model.nextOrderTradingDate}
				</div>
			</div>
			
			<c:if test="${model.isOrderLocked}">
			<div style="width:405px;" >
				<div id="divWarning" name="divWarning" class="tbs-ui-message-warning tbsStyleRounded" >
					Orders are now Locked until after this month's Trade Execution process has been completed.
				</div>
			</div>
			</c:if>
			
			<div style="margin-top:80px; width:405px; height:200px;" >
				<div class="tbs-ui-page-section-bar" >
					Open Positions
				</div>
			
				<%-- ********** POSITIONS TABLE ********** --%>
				<c:set var="tableId" value="table1" />
				<c:set var="tableMarginLeft" value="3" />
				<c:set var="tableMarginTop" value="3" />	
				<%@ include file="template/table.jspf" %>
			</div>
		</div>
	</div>

	<input type="hidden" id="isLockedOut" name="isLockedOut" value="${model.isOrderLocked}">

<%@ include file="template/layoutStandardFooterNew.jspf" %>
