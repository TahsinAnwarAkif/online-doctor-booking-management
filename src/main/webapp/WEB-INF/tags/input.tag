<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="name" required="true" %>
<%@ attribute name="type" %>
<%@ attribute name="message" required="true" %>

<fmt:message key="${message}" var="message"/>

<div class="form-group">
    <label for="${name}" class="col-xs-4">
        ${message}
    </label>

    <div class="col-xs-8">
        <input type="${type}" class="form-control" name="${name}"
               placeholder="${message}"/>
    </div>
</div>