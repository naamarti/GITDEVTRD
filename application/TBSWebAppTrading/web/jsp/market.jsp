<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="market_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.market.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Market</span>
		
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/market1_48.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<div style="margin-top:9px;" >
			<span class="tbs-ui-pagefilter-screentitle">Market</span>
		</div>
	</div>

	<div style="margin-left:9px; margin-top:15px;" >
		<div style="width:520px; float:left;" >
			<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
				<tr>
					<td>
						<img src="images/invoice1_24.png" height="24px;" width="24px;" style="border-style:none;" alt=""/>
					</td>
					<td>
						<span class="tbs-ui-text-link" onClick="jScript.menu.navigateToURL('transactions.htm')">Market Transaction History for the Previous Month </span>
					</td>
				</tr>
			</div>
		</div>
		<div style="margin-left:15px; width:405px; float:left;" >
			<div id="divNotice" name="divNotice" class="tbs-ui-message-info tbsStyleRounded" >
				Next Trade Execution date will be on ${model.nextOrderTradingDate}
			</div>
		</div>
	</div>
	
	<div style="margin-left:9px; width:945px; height:26px;" >
		<div style="margin-left:36px; margin-top:12px; width:417px; height:26px; border:1px solid black; background-color:#3F3F3F; float:left;" >
			<table cellspacing="0" cellpadding="0" border="0" width="417px" >
				<tr>
					<td class="tbsStyleTableHeaderBar" align="CENTER"  >
						BID
					</td>
				</tr>
			</table>
		</div>
		<div style="margin-left:25px; margin-top:12px; width:418px; height:26px; border:1px solid black; background-color:#3F3F3F; float:left;" >
			<table cellspacing="0" cellpadding="0" border="0" width="418px" >
				<tr>
					<td class="tbsStyleTableHeaderBar" align="CENTER"  >
						ASK
					</td>
				</tr>
			</table>
		</div>
	</div>	
	
	<%-- ********** Market Summary TABLE ********** --%>
	<c:set var="tableId" value="tableMarketSummary" />
	<c:set var="tableMarginLeft" value="20" />
	<c:set var="tableMarginTop" value="6" />	
	<%@ include file="template/table.jspf" %>
	
	<input type="hidden" id="orderId" name="orderId" value="${model.orderId}">

<%@ include file="template/layoutStandardFooterNew.jspf" %>
