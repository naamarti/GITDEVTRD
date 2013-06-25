<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="tradeExecution_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.tradeExecution.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Trade Execution</span>
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/market1_48.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<div style="margin-top:9px;" >
			<span class="tbs-ui-pagefilter-screentitle">Trade Execution</span>
		</div>
	</div>
	
	<div style="margin-left:9px; margin-top:15px; width:945px;" >
		<div style="margin-left:535px; width:405px;" >
			<div id="divNotice" name="divNotice" class="tbs-ui-message-info tbsStyleRounded" >
				Next Trade Execution date will be on ${model.nextOrderTradingDate}
			</div>
		</div>
	</div>

	<div style="margin-left:9px; margin-top:15px; width:945px;" >
		<div class="tbs-ui-page-section-bar" >
			Execution Status
		</div>

		<%-- ********** Execution Status TABLE ********** --%>
		<c:set var="tableId" value="tableExecutionStatus" />
		<c:set var="tableMarginLeft" value="3" />
		<c:set var="tableMarginTop" value="3" />	
		<%@ include file="template/table.jspf" %>
	</div>


	<div style="margin-left:9px; margin-top:15px; width:945px;" >
		<div class="tbs-ui-page-section-bar" >
			Trades
		</div>

		<%-- ********** Trades TABLE ********** --%>
		<c:set var="tableId" value="tableTradeExecution" />
		<c:set var="tableMarginLeft" value="3" />
		<c:set var="tableMarginTop" value="3" />	
		<%@ include file="template/table.jspf" %>
	</div>

	<div style="margin-left:30px; margin-top:30px;" >
		<h2>Execute Trade</h2>
		<%@ include file="template/buttonSubmit.jspf" %>
	</div> 


	<c:set var="tableId1" value="tableStatusTypes" />
	<c:set var="tableData" value="${model[tableId1]}" />
	<div id="divMessageTypes" style="margin-left:12px; margin-top:15px;width:100px;height:100px;display:none;" >
		<div align="center">
				<select id="dropDownListStatus" name="dropDownListStatus" class="tbsStylePageFilterInput" style="width:190px;" >
					<c:forEach items="${tableData.rowList}" varStatus="status" var="row">
										<option value="${row.columnMap['transactionStatusTypeId'].value}" >${row.columnMap['transactionStatusTypeCode'].value}</option>
					</c:forEach>
				</select>
		</div>
	</div>
	
	<input type="hidden" id="transactionId" name="transactionId" value="${model.transactionId}">
	<input type="hidden" id="statusId" name="statusId" value="${model.statusId}">

<%@ include file="template/layoutStandardFooterNew.jspf" %>
