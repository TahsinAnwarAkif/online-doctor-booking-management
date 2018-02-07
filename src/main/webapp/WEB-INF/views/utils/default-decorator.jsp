<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<jsp:include page="/WEB-INF/views/utils/mappings.jsp"/>

<spring:url value="../../../resources/css/bootstrap.min.css" var="stylesheet"/>
<spring:url value="../../../resources/css/custom-styles.css" var="customStylesheet"/>
<spring:url value="../../../resources/js/bootstrap.min.js" var="js"/>

<link href="${stylesheet}" rel="stylesheet" type="text/css"/>
<link href="${customStylesheet}" rel="stylesheet" type="text/css"/>
<script src="${customStylesheet}"></script>
<script src="${js}"></script>

<html>

<head>
    <jsp:include page="/WEB-INF/views/utils/header.jsp"/>
    <title><sitemesh:write property='title'/></title>
    <sitemesh:write property='head'/>
    <tag:bottom-padding value="50px"/>
    <jsp:include page="/WEB-INF/views/utils/success-alert.jsp"/>
</head>

<body>
<sitemesh:write property='body'/>
<tag:bottom-padding value="50px"/>
<jsp:include page="/WEB-INF/views/utils/footer.jsp"/>
</body>
</html>