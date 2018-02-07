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
    <tag:head pageHeader="patientForm.header"/>
</head>

<body>

<form:form id="createOrUpdatePatientForm" class="col-md-12" action="${createOrUpdatePatientActionLink}"
           modelAttribute="user"
           method="post">

    <div class="col-md-8">

        <form:hidden path="id"/>

        <tag:form-input path="name" message="userForm.patientName" placeholder="${userFormPatientName}"/>

        <tag:form-input path="username" message="userForm.userName" placeholder="${userFormUserName}"/>

        <tag:form-input path="password" type="password" message="userForm.userPassword"
                        placeholder="${userFormUserPassword}"/>

        <tag:form-input path="address" message="userForm.address" placeholder="${userFormAddress}"/>

        <tag:form-input path="phone" message="userForm.phone" placeholder="${userFormPhone}"/>

        <tag:form-input path="email" message="userForm.email" placeholder="${userFormEmail}"/>

        <tag:form-input path="ssn" message="userForm.patientSsn" placeholder="${userFormPatientSsn}"/>

        <tag:form-input path="problem" message="userForm.patientProblem" placeholder="${userFormPatientProblem}"/>

        <form:hidden path="role" value="PATIENT"/>
    </div>

    <tag:form-errors path="*"/>

</form:form>

<div class="col-md-offset-4">
    <c:if test="${createOrUpdatePatientsAsAdminButton}">
        <tag:submit form="createOrUpdatePatientForm" name="btn.submit"/>
    </c:if>
    <c:if test="${!createOrUpdatePatientsAsAdminButton}">
        <tag:submit form="createOrUpdatePatientForm" name="btn.submit" onClick="userForm.confirmation"/>
    </c:if>
</div>
</body>
</html>