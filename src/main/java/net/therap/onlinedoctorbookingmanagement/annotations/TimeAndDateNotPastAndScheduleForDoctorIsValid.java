package net.therap.onlinedoctorbookingmanagement.annotations;

import net.therap.onlinedoctorbookingmanagement.validators.AppointmentTimeAndDateNotPastAndScheduleForDoctorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author anwar
 * @since 2/23/18
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AppointmentTimeAndDateNotPastAndScheduleForDoctorValidator.class)
@Documented
public @interface TimeAndDateNotPastAndScheduleForDoctorIsValid {

    String message() default "time or date should not be past, " +
            "or in invalid format, or doctor does not visit at this schedule!\n";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}