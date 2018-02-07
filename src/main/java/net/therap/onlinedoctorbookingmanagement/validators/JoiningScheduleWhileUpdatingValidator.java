package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.annotations.JoiningScheduleNotUpdatableIfPatientIsAlreadyAppointed;
import net.therap.onlinedoctorbookingmanagement.dao.AppointmentDao;
import net.therap.onlinedoctorbookingmanagement.dao.UserDao;
import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.JOINING_SCHEDULE_NOT_UPDATABLE_WHEN_PATIENT_ALREADY_BEEN_APPOINTED_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.isListEmpty;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.getDateTimeCombinedAsString;

/**
 * @author anwar
 * @since 2/22/18
 */
@Service
public class JoiningScheduleWhileUpdatingValidator
        implements ConstraintValidator<JoiningScheduleNotUpdatableIfPatientIsAlreadyAppointed, Doctor> {

    private String message;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public void initialize(JoiningScheduleNotUpdatableIfPatientIsAlreadyAppointed constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Doctor doctor, ConstraintValidatorContext context) {

        if (checkIfAnObjectIsNull(doctor)
                || doctor.getId() == 0) {
            return true;
        }

        try {

            Doctor doctorBeforeUpdate = (Doctor) userDao.findOne(doctor.getId());

            String scheduleBeforeUpdate = getDateTimeCombinedAsString(doctorBeforeUpdate.getJoiningDate(),
                    doctorBeforeUpdate.getDailyScheduleStart(),
                    doctorBeforeUpdate.getDailyScheduleEnd());

            String targetSchedule = getDateTimeCombinedAsString(doctor.getJoiningDate(),
                    doctor.getDailyScheduleStart(),
                    doctor.getDailyScheduleEnd());

            if (!scheduleBeforeUpdate.equals(targetSchedule)
                    && !isListEmpty(appointmentDao
                    .findAllByDoctorId(doctorBeforeUpdate.getId()))) {

                this.message = JOINING_SCHEDULE_NOT_UPDATABLE_WHEN_PATIENT_ALREADY_BEEN_APPOINTED_ERROR.getMessage();

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(this.message)
                        .addPropertyNode("joiningDate")
                        .addConstraintViolation();

                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}