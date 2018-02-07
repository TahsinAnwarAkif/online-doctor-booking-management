<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="checked" %>

<form:checkbox path="${path}" value="${value}" checked="${checked}"/>
<strong><c:out value="${value}"/></strong>