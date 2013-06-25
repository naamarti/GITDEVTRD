<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="accountHome_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.accountHome.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/home1_64.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<div style="margin-top:9px;" >
			<span class="tbs-ui-pagefilter-screentitle">Account Home</span>
		</div>
	</div>
	

	<div style="margin-left:9px; margin-top:9px;" >	
		<%-- ********** Account Summary TABLE ********** --%>
		<c:set var="tableId" value="tableAccountSummary" />
		<c:set var="tableMarginLeft" value="3" />
		<c:set var="tableMarginTop" value="3" />	
		<%@ include file="template/table2.jspf" %>
	</div>



	<div style="margin-left:9px; margin-top:15px; width:945px;" >
		<div class="tbs-ui-page-section-bar" >
			Open Positions
		</div>

		<%-- ********** Positions TABLE ********** --%>
		<c:set var="tableId" value="tablePositions" />
		<c:set var="tableMarginLeft" value="3" />
		<c:set var="tableMarginTop" value="3" />	
		<%@ include file="template/table.jspf" %>
	</div>


	<div style="margin-left:9px; margin-top:15px; width:945px;" >
		<div class="tbs-ui-page-section-bar" >
			Open Orders
		</div>

		<%-- ********** Open Orders TABLE ********** --%>
		<c:set var="tableId" value="tableOpenOrders" />
		<c:set var="tableMarginLeft" value="3" />
		<c:set var="tableMarginTop" value="3" />	
		<%@ include file="template/table.jspf" %>
	</div>


	<div style="margin-left:9px; margin-top:15px; width:945px;" >
		<div class="tbs-ui-page-section-bar" >
			Transaction History
		</div>
					
		<%-- ********** Transactions TABLE ********** --%>
		<c:set var="tableId" value="tableTransactions" />
		<c:set var="tableMarginLeft" value="3" />
		<c:set var="tableMarginTop" value="3" />	
		<%@ include file="template/table.jspf" %>
	</div>
	
	<input type="hidden" id="orderId" name="orderId" value="${model.orderId}">
	<input type="hidden" id="transactionId" name="transactionId" value="${model.transactionId}">
	<input type="hidden" id="isLockedOut" name="isLockedOut" value="${model.isOrderLocked}">

	 
<%@ include file="template/layoutStandardFooterNew.jspf" %>
