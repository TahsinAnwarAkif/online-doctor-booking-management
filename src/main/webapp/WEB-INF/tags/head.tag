<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="pageHeader" required="true" %>

<title>
    <fmt:message key="${pageHeader}"/>
</title>

<h2 align="center">
    <fmt:message key="${pageHeader}"/>
</h2>