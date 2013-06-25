<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="transactions_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.transactions.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Transactions</span>
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/invoice1_64.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<div style="margin-top:9px;" >
			<span class="tbs-ui-pagefilter-screentitle">Transactions</span>
		</div>
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
	
<%@ include file="template/layoutStandardFooterNew.jspf" %>
