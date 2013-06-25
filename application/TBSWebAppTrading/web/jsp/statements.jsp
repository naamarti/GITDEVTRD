<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="statements_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.statements.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Statements</span>
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/excel1_48.png" style="left:15px; top:9px; height:36px; width:36px; z-index:10; position:relative; border-style:none;" alt=""/>
			<img src="images/pdf1_48.png" style="left:-40px; top:0px; height:36px; width:36px; z-index:10; position:relative; border-style:none;" alt=""/>
		</div>
		<div style="left:-30px; margin-top:9px; position:relative;" >
			<span class="tbs-ui-pagefilter-screentitle">Statements</span>
		</div>
	</div>


	<div style="margin-left:9px; margin-top:15px; width:945px; height:150px;" >
		<div class="tbs-ui-page-section-bar" >
			Monthly Statements
		</div>
	</div>

	<div style="margin-left:9px; margin-top:15px; width:945px; height:150px;" >
		<div class="tbs-ui-page-section-bar" >
			Confirmations
		</div>
	</div>

<%@ include file="template/layoutStandardFooterNew.jspf" %>
