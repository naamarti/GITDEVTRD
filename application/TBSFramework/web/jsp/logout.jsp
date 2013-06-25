<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="layoutShowIconBar" value="0" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:30px; margin-top:100px;" >
		<div class="tbs-ui-message-info tbsStyleShadow2" style="margin-bottom:15px; width:600px;" >
			You have been logged off the system.
		</div>
		
		<a href="<c:url value="login.htm" />" class="tbsStyleLinks" >
			Back to Login
		</a>
	</div>

<%@ include file="template/layoutStandardFooterNew.jspf" %>

