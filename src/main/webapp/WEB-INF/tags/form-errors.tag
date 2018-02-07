<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" %>

<div class="col-md-4">
    <div class="col-xs-12">
        <form:errors cssClass="alert-danger" path="${path}"/>
    </div>
</div>