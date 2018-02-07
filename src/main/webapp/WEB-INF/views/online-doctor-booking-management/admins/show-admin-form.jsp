<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<jsp:include page="../../utils/placeholders.jsp"/>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="adminForm.header"/>
</head>

<body>

<form:form id="createOrUpdateAdminForm" class="col-md-12" action="${createOrUpdateAdminActionLink}"
           modelAttribute="user"
           method="post">

    <div class="col-md-8">

        <form:hidden path="id"/>

        <tag:form-input path="name" message="userForm.adminName" placeholder="${userFormAdminName}"/>

        <tag:form-input path="username" message="userForm.userName" placeholder="${userFormUserName}"/>

        <tag:form-input path="password" type="password" message="userForm.userPassword"
                        placeholder="${userFormUserPassword}"/>

        <tag:form-input path="address" message="userForm.address" placeholder="${userFormAddress}"/>

        <tag:form-input path="phone" message="userForm.phone" placeholder="${userFormPhone}"/>

        <tag:form-input path="email" message="userForm.email" placeholder="${userFormEmail}"/>

        <form:hidden path="role" value="ADMIN"/>
    </div>

    <tag:form-errors path="*"/>

</form:form>

<div class="col-md-offset-4">
    <tag:submit form="createOrUpdateAdminForm" name="btn.submit"/>
</div>
</body>
</html>