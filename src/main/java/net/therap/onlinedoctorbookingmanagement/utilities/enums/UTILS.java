package net.therap.onlinedoctorbookingmanagement.utilities.enums;

/**
 * @author anwar
 * @since 2/22/18
 */
public enum UTILS {

    BLANK_INPUT(""),
    CUSTOM_ERROR_CODE("Custom Error!"),
    SUCCESS_ALERT("{name} {operation} successfully!"),
    NAME_PARAM("{name}"),
    OPERATION_PARAM("{operation}"),
    MINIMUM_TIME_ALLOCATED_FOR_A_PATIENT("30");

    private String value;

    UTILS(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}