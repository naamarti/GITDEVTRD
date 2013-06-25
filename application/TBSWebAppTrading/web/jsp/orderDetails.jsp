<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="orderDetails_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.orderDetails.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('orderEntry.htm')">Trade</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Order Details</span>
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
		<div style="width:520px; height:320px; float:left; border:1px solid black;" >
			<div class="tbs-ui-page-section-bar" >
				Order Details
			</div>
			
			<%-- ********** ORDER DETAILS FORM ********** --%>
			<c:set var="formId" value="form1" />
			<c:set var="formMarginLeft" value="30" />
			<c:set var="formMarginTop" value="15" />
			<%@ include file="template/form.jspf" %>
		
			<div class="tbs-ui-message-disclaimer" style="margin-left:12px; margin-top:15px; margin-right:12px;" >
				This order is in the Market as an anonymous bid/ask, and will be acted upon at the next Month End cycle.
				<br>Please be sure to read the <a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('siteDocuments.htm')">TBS Units Trading System Rules</a> before placing any orders in the Market.
			</div>
		</div>

		<div style="margin-left:15px; width:405px; float:left;" >
			<div id="divNotice" name="divNotice" class="tbs-ui-message-info tbsStyleRounded" >
				Next Trade Execution date will be on March 28, 2013
			</div>
		</div>
	</div>

	<input type="hidden" id="orderId" name="orderId" value="${model.orderId}">

<%@ include file="template/layoutStandardFooterNew.jspf" %>
