package net.therap.onlinedoctorbookingmanagement.utilities.enums;

/**
 * @author anwar
 * @since 3/8/18
 */
public enum BUTTON_ATTRIBUTES {

    SHOW_PATIENTS_BUTTON("showPatientsButton"),
    SHOW_SPECIALTIES_BUTTON("showSpecialtiesButton"),
    SHOW_DOCTORS_PROFILE_BUTTON("showDoctorsProfileButton"),
    SHOW_PATIENTS_PROFILE_BUTTON("showPatientsProfileButton"),
    ROLES_BUTTON("rolesButton"),
    ADMINS_BUTTON("adminsButton"),
    SHOW_APPOINTMENTS_BUTTON("showAppointmentsButton"),
    SHOW_MY_APPOINTMENTS_BUTTON("showMyAppointmentsButton"),
    CREATE_OR_UPDATE_DOCTORS_AS_ADMIN_BUTTON("createOrUpdateDoctorsAsAdminButton"),
    CREATE_OR_UPDATE_PATIENTS_AS_ADMIN_BUTTON("createOrUpdatePatientsAsAdminButton"),
    CREATE_APPOINTMENT_BUTTON("createAppointmentButton"),
    CREATE_MY_APPOINTMENT_BUTTON("createMyAppointmentButton"),
    DELETE_APPOINTMENTS_BUTTON("deleteAppointmentsButton"),
    DELETE_MY_APPOINTMENTS_BUTTON("deleteMyAppointmentsButton"),
    UPDATE_STATUS_OF_APPOINTMENTS_BUTTON("updateStatusOfAppointmentsButton"),
    UPDATE_MY_STATUS_OF_APPOINTMENTS_BUTTON("updateMyStatusOfAppointmentsButton"),
    DELETE_DOCTORS_BUTTON("deleteDoctorsButton"),
    UPDATE_PATIENTS_BUTTON("updatePatientsButton"),
    DELETE_PATIENTS_BUTTON("deletePatientsButton"),
    CREATE_SPECIALTIES_BUTTON("createSpecialtiesButton"),
    UPDATE_SPECIALTIES_BUTTON("updateSpecialtiesButton"),
    DELETE_SPECIALTIES_BUTTON("deleteSpecialtiesButton");

    private String attrName;

    BUTTON_ATTRIBUTES(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrName() {
        return this.attrName;
    }
}