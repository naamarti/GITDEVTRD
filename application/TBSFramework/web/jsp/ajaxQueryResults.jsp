<%@ include file="template/pageDocTypeXML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>
<c:set var="indent" value="    " />
<root>
<c:forEach items="${model.queryResults}" varStatus="status" var="row">
${indent}<row>
<c:forEach items="${row}" varStatus="status" var="elem">
${indent}${indent}<${elem.key}>${elem.value}</${elem.key}>
</c:forEach>
${indent}</row>
</c:forEach>
${indent}<ajaxStatus>${model.ajaxStatus}</ajaxStatus>
${indent}<sessionMessageSuccess>${model.sessionMessageSuccess}</sessionMessageSuccess>
${indent}<sessionMessageError>${model.sessionMessageError}</sessionMessageError>
</root>
