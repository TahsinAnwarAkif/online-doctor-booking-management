package net.therap.onlinedoctorbookingmanagement.utilities.enums;

/**
 * @author anwar
 * @since 2/22/18
 */
public enum MAPPING_VIEWS {

    LOGIN_VIEW("login"),
    MENU_VIEW("online-doctor-booking-management/home"),
    SHOW_ALL_DOCTORS_VIEW("/online-doctor-booking-management/doctors/show-doctors"),
    SHOW_DOCTOR_FORM_VIEW("/show-doctor-form"),
    ERROR_VIEW("/utils/error"),
    SHOW_ALL_PATIENTS_VIEW("/online-doctor-booking-management/patients/show-patients"),
    SHOW_PATIENT_FORM_VIEW("/show-patient-form"),
    SHOW_ALL_ROLES_VIEW("/online-doctor-booking-management/roles/show-roles"),
    SHOW_ALL_SPECIALTIES_VIEW("/online-doctor-booking-management/specialties/show-specialties"),
    SHOW_SPECIALTY_FORM_VIEW("/online-doctor-booking-management/specialties/show-specialty-form"),
    UPDATE_ROLE_FORM_VIEW("/online-doctor-booking-management/roles/update-role"),
    SHOW_ALL_ADMINS_VIEW("/online-doctor-booking-management/admins/show-admins"),
    SHOW_ADMIN_FORM_VIEW("/online-doctor-booking-management/admins/show-admin-form"),
    SHOW_APPOINTMENTS_VIEW("/online-doctor-booking-management/appointments/show-appointments"),
    CREATE_APPOINTMENT_FORM_VIEW("/online-doctor-booking-management/appointments/create-appointment"),
    UPDATE_STATUS_OF_APPOINTMENT_FORM_VIEW("/online-doctor-booking-management/appointments/update-appointment");

    private String url;

    MAPPING_VIEWS(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}