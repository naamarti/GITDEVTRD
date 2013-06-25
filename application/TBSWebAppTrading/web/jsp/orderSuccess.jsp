<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="orderSuccess_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.orderSuccess.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('orderEntry.htm')">Trade</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Order Confirmation</span>
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
		<div style="width:520px; height:250px; float:left; border:1px solid black;" >
			<div class="tbs-ui-page-section-bar" >
				Order Confirmation
			</div>

			<div style="margin-left:12px; margin-top:12px;" >
				Order <b>'${model.orderNumber}'</b> successfully submitted.
				<br><br>
				You can view all your open Orders from the Account Home screen.
			</div>
		</div>

		<div style="margin-left:15px; width:405px; float:left;" >
			<div id="divNotice" name="divNotice" class="tbs-ui-message-info tbsStyleRounded" >
				Next Trade Execution date will be on ${model.nextOrderTradingDate}
			</div>
		</div>
	</div>

<%@ include file="template/layoutStandardFooterNew.jspf" %>
