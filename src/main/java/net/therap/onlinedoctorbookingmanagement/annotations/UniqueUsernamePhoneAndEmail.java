package net.therap.onlinedoctorbookingmanagement.annotations;

import net.therap.onlinedoctorbookingmanagement.validators.UniqueUserValidator;

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
 * @since 2/9/18
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueUserValidator.class)
@Documented
public @interface UniqueUsernamePhoneAndEmail {

    String message() default "This {name} already exists!\n";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}