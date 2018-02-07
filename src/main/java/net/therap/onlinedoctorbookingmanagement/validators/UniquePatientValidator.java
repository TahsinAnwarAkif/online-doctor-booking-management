package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.annotations.UniquePatient;
import net.therap.onlinedoctorbookingmanagement.dao.PatientDao;
import net.therap.onlinedoctorbookingmanagement.dao.UserDao;
import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;

/**
 * @author anwar
 * @since 2/13/18
 */
@Service
public class UniquePatientValidator implements ConstraintValidator<UniquePatient, Patient> {

    private String message;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PatientDao patientDao;

    @Override
    public void initialize(UniquePatient constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Patient patient, ConstraintValidatorContext context) {

        if (checkIfAnObjectIsNull(patient)) {
            return true;
        }

        boolean hasError;

        try {
            if (patient.getId() == 0) {

                hasError = !checkIfAnObjectIsNull(patientDao.findOneBySSN(patient.getSsn()));

            } else {

                Patient patientBeforeUpdate = (Patient) userDao.findOne(patient.getId());

                hasError = !patientBeforeUpdate.getSsn().equals(patient.getSsn())
                        && !checkIfAnObjectIsNull(patientDao.findOneBySSN(patient.getSsn()));
            }

            if (hasError) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message.replace(NAME_PARAM.getValue(),
                        "ssn"))
                        .addPropertyNode("ssn")
                        .addConstraintViolation();

                return false;
            }

        } catch (Exception e) {

            return false;
        }

        return true;
    }
}