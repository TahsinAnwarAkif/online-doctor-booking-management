<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<jsp:include page="/WEB-INF/views/utils/mappings.jsp"/>

<!DOCTYPE html>

<html>

<head>
    <tag:head pageHeader="login.header"/>
    <hr/>
</head>

<body>
<div class="col-md-12 col-md-offset-4">

    <div class="col-md-offset-1" style="padding-bottom: 150px">
        <tag:button name="btn.registerAsDoctor" value="${showDoctorFormLink}"/>
        <tag:button name="btn.registerAsPatient" value="${showPatientFormLink}"/>
    </div>


    <form id="loginForm" class="col-md-8" action="${loginActionLink}" method="post">
        <div class="col-md-4 div-center">
            <tag:input name="userName" message="loginForm.userName"/>

            <tag:bottom-padding value="50px"/>

            <tag:input type="password" name="userPassword" message="loginForm.userPassword"/>
        </div>

        <div class="col-md-4">
            <div class="alert-danger"><c:out value="${authError}"/></div>
        </div>

        <tag:bottom-padding value="150px"/>

        <div class="col-md-offset-3">
            <tag:submit form="loginForm" name="btn.submit"/>
        </div>
    </form>
</div>
</body>
</html>