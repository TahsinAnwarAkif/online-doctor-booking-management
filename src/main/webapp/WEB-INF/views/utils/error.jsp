<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>

<head>
    <c:if test="${error != null}">
        <div class="col-md-offset-5">
            <div class="alert alert-success alert-dismissable text-center" style="width: 300px">
                <strong style="color: #FF0000;"><c:out value="${error}"/></strong>
                <span class="close" onclick="this.parentElement.style.display='none';">&times;</span>
            </div>
        </div>
    </c:if>
</head>
<body></body>
</html>