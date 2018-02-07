package net.therap.onlinedoctorbookingmanagement.utilities.enums;

/**
 * @author anwar
 * @since 2/22/18
 */
public enum ATTRIBUTES {

    USER_ID("userId"),
    USER_NAME("userName"),
    USER_PASSWORD("userPassword"),
    ALL_DOCTORS("allDoctors"),
    ALL_PATIENTS("allPatients"),
    ALL_ROLES("allRoles"),
    ALL_ADMINS("allAdmins"),
    ROLE("role"),
    SELECTED_PATIENT("selectedPatient"),
    IS_A_BACKED_URL("isBackedUrl"),
    IS_A_POST_URL("isPost"),
    ALL_SPECIALTIES("allSpecialties"),
    ALL_ROLE_ACTIONS("allRoleActions"),
    AUTH_ERROR("authError"),
    USER_ROLE_ACTIONS("userRoleActions"),
    ERROR("error"),
    SPECIALTY("specialty"),
    ALL_APPOINTMENTS("allAppointments"),
    SELECTED_DOCTORS_OR_PATIENTS_APPOINTMENTS("selectedAppointments"),
    HISTORY("history"),
    ALERT_NOTIFICATION("alertNotification"),
    DATE_TO_BE_SCHEDULED("dateToBeScheduled"),
    TIME_TO_BE_SCHEDULED("timeToBeScheduled"),
    ALL_STATUS("allStatus"),
    USER("user"),
    SELECTED_APPOINTMENT("selectedAppointment"),
    APPOINTMENT("appointment"),
    USER_ROLE_TYPE("roleType"),
    BINDING_RESULT_FOR_USER("org.springframework.validation.BindingResult.user"),
    BINDING_RESULT_FOR_SPECIALTY("org.springframework.validation.BindingResult.specialty"),
    BINDING_RESULT_FOR_ROLE("org.springframework.validation.BindingResult.role"),
    BINDING_RESULT_FOR_APPOINTMENT("org.springframework.validation.BindingResult.appointment");

    private String attrName;

    ATTRIBUTES(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrName() {
        return this.attrName;
    }
}