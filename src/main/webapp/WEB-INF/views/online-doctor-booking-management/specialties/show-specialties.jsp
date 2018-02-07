<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="showSpecialties.header"/>
</head>

<body>

<tag:table deleteLink="${deleteSpecialtiesLink}">

    <jsp:attribute name="thead">
        <tr>
            <th class="tg-i6eq"><fmt:message key="showSpecialties.name"/></th>
            <th class="tg-i6eq"><fmt:message key="showSpecialties.description"/></th>
            <c:if test="${updateSpecialtiesButton}">
                <th class="tg-i6eq"><fmt:message key="btn.update"/></th>
            </c:if>
            <c:if test="${deleteSpecialtiesButton}">
                <th class="tg-i6eq"><fmt:message key="btn.delete"/></th>
            </c:if>
        </tr>
    </jsp:attribute>

    <jsp:attribute name="tbody">
        <c:forEach var="specialty" items="${allSpecialties}">
            <tr>
                <c:url var="updateSpecialtyLink" value="${showSpecialtyFormLink}" scope="request">
                    <c:param name="id" value="${specialty.id}"/>
                </c:url>
                <td><c:out value="${specialty.name}"/></td>
                <td><c:out value="${specialty.description}"/></td>
                <c:if test="${updateSpecialtiesButton}">
                    <td>
                        <tag:button name="btn.update" value="${updateSpecialtyLink}"/>
                    </td>
                </c:if>
                <c:if test="${deleteSpecialtiesButton}">
                    <td>
                        <tag:checkbox name="idsOfSpecialtiesToBeDeleted" value="${specialty.id}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </jsp:attribute>
</tag:table>

<tag:bottom-padding value="50px"/>

<div class="text-center">
    <c:if test="${deleteSpecialtiesButton}">
        <tag:submit form="deleteForm" name="btn.delete" onClick="form.deleteSpecialtyConfirmation"/>
    </c:if>

    <tag:bottom-padding value="50px"/>

    <c:if test="${createSpecialtiesButton}">
        <tag:button name="btn.create" value="${showSpecialtyFormLink}"/>
    </c:if>
</div>
</body>
</html>