<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="showPatients.header"/>
</head>

<body>

<tag:table deleteLink="${deletePatientsLink}">

    <jsp:attribute name="thead">
        <tr>
            <th class="tg-i6eq"><fmt:message key="showUsers.name"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.username"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.address"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.phone"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.email"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.patientSsn"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.patientProblem"/></th>
            <c:if test="${updatePatientsButton}">
                <th class="tg-i6eq"><fmt:message key="btn.update"/></th>
            </c:if>
            <c:if test="${deletePatientsButton}">
                <th class="tg-i6eq"><fmt:message key="btn.delete"/></th>
            </c:if>
        </tr>
    </jsp:attribute>

    <jsp:attribute name="tbody">
        <c:forEach var="patient" items="${allPatients}">
            <tr>
                <c:url var="updatePatientLink" value="${showPatientFormLink}" scope="request">
                    <c:param name="id" value="${patient.id}"/>
                </c:url>
                <td><c:out value="${patient.name}"/></td>
                <td><c:out value="${patient.username}"/></td>
                <td><c:out value="${patient.address}"/></td>
                <td><c:out value="${patient.phone}"/></td>
                <td><c:out value="${patient.email}"/></td>
                <td><c:out value="${patient.ssn}"/></td>
                <td><c:out value="${patient.problem}"/></td>
                <c:if test="${updatePatientsButton}">
                    <td>
                        <tag:button name="btn.update" value="${updatePatientLink}"/>
                    </td>
                </c:if>
                <c:if test="${deletePatientsButton}">
                    <td>
                        <tag:checkbox name="idsOfPatientsToBeDeleted" value="${patient.id}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </jsp:attribute>
</tag:table>

<tag:bottom-padding value="50px"/>

<div class="text-center">
    <c:if test="${deletePatientsButton}">
        <tag:submit form="deleteForm" name="btn.delete" onClick="form.deleteUserConfirmation"/>
    </c:if>

    <tag:bottom-padding value="50px"/>

    <tag:button name="btn.create" value="${showPatientFormLink}"/>
</div>
</body>
</html>