<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>
<%@ page isErrorPage="true" %>
<c:set var="isErrorPage" value="true" />

<%-- ********** LAYOUT STANDARD ********** --%>
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:30px; margin-top:30px;" >
		<div class="tbs-ui-message-error tbsStyleShadow2" style="margin-bottom:15px; width:600px;" >
			An unexpected error has occured. Please contact your system administrator.
		</div>
		
		<a href="<c:url value="login.htm" />" class="tbsStyleLinks" >
			Back to Login
		</a>
	</div>

	<c:if test="${ !fn:startsWith(model.environmentName, 'PRD') }" >
		<div class="tbs-ui-message-info tbsStyleShadow2" style="margin-left:30px; margin-top:60px; width:600px;" >
			<b>DEBUG INFO:</b>
			&nbsp;(<i>these details will <u>not</u> be displayed in Production</i>)
			<br/><br/>
			<b>pageContext.exception =</b> &nbsp; ${pageContext.exception}
			<br/><br/>
			<b>sessionMessageError =</b> &nbsp; ${model.sessionMessageError}
			<br/><br/>
			<b>sessionMessageDebug =</b> &nbsp; ${model.sessionMessageDebug}
			<br/><br/>
		</div>
	</c:if>

<%@ include file="template/layoutStandardFooterNew.jspf" %>

