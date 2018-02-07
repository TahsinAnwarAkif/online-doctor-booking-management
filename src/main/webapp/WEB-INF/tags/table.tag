<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="thead" fragment="true" required="true" %>
<%@ attribute name="tbody" fragment="true" required="true" %>
<%@ attribute name="deleteLink" %>

<table border="3" class="table-default" align="center">
    <thead>
    <jsp:invoke fragment="thead"/>
    </thead>

    <tbody>
    <form id="deleteForm" action="${deleteLink}" method="post">
        <jsp:invoke fragment="tbody"/>
    </form>
    </tbody>
</table>