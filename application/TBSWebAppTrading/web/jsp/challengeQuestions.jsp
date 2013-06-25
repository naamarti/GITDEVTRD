<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="challengeQuestions_v0001.js" />
<c:set var="javaScriptOnReady" value="jScript.challengeQuestions.init();" />
<c:set var="layoutShowIconBar" value="0" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:30px; margin-top:30px; padding:3px; width:546px; font-weight:bold; font-family:Arial,Verdana,Tahoma; font-size:14px; color:White; background-color:#444444;" >
		Security Question Setup
	</div>

	<div class="tbsStyleForm tbsStyleRounded" style="margin-left:30px; margin-top:6px; width:550px; border:1px solid; overflow:hidden;" >
		<table width="550px" cellpadding="1" border="0" style="font-family:Verdana;font-size:12px;">
			<colgroup>
				<col style="width:10px;">
				<col style="width:300px;">
				<col style="width:10px;">
				<col style="width:200px;">
				<col style="width:30px;">
			</colgroup>
			<c:forEach items="${model.challengeResponseList}" varStatus="status" var="r">
				<c:set var="i" value="${status.count}" />
				<tr>
					<td>&nbsp;</td>
					<td align="left">
						<select id="question${i}" name="question${i}" class="tbsStyleFormInput" style="width:295px;">
							<option value="0" ${r.questionId == 0 ? 'selected' : ''}>Select a Security Question...</option>
							<c:forEach items="${model.challengeQuestionList}" var="q">
								<option value="${q.questionId}" ${r.questionId == q.questionId ? 'selected' : ''}>${q.question}</option>
							</c:forEach>
						</select>
					</td>
					<td>&nbsp;</td>
					<td class="forminput">
						<input id="challengeResponse${i}" name="challengeResponse${i}" type="text" value="${r.response}" class="tbsStyleFormInput" style="width:195px;"/>
					</td>
					<td>&nbsp;</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<%-- ********** BUTTONS ********** --%>
	<div style="margin-top:12px; width:590px; height:38px; position:relative;" >
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

