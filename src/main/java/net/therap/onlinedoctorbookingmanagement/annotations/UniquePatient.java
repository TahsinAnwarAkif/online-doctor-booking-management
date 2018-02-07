package net.therap.onlinedoctorbookingmanagement.annotations;

import net.therap.onlinedoctorbookingmanagement.validators.UniquePatientValidator;

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
 * @since 2/13/18
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniquePatientValidator.class)
@Documented
public @interface UniquePatient {

    String message() default "this {name} already exists!\n";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}