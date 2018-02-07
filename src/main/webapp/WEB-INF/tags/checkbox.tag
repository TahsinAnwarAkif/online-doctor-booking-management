<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="name" required="true" %>
<%@ attribute name="value" required="true" %>

<input type="checkbox" name="${name}" value="${value}"/>