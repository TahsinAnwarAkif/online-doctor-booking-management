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
    <tag:head pageHeader="specialtyForm.header"/>
</head>

<body>

<form:form id="createOrUpdateSpecialtyForm" cssClass="col-md-12" action="${createOrUpdateSpecialtyActionLink}"
           modelAttribute="specialty"
           method="post">

    <div class="col-md-8">

        <form:hidden path="id"/>

        <tag:form-input path="name" message="specialtyForm.name" placeholder="${specialtyFormSpecialtyName}"/>

        <tag:form-input path="description" message="specialtyForm.description"
                        placeholder="${specialtyFormSpecialtyDescription}"/>
    </div>

    <tag:form-errors path="*"/>

</form:form>

<div class="col-md-offset-4">
    <tag:submit form="createOrUpdateSpecialtyForm" name="btn.submit"/>
</div>
</body>
</html>