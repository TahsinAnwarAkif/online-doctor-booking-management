package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.annotations.TimeAndDateNotPastAndScheduleForDoctorIsValid;
import net.therap.onlinedoctorbookingmanagement.domain.Appointment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.*;

/**
 * @author anwar
 * @since 2/23/18
 */
public class AppointmentTimeAndDateNotPastAndScheduleForDoctorValidator
        implements ConstraintValidator<TimeAndDateNotPastAndScheduleForDoctorIsValid, Appointment> {

    @Override
    public boolean isValid(Appointment appointment, ConstraintValidatorContext context) {

        if (checkIfAnObjectIsNull(appointment,
                appointment.getDoctor(),
                appointment.getPatient(),
                appointment.getDate(),
                appointment.getTime())
                || appointment.getId() != 0) {
            return true;
        }

        String errorMessage = "";

        try {

            if (isScheduleNotGreaterThanCurrentTime(appointment.getDate(), appointment.getTime())) {

                errorMessage = APPOINTMENT_SCHEDULE_NOT_BE_PAST_ERROR.getMessage();

            } else if (!isFirstDateGreaterThanOrEqualToAnother(appointment.getDate(),
                    appointment.getDoctor().getJoiningDate())
                    || !isGivenTimeWithinRange(appointment.getTime(),
                    appointment.getDoctor().getDailyScheduleStart(), appointment.getDoctor().getDailyScheduleEnd())) {

                errorMessage = APPOINTMENT_SCHEDULE_INVALID_FOR_THIS_DOCTOR_ERROR.getMessage();
            }

            if (!errorMessage.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage)
                        .addPropertyNode("date")
                        .addConstraintViolation();
                return false;
            }

        } catch (Exception e) {

            if (e instanceof IllegalArgumentException) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(APPOINTMENT_SCHEDULE_INVALID_FORMAT_ERROR.getMessage())
                        .addPropertyNode("date")
                        .addConstraintViolation();
            }

            return false;
        }
        return true;
    }
}