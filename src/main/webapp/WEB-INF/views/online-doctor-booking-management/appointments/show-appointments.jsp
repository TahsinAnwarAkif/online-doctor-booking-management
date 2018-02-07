<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="showAppointments.header"/>
</head>

<body>

<c:if test="${showAppointmentsButton}">
    <c:set var="appointments" value="${allAppointments}"/>
</c:if>

<c:if test="${showMyAppointmentsButton}">
    <c:set var="appointments" value="${selectedAppointments}"/>
</c:if>

<c:if test="${deleteAppointmentsButton}">
    <c:set var="deleteAppointmentsLink" value="${deleteAppointmentsLink}"/>
</c:if>

<c:if test="${deleteMyAppointmentsButton}">
    <c:set var="deleteAppointmentsLink" value="${deleteMyAppointmentsLink}"/>
</c:if>

<tag:table deleteLink="${deleteAppointmentsLink}">

    <jsp:attribute name="thead">
        <tr>
            <th class="tg-i6eq"><fmt:message key="showAppointments.doctor"/></th>
            <th class="tg-i6eq"><fmt:message key="showAppointments.patient"/></th>
            <th class="tg-i6eq"><fmt:message key="showAppointments.date"/></th>
            <th class="tg-i6eq"><fmt:message key="showAppointments.time"/></th>
            <th class="tg-i6eq"><fmt:message key="showAppointments.doctorSpecialty"/></th>
            <th class="tg-i6eq"><fmt:message key="showAppointments.patientProblemOverview"/></th>
            <th class="tg-i6eq"><fmt:message key="showAppointments.status"/></th>
            <c:if test="${updateStatusOfAppointmentsButton || updateMyStatusOfAppointmentsButton}">
                <th class="tg-i6eq"><fmt:message key="btn.update"/></th>
            </c:if>
            <c:if test="${deleteAppointmentsButton || deleteMyAppointmentsButton}">
               <th class="tg-i6eq"><fmt:message key="btn.delete"/></th>
            </c:if>
        </tr>
    </jsp:attribute>

    <jsp:attribute name="tbody">
        <c:forEach var="appointment" items="${appointments}">
            <tr>
                <c:if test="${updateStatusOfAppointmentsButton}">
                    <c:url var="updateLink"
                           value="${updateStatusOfAppointmentLink}"
                           scope="request">
                        <c:param name="id" value="${appointment.id}"/>
                </c:url>
                </c:if>
                <c:if test="${updateMyStatusOfAppointmentsButton}">
                    <c:url var="updateLink"
                           value="${updateStatusOfAppointmentLink}"
                           scope="request">
                        <c:param name="id" value="${appointment.id}"/>
                        <c:param name="userId" value="${userId}"/>
                    </c:url>
                </c:if>
                <td><c:out value="${appointment.doctor.name}"/></td>
                <td><c:out value="${appointment.patient.name}"/></td>
                <td><c:out value="${appointment.date}"/></td>
                <td><c:out value="${appointment.time}"/></td>
                <td><c:out value="${appointment.doctor.specialty.name}"/></td>
                <td><c:out value="${appointment.patient.problem}"/></td>
                <td><c:out value="${appointment.status}"/></td>
                <c:if test="${updateStatusOfAppointmentsButton || updateMyStatusOfAppointmentsButton}">
                    <td>
                        <tag:button name="btn.updateAppointmentStatus" value="${updateLink}"/>
                    </td>
                </c:if>
                <c:if test="${deleteAppointmentsButton || deleteMyAppointmentsButton}">
                    <td>
                        <tag:checkbox name="idsOfAppointmentsToBeDeleted" value="${appointment.id}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </jsp:attribute>
</tag:table>

<tag:bottom-padding value="50px"/>

<div class="text-center">
    <c:if test="${deleteAppointmentsButton || deleteMyAppointmentsButton}">
        <tag:submit form="deleteForm" name="btn.delete" onClick="form.deleteAppointmentConfirmation"/>
    </c:if>

    <tag:bottom-padding value="50px"/>


    <c:if test="${createAppointmentButton}">
        <tag:button name="btn.create" value="${createAppointmentLink}"/>
    </c:if>

    <c:if test="${createMyAppointmentButton}">
        <tag:button name="btn.create" value="${createMyAppointmentLink}"/>
    </c:if>
</div>
</body>
</html>