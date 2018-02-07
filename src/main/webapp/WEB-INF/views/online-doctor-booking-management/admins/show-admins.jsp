<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="showAdmins.header"/>
</head>

<body>

<tag:table deleteLink="${deleteAdminsLink}">

    <jsp:attribute name="thead">
        <tr>
            <th class="tg-i6eq"><fmt:message key="showUsers.name"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.username"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.address"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.phone"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.email"/></th>
            <th class="tg-i6eq"><fmt:message key="btn.update"/></th>
            <th class="tg-i6eq"><fmt:message key="btn.delete"/></th>
        </tr>
    </jsp:attribute>

    <jsp:attribute name="tbody">
        <c:forEach var="admin" items="${allAdmins}">
            <tr>
                <c:url var="updateAdminLink" value="${showAdminFormLink}" scope="request">
                            <c:param name="id" value="${admin.id}"/>
                </c:url>
                <td><c:out value="${admin.name}"/></td>
                <td><c:out value="${admin.username}"/></td>
                <td><c:out value="${admin.address}"/></td>
                <td><c:out value="${admin.phone}"/></td>
                <td><c:out value="${admin.email}"/></td>
                <td>
                    <tag:button name="btn.update" value="${updateAdminLink}"/>
                </td>
                <td>
                    <tag:checkbox name="idsOfAdminsToBeDeleted" value="${admin.id}"/>
                </td>
            </tr>
        </c:forEach>
    </jsp:attribute>
</tag:table>

<tag:bottom-padding value="50px"/>

<div class="text-center">
    <tag:submit form="deleteForm" name="btn.delete" onClick="form.deleteUserConfirmation"/>

    <tag:bottom-padding value="50px"/>

    <tag:button name="btn.create" value="${showAdminFormLink}"/>
</div>
</body>
</html>