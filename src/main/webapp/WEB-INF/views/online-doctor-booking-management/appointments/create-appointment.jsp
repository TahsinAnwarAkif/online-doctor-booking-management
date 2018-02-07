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
    <tag:head pageHeader="createAppointmentForm.header"/>
</head>

<body>

<c:if test="${createAppointmentButton}">
    <c:set var="patients" value="${allPatients}"/>
    <c:set var="createAppointmentActionLink" value="${createAppointmentActionLink}"/>
</c:if>

<c:if test="${createMyAppointmentButton}">
    <c:set var="selectedPatient" value="${selectedPatient}"/>
    <c:set var="createAppointmentActionLink" value="${createMyAppointmentActionLink}"/>
</c:if>

<form:form id="createAppointmentForm" class="col-md-12" action="${createAppointmentActionLink}"
           modelAttribute="appointment" method="post">

    <div class="col-md-8">

        <div class="form-group" style="padding-bottom: 50px">
            <form:label path="patient" cssClass="col-xs-4"><fmt:message key="userForm.patientName"/></form:label>

            <div class="col-xs-5">
                <c:if test="${createAppointmentButton}">
                    <form:select path="patient" cssClass="form-control">
                        <form:options items="${patients}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </c:if>
                <c:if test="${createMyAppointmentButton}">
                    <form:select path="patient" cssClass="form-control">
                        <form:option value="${selectedPatient.id}" label="${selectedPatient.name}"/>
                    </form:select>
                </c:if>
            </div>
        </div>

        <div class="form-group" style="padding-bottom: 50px">
            <form:label path="doctor" class="col-xs-4"><fmt:message key="userForm.doctorName"/></form:label>

            <div class="col-xs-5">
                <form:select cssClass="form-control" path="doctor">
                    <form:options items="${allDoctors}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>

        <tag:form-input path="date" message="appointmentForm.date" placeholder="${formDatePattern}"/>

        <tag:form-input path="time" message="appointmentForm.time" placeholder="${formTimePattern}"/>

        <form:hidden path="status" value="OK"/>
    </div>

    <tag:form-errors path="*"/>

</form:form>

<div class="col-md-offset-4">
    <tag:submit form="createAppointmentForm" name="btn.submit"/>
</div>
</body>
</html>