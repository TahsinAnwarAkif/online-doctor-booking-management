<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>

<html>

<h1 align="center"><fmt:message key="project.header"/></h1>

<tag:bottom-padding value="25px"/>
<c:if test="${userId != null}">
    <div class="row margin-top-20">
        <div class="col-md-12">
            <div class="col-md-6 col-xs-offset-4">

                <tag:button name="btn.showDoctors" value="${showDoctorsLink}"/>

                <c:if test="${showPatientsButton}">
                    <tag:button name="btn.showPatients" value="${showPatientsLink}"/>
                </c:if>

                <c:if test="${showSpecialtiesButton}">
                    <tag:button name="btn.showSpecialties" value="${showSpecialtiesLink}"/>
                </c:if>

                <c:if test="${showDoctorsProfileButton}">
                    <tag:button name="btn.myProfile" value="${updateProfileLinkForDoctors}"/>
                </c:if>

                <c:if test="${showPatientsProfileButton}">
                    <tag:button name="btn.myProfile" value="${updateProfileLinkForPatients}"/>
                </c:if>

                <c:if test="${rolesButton}">
                    <tag:button name="btn.showRoles" value="${showRolesLink}"/>
                </c:if>

                <c:if test="${adminsButton}">
                    <tag:button name="btn.showAdmins" value="${showAdminsLink}"/>
                </c:if>

                <c:if test="${showAppointmentsButton}">
                    <tag:button name="btn.showAppointments" value="${showAppointmentsLink}"/>
                </c:if>

                <c:if test="${showMyAppointmentsButton}">
                    <tag:button name="btn.showMyAppointments" value="${showMyAppointmentsLink}"/>
                </c:if>
            </div>

            <div class="col-md-offset-10">
                <tag:button name="btn.logout" value="${logoutLink}"/>
            </div>
            <hr/>
        </div>
    </div>
</c:if>
</html>