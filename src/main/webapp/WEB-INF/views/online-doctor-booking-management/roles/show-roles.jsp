<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="showRoles.header"/>
</head>

<body>

<tag:table>

    <jsp:attribute name="thead">
        <tr>
            <th class="tg-i6eq"><fmt:message key="showRoles.type"/></th>
            <th class="tg-i6eq"><fmt:message key="showRoles.actions"/></th>
            <th class="tg-i6eq"><fmt:message key="btn.update"/></th>
        </tr>
    </jsp:attribute>

    <jsp:attribute name="tbody">
            <c:forEach var="role" items="${allRoles}">
                <tr>
                    <c:url var="updateLink" value="${updateRoleFormLink}" scope="request">
                        <c:param name="type" value="${role.type}"/>
                    </c:url>
                    <td><c:out value="${role.type}"/></td>
                    <td><c:out value="${role.actions}"/></td>
                    <td>
                        <tag:button name="btn.update" value="${updateLink}"/>
                    </td>
                </tr>
            </c:forEach>
        </jsp:attribute>
</tag:table>
</body>
</html>