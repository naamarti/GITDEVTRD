<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="siteMap_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.siteMap.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px; ">
		<div class="tbsStylePageFilterImage" >
			<img src="images/pushpin1_48.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<h2 >Site Map</h2>
	</div>

	<div style="margin-left:9px; margin-top:15px; width:250px; float:left;" >
		<div class="tbs-ui-page-section-bar" >
			Account
		</div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</a></div>
	</div>
		
	
	<div style="margin-left:9px; margin-top:15px; width:250px; float:left;" >
		<div class="tbs-ui-page-section-bar" >
			Trading
		</div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('market.htm')">Market</a></div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('orderEntry.htm')">Trade</a></div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('transactions.htm')">Transaction History</a></div>
	</div>


	<div style="margin-left:9px; margin-top:15px; width:250px; float:left;" >
		<div class="tbs-ui-page-section-bar" >
			Documents
		</div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('siteDocuments.htm')">Disclosures</a></div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('companyNews.htm')">Company News</a></div>
		<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('financialDocuments.htm')">Financials</a></div>
	</div>
	
	
	<c:set var="isFinanceRole" value="${model.isFinanceRole}" />
	<c:choose>
		<c:when test="${(isFinanceRole)}">
			<div style="margin-left:9px; margin-top:15px; width:250px; float:left;" >
				<div class="tbs-ui-page-section-bar" >
					Finance
				</div>		
				<div style="margin-top:3px;"><a href="#" class="tbsStyleLinks" onClick="jScript.menu.navigateToURL('tradeExecution.htm')">Trade Execution</a></div>
			</div>
		</c:when>	
		
	</c:choose>
	

<%@ include file="template/layoutStandardFooterNew.jspf" %>
