<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="showDoctors.header"/>
</head>

<body>

<tag:table deleteLink="${deleteDoctorsLink}">

    <jsp:attribute name="thead">
        <tr>
            <th class="tg-i6eq"><fmt:message key="showUsers.name"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.username"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.doctorJoiningDate"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.doctorSpecialty"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.doctorDailyScheduleStart"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.doctorDailyScheduleEnd"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.doctorVisitsPerDay"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.doctorVisitingAmount"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.address"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.phone"/></th>
            <th class="tg-i6eq"><fmt:message key="showUsers.email"/></th>
            <c:if test="${createOrUpdateDoctorsAsAdminButton}">
                <th class="tg-i6eq"><fmt:message key="btn.update"/></th>
            </c:if>
            <c:if test="${deleteDoctorsButton}">
                <th class="tg-i6eq"><fmt:message key="btn.delete"/></th>
            </c:if>
        </tr>
    </jsp:attribute>

    <jsp:attribute name="tbody">
        <c:forEach var="doctor" items="${allDoctors}">
            <tr>
                <c:url var="updateDoctorLink" value="${showDoctorFormLink}" scope="request">
                    <c:param name="id" value="${doctor.id}"/>
                </c:url>
                <td><c:out value="${doctor.name}"/></td>
                <td><c:out value="${doctor.username}"/></td>
                <td><c:out value="${doctor.joiningDate}"/></td>
                <td><c:out value="${doctor.specialty.name}"/></td>
                <td><c:out value="${doctor.dailyScheduleStart}"/></td>
                <td><c:out value="${doctor.dailyScheduleEnd}"/></td>
                <td><c:out value="${doctor.estimatedVisitsPerDay}"/></td>
                <td><c:out value="${doctor.visitingAmount}"/></td>
                <td><c:out value="${doctor.address}"/></td>
                <td><c:out value="${doctor.phone}"/></td>
                <td><c:out value="${doctor.email}"/></td>
                <c:if test="${createOrUpdateDoctorsAsAdminButton}">
                    <td>
                        <tag:button name="btn.update" value="${updateDoctorLink}"/>
                    </td>
                </c:if>
                <c:if test="${deleteDoctorsButton}">
                    <td>
                        <tag:checkbox name="idsOfDoctorsToBeDeleted" value="${doctor.id}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </jsp:attribute>
</tag:table>

<tag:bottom-padding value="50px"/>

<div class="text-center">
    <c:if test="${deleteDoctorsButton}">
        <tag:submit form="deleteForm" name="btn.delete" onClick="form.deleteUserConfirmation"/>
    </c:if>

    <tag:bottom-padding value="50px"/>

    <c:if test="${createOrUpdateDoctorsAsAdminButton}">
        <tag:button name="btn.create" value="${showDoctorFormLink}"/>
    </c:if>
</div>
</body>
</html>