<%@ tag pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="form" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="onClick" %>

<c:if test="${onClick != null}">
    <input type="submit" form="${form}" class="btn btn-default" value="<fmt:message key="${name}"/>"
           onclick="if(!confirm('<fmt:message key="${onClick}"/>'))return false;"/>
</c:if>

<c:if test="${onClick == null}">
    <input type="submit" form="${form}" class="btn btn-default" value="<fmt:message key="btn.submit"/>"/>
</c:if>