<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<jsp:include page="../../utils/placeholders.jsp"/>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="roleForm.header"/>
</head>

<body>

<form:form id="updateRoleForm" cssClass="col-md-12" action="${updateRoleActionLink}" modelAttribute="role"
           method="post">

    <div class="col-md-8">
        <div class="form-group">
            <form:label path="type" cssClass="col-xs-4"><fmt:message key="roleForm.type"/></form:label>

            <div class="col-xs-4">
                <form:select path="type">
                    <form:option value="${role.type}" label="${role.type}"/>
                </form:select>
            </div>
        </div>

        <div class="form-group" style="padding-top: 50px">

            <form:label path="actions" cssClass="col-xs-4"><fmt:message key="roleForm.actions"/></form:label>

            <div class="col-xs-5">
                <c:forEach var="action" items="${allRoleActions}">
                    <c:if test="${fn:contains(role.actions, action)}">
                        <tag:form-checkbox path="actions" value="${action}" checked="checked"/>
                    </c:if>

                    <c:if test="${!fn:contains(role.actions, action)}">
                        <tag:form-checkbox path="actions" value="${action}"/>
                    </c:if>

                    <tag:bottom-padding value="25px"/>
                </c:forEach>
            </div>
        </div>

        <tag:form-errors path="*"/>

    </div>
</form:form>

<div class="col-md-offset-4">
    <tag:submit form="updateRoleForm" name="btn.submit"/>
</div>
</body>
</html>