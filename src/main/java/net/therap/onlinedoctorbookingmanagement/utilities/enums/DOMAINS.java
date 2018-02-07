package net.therap.onlinedoctorbookingmanagement.utilities.enums;

/**
 * @author anwar
 * @since 2/22/18
 */
public enum DOMAINS {

    USER("User"),
    DOCTOR("Doctor"),
    PATIENT("Patient"),
    SPECIALTY("Specialty"),
    ROLE("role"),
    APPOINTMENT("Appointment"),
    APPOINTMENT_SCHEDULE_GENERATOR("AppointmentScheduleGenerator");

    private String name;

    DOMAINS(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static DOMAINS getEnumByName(String input) {
        for (DOMAINS domain : DOMAINS.values())
            if (input.equals(domain.name)) {
                return domain;
            }
        return null;
    }
}