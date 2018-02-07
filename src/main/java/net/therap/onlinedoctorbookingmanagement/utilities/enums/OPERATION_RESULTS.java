package net.therap.onlinedoctorbookingmanagement.utilities.enums;

/**
 * @author anwar
 * @since 2/22/18
 */
public enum OPERATION_RESULTS {

    USER_NOT_AVAILABLE_ERROR("Username and password did not match!\n"),
    NULL_SELECTION_ERROR("Selected {name} is not present!\n"),
    INVALID_ACTIONS_IN_ROLE_ERROR("Selected actions, {name} are not valid for this role!\n"),
    INVALID_ROLE_TYPE_ERROR("Role type is not valid!\n"),
    BAD_REQUEST_ERROR("Bad Request!\n"),
    BLANK_INPUT_ERROR("Input cannot be empty!\n"),
    DAILY_SCHEDULE_END_ERROR("( schedule_end -  schedule_start ) / estimated_no_of_visits should be greater than or equal to 30 minutes!\n"),
    NOT_AUTHORIZED_ERROR("You are not authorized!"),
    PAGE_NOT_FOUND_ERROR("Error Code: 404, Page Not Found!\n"),
    DOCTOR_ASSOCIATED_WITH_SPECIALTY_ERROR("{name} associated with this specialty!\n"),
    JOINING_SCHEDULE_NOT_UPDATABLE_WHEN_PATIENT_ALREADY_BEEN_APPOINTED_ERROR("Joining schedule cannot be updated as you have pending appointments!\n"),
    REQUEST_PARAM_ERROR("Request Parameter is missing OR if you are deleting, please select at least one!\n"),
    APPOINTMENT_SCHEDULE_NOT_BE_PAST_ERROR("Appointment schedule should not be past!\n"),
    APPOINTMENT_SCHEDULE_NOT_BE_NULL_ERROR("If you are fixing schedule manually, please input both time and date!\n"),
    APPOINTMENT_SCHEDULE_INVALID_FOR_THIS_DOCTOR_ERROR("This doctor does not visit patients at this schedule!\n"),
    APPOINTMENT_SCHEDULE_INVALID_FORMAT_ERROR("date or time is in invalid format!\n");

    private String message;

    OPERATION_RESULTS(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}