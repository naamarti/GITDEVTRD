<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="changePassword_v0002.js" />
<c:set var="javaScriptOnReady" value="jScript.changePassword.init();" />
<c:set var="layoutShowIconBar" value="0" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:30px; margin-top:30px; padding:3px; width:446px; font-weight:bold; font-family:Arial,Verdana,Tahoma; font-size:14px; color:White; background-color:#444444;" >
		Change Password
	</div>
	
	<%-- ********** LOGIN FORM ********** --%>
	<c:set var="formId" value="loginForm" />
	<c:set var="formMarginLeft" value="30" />
	<c:set var="formMarginTop" value="6" />
	<%@ include file="template/form.jspf" %>

	<%-- ********** BUTTONS ********** --%>
	<div style="margin-top:6px; width:492px; height:38px; position:relative;" >
		<div style="width:140px; float:right;" >
			<%@ include file="template/buttonSave.jspf" %>
		</div>
		<div style="width:1px; height:1px; overflow:hidden;" >
			<input type="submit" value="" style="margin-left:10px; margin-top:10px;" />
		</div>
	</div>

	<%-- ********** HIDDEN PARAMS ********** --%>
	<input type="hidden" id="loginStep" name="loginStep" value="${model.loginStep}">

<%@ include file="template/layoutStandardFooterNew.jspf" %>

