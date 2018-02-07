package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.annotations.UniqueSpecialty;
import net.therap.onlinedoctorbookingmanagement.dao.SpecialtyDao;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;

/**
 * @author anwar
 * @since 2/12/18
 */
@Service
public class UniqueSpecialtyValidator implements ConstraintValidator<UniqueSpecialty, Specialty> {

    private String message;

    @Autowired
    private SpecialtyDao specialtyDao;

    @Override
    public void initialize(UniqueSpecialty constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Specialty specialty, ConstraintValidatorContext context) {

        if (checkIfAnObjectIsNull(specialty)) {
            return true;
        }

        boolean hasError;

        try {
            if (specialty.getId() == 0) {

                hasError = !checkIfAnObjectIsNull(specialtyDao.findOneByName(specialty.getName()));

            } else {

                Specialty specialtyBeforeUpdate = specialtyDao.findOne(specialty.getId());

                hasError = !specialtyBeforeUpdate.getName().equals(specialty.getName())
                        && !checkIfAnObjectIsNull(specialtyDao.findOneByName(specialty.getName()));
            }

            if (hasError) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message.replace(NAME_PARAM.getValue()
                        , "name"))
                        .addPropertyNode("name")
                        .addConstraintViolation();
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}