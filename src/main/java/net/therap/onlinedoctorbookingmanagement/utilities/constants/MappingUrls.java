package net.therap.onlinedoctorbookingmanagement.utilities.constants;

/**
 * @author anwar
 * @since 2/8/18
 */
public class MappingUrls {

    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_ACTION_URL = "/login-action";
    public static final String GO_BACK_URL = "/go-back";
    public static final String LOG_OUT_URL = "/logout";
    public static final String SHOW_ERROR_URL = "/error";
    public static final String GENERIC_URL_FOR_FILTERED_ONES = "/online-doctor-booking-management";
    public static final String MENU_URL = GENERIC_URL_FOR_FILTERED_ONES + "/home";

    public static final String SHOW_ALL_DOCTORS_URL = GENERIC_URL_FOR_FILTERED_ONES + "/doctors/show-doctors";
    public static final String SHOW_DOCTOR_FORM_URL = "/show-doctor-form";
    public static final String SHOW_DOCTOR_FORM_WITH_ID_URL = "/show-doctor-form?id=";
    public static final String CREATE_OR_UPDATE_DOCTOR_ACTION_URL = "/create-or-update-doctor-action";
    public static final String DELETE_DOCTOR_URL = GENERIC_URL_FOR_FILTERED_ONES + "/doctors/delete-doctors";

    public static final String SHOW_ALL_ROLES_URL = GENERIC_URL_FOR_FILTERED_ONES + "/roles/show-roles";
    public static final String UPDATE_ROLE_FORM_URL = GENERIC_URL_FOR_FILTERED_ONES + "/roles/update-role";
    public static final String UPDATE_ROLE_FORM_WITH_TYPE_URL = GENERIC_URL_FOR_FILTERED_ONES + "/roles/update-role?type=";
    public static final String UPDATE_ROLE_ACTION_URL = GENERIC_URL_FOR_FILTERED_ONES + "/roles/update-role-action";

    public static final String SHOW_ALL_SPECIALTIES_URL = GENERIC_URL_FOR_FILTERED_ONES + "/specialties/show-specialties";
    public static final String SHOW_SPECIALTY_FORM_URL = GENERIC_URL_FOR_FILTERED_ONES + "/specialties/show-specialty-form";
    public static final String SHOW_SPECIALTY_FORM_WITH_ID_URL = GENERIC_URL_FOR_FILTERED_ONES + "/specialties/show-specialty-form?id=";
    public static final String CREATE_OR_UPDATE_SPECIALTY_ACTION_URL = GENERIC_URL_FOR_FILTERED_ONES + "/specialties/create-or-update-specialty";
    public static final String DELETE_SPECIALTY_URL = GENERIC_URL_FOR_FILTERED_ONES + "/specialties/delete-specialties";

    public static final String SHOW_ALL_ADMINS_URL = GENERIC_URL_FOR_FILTERED_ONES + "/admins/show-admins";
    public static final String SHOW_ADMIN_FORM_URL = GENERIC_URL_FOR_FILTERED_ONES + "/admins/show-admin-form";
    public static final String SHOW_ADMIN_FORM_WITH_ID_URL = GENERIC_URL_FOR_FILTERED_ONES + "/admins/show-admin-form?id=";
    public static final String CREATE_OR_UPDATE_ADMIN_ACTION_URL = GENERIC_URL_FOR_FILTERED_ONES + "/admins/create-or-update-admin-action";
    public static final String DELETE_ADMIN_URL = GENERIC_URL_FOR_FILTERED_ONES + "/admins/delete-admins";

    public static final String SHOW_ALL_PATIENTS_URL = GENERIC_URL_FOR_FILTERED_ONES + "/patients/show-patients";
    public static final String SHOW_PATIENT_FORM_URL = "/show-patient-form";
    public static final String SHOW_PATIENT_FORM_WITH_ID_URL = "/show-patient-form?id=";
    public static final String CREATE_OR_UPDATE_PATIENT_ACTION_URL = "/create-or-update-patient-action";
    public static final String DELETE_PATIENT_URL = GENERIC_URL_FOR_FILTERED_ONES + "/patients/delete-patients";

    public static final String SHOW_ALL_APPOINTMENTS_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/show-appointments";
    public static final String SHOW_APPOINTMENTS_WITH_USERID_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/show-appointments?userId=";
    public static final String CREATE_APPOINTMENT_FORM_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/create-appointment";
    public static final String CREATE_APPOINTMENT_FORM_WITH_USERID_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/create-appointment?userId=";
    public static final String CREATE_APPOINTMENT_ACTION_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/create-appointment-action";
    public static final String DELETE_APPOINTMENTS_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/delete-appointments";
    public static final String UPDATE_STATUS_OF_APPOINTMENT_FORM_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/update-status-of-appointment";
    public static final String UPDATE_STATUS_OF_APPOINTMENT_ACTION_URL = GENERIC_URL_FOR_FILTERED_ONES + "/appointments/update-status-of-appointment-action";
}