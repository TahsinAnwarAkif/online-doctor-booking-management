<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<jsp:include page="utils/placeholders.jsp"/>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="doctorForm.header"/>
</head>

<body>

<form:form id="createOrUpdateDoctorForm" cssClass="col-md-12" action="${createOrUpdateDoctorActionLink}"
           modelAttribute="user"
           method="post">

    <div class="col-md-8">
        <form:hidden path="id"/>

        <tag:form-input path="name" message="userForm.doctorName" placeholder="${userFormDoctorName}"/>

        <tag:form-input path="username" message="userForm.userName" placeholder="${userFormUserName}"/>

        <div class="form-group" style="padding-bottom: 50px">
            <label for="specialty" class="col-xs-4"><fmt:message key="userForm.doctorSpecialty"/></label>

            <div class="col-xs-5">
                <form:select cssClass="form-control" path="specialty">

                    <c:if test="${user.specialty == null}">
                        <form:options items="${allSpecialties}" itemValue="id" itemLabel="name"/>
                    </c:if>

                    <c:if test="${user.specialty != null}">
                        <form:option value="${user.specialty.id}" label="${user.specialty.name}"/>

                        <c:forEach var="otherSpecialties" items="${allSpecialties}">
                            <c:if test="${otherSpecialties.id != user.specialty.id}">
                                <form:option value="${otherSpecialties.id}" label="${otherSpecialties.name}"/>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </form:select>
            </div>
        </div>

        <tag:form-input path="password" type="password" message="userForm.userPassword"
                        placeholder="${userFormUserPassword}"/>

        <tag:form-input path="address" message="userForm.address" placeholder="${userFormAddress}"/>

        <tag:form-input path="phone" message="userForm.phone" placeholder="${userFormPhone}"/>

        <tag:form-input path="email" message="userForm.email" placeholder="${userFormEmail}"/>

        <tag:form-input path="joiningDate" message="userForm.doctorJoiningDate" placeholder="${formDatePattern}"/>

        <tag:form-input path="dailyScheduleStart" message="userForm.doctorDailyScheduleStart"
                        placeholder="${formTimePattern}"/>

        <tag:form-input path="dailyScheduleEnd" message="userForm.doctorDailyScheduleEnd"
                        placeholder="${formTimePattern}"/>

        <tag:form-input path="estimatedVisitsPerDay" message="userForm.doctorVisitsPerDay"
                        placeholder="${userFormDoctorVisitsPerDay}"/>

        <tag:form-input path="visitingAmount" message="userForm.doctorVisitingAmount"
                        placeholder="${userFormDoctorVisitingAmount}"/>

        <form:hidden path="role" value="DOCTOR"/>
    </div>

    <tag:form-errors path="*"/>

</form:form>

<div class="col-md-offset-4">
    <c:if test="${createOrUpdateDoctorsAsAdminButton}">
        <tag:submit form="createOrUpdateDoctorForm" name="btn.submit"/>
    </c:if>
    <c:if test="${!createOrUpdateDoctorsAsAdminButton}">
        <tag:submit form="createOrUpdateDoctorForm" name="btn.submit" onClick="userForm.confirmation"/>
    </c:if>
</div>
</body>
</html>