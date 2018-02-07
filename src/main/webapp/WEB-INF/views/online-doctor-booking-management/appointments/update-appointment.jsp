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
    <tag:head pageHeader="updateAppointmentForm.header"/>
</head>

<body>

<c:if test="${updateStatusOfAppointmentsButton}">
    <c:set var="updateStatusOfAppointmentActionLink" value="${updateStatusOfAppointmentActionLink}"/>
</c:if>

<c:if test="${updateMyStatusOfAppointmentsButton}">
    <c:set var="updateStatusOfAppointmentActionLink" value="${updateMyStatusOfAppointmentActionLink}"/>
</c:if>

<form id="updateAppointmentForm" class="col-md-12" action="${updateStatusOfAppointmentActionLink}" method="post">

    <input type="hidden" name="id" value="${selectedAppointment.id}"/>

    <div class="col-md-8">
        <div class="form-group" style="padding-bottom: 50px">
            <label class="col-xs-4"><fmt:message key="userForm.patientName"/></label>

            <div class="col-xs-5" style="padding-bottom: 50px">
                <strong><c:out value="${selectedAppointment.patient.name}"/></strong>
            </div>
        </div>

        <div class="form-group" style="padding-bottom: 50px">
            <label class="col-xs-4"><fmt:message key="userForm.doctorName"/></label>

            <div class="col-xs-5">
                <strong><c:out value="${selectedAppointment.doctor.name}"/></strong>
            </div>
        </div>

        <div class="form-group" style="padding-bottom: 50px">
            <label class="col-xs-4"><fmt:message key="appointmentForm.date"/></label>

            <div class="col-xs-5">
                <strong><c:out value="${selectedAppointment.date}"/></strong>
            </div>
        </div>

        <div class="form-group" style="padding-bottom: 50px">
            <label class="col-xs-4"><fmt:message key="appointmentForm.time"/></label>

            <div class="col-xs-5">
                <strong><c:out value="${selectedAppointment.time}"/></strong>
            </div>
        </div>

        <div class="form-group" style="padding-bottom: 50px">
            <label class="col-xs-4"><fmt:message key="appointmentForm.status"/></label>

            <div class="col-xs-5">
                <select class="form-control" name="status">
                    <option value="${selectedAppointment.status}"
                            selected><c:out value="${selectedAppointment.status}"/></option>
                    <c:forEach var="otherStatus" items="${allStatus}">
                        <c:if test="${otherStatus != selectedAppointment.status}">
                            <option value="${otherStatus}"><c:out value="${otherStatus}"/></option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>

    <tag:form-errors path="*"/>

</form>

<tag:bottom-padding value="50px"/>

<div class="col-md-offset-4">
    <tag:submit form="updateAppointmentForm" name="btn.submit"/>
</div>
</body>
</html>