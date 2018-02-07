<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="loginActionLink" value="/login-action" scope="session"/>
<c:url var="logoutLink" value="/logout" scope="session"/>
<c:url var="menuLink" value="/online-doctor-booking-management/home" scope="session"/>
<c:url var="goBackLink" value="/go-back" scope="session"/>

<c:url var="showDoctorsLink" value="/online-doctor-booking-management/doctors/show-doctors" scope="session"/>
<c:url var="showDoctorFormLink" value="/show-doctor-form" scope="session"/>
<c:url var="createOrUpdateDoctorActionLink" value="/create-or-update-doctor-action" scope="session"/>
<c:url var="deleteDoctorsLink" value="/online-doctor-booking-management/doctors/delete-doctors" scope="session"/>

<c:url var="showPatientsLink" value="/online-doctor-booking-management/patients/show-patients" scope="session"/>
<c:url var="showPatientFormLink" value="/show-patient-form" scope="session"/>
<c:url var="createOrUpdatePatientActionLink" value="/create-or-update-patient-action" scope="session"/>
<c:url var="deletePatientsLink" value="/online-doctor-booking-management/patients/delete-patients" scope="session"/>

<c:url var="showSpecialtiesLink" value="/online-doctor-booking-management/specialties/show-specialties"
       scope="session"/>
<c:url var="showSpecialtyFormLink" value="/online-doctor-booking-management/specialties/show-specialty-form"
       scope="session"/>
<c:url var="createOrUpdateSpecialtyActionLink"
       value="/online-doctor-booking-management/specialties/create-or-update-specialty"
       scope="session"/>
<c:url var="deleteSpecialtiesLink" value="/online-doctor-booking-management/specialties/delete-specialties"
       scope="session"/>

<c:url var="showAdminsLink" value="/online-doctor-booking-management/admins/show-admins" scope="session"/>
<c:url var="showAdminFormLink" value="/online-doctor-booking-management/admins/show-admin-form" scope="session"/>
<c:url var="createOrUpdateAdminActionLink"
       value="/online-doctor-booking-management/admins/create-or-update-admin-action"
       scope="session"/>
<c:url var="deleteAdminsLink" value="/online-doctor-booking-management/admins/delete-admins" scope="session"/>

<c:url var="showAppointmentsLink" value="/online-doctor-booking-management/appointments/show-appointments"
       scope="session"/>
<c:url var="showMyAppointmentsLink" value="/online-doctor-booking-management/appointments/show-appointments"
       scope="session">
    <c:param name="userId" value="${userId}"/>
</c:url>
<c:url var="createAppointmentLink" value="/online-doctor-booking-management/appointments/create-appointment"
       scope="session"/>
<c:url var="createMyAppointmentLink" value="/online-doctor-booking-management/appointments/create-appointment"
       scope="session">
    <c:param name="userId" value="${userId}"/>
</c:url>
<c:url var="createAppointmentActionLink"
       value="/online-doctor-booking-management/appointments/create-appointment-action"
       scope="session"/>
<c:url var="createMyAppointmentActionLink"
       value="/online-doctor-booking-management/appointments/create-appointment-action"
       scope="session">
    <c:param name="userId" value="${userId}"/>
</c:url>
<c:url var="deleteAppointmentsLink" value="/online-doctor-booking-management/appointments/delete-appointments"
       scope="session"/>
<c:url var="deleteMyAppointmentsLink" value="/online-doctor-booking-management/appointments/delete-appointments"
       scope="session">
    <c:param name="userId" value="${userId}"/>
</c:url>
<c:url var="updateStatusOfAppointmentLink"
       value="/online-doctor-booking-management/appointments/update-status-of-appointment" scope="session"/>
<c:url var="updateStatusOfAppointmentActionLink"
       value="/online-doctor-booking-management/appointments/update-status-of-appointment-action" scope="session"/>
<c:url var="updateMyStatusOfAppointmentActionLink"
       value="/online-doctor-booking-management/appointments/update-status-of-appointment-action" scope="session">
    <c:param name="userId" value="${userId}"/>
</c:url>
<c:url var="createMyAppointmentActionLink"
       value="/online-doctor-booking-management/appointments/create-appointment-action"
       scope="session">
    <c:param name="userId" value="${userId}"/>
</c:url>

<c:url var="showRolesLink" value="/online-doctor-booking-management/roles/show-roles" scope="session"/>
<c:url var="updateRoleFormLink" value="/online-doctor-booking-management/roles/update-role" scope="session"/>
<c:url var="updateRoleActionLink" value="/online-doctor-booking-management/roles/update-role-action" scope="session"/>

<c:url var="updateProfileLinkForDoctors" value="${showDoctorFormLink}"
       scope="session">
    <c:param name="id" value="${userId}"/>
</c:url>
<c:url var="updateProfileLinkForPatients" value="${showPatientFormLink}"
       scope="session">
    <c:param name="id" value="${userId}"/>
</c:url>