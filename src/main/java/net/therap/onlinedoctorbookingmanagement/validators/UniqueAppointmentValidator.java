package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.annotations.UniqueAppointment;
import net.therap.onlinedoctorbookingmanagement.dao.AppointmentDao;
import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;

/**
 * @author anwar
 * @since 2/15/18
 */
@Service
public class UniqueAppointmentValidator implements ConstraintValidator<UniqueAppointment, Appointment> {

    private static final Logger logger = Logger.getLogger(UniqueAppointmentValidator.class);

    private String message;

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public void initialize(UniqueAppointment constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.message = constraintAnnotation.message();
    }

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

        String columnName;

        try {
            columnName = !checkIfAnObjectIsNull(appointmentDao
                    .findOneByTimeAndDateAndDoctorId(appointment.getTime(),
                            appointment.getDate(), appointment.getDoctor().getId()))
                    ? "doctor"
                    : !checkIfAnObjectIsNull(appointmentDao
                    .findOneByTimeAndDateAndPatientId(appointment.getTime(),
                            appointment.getDate(), appointment.getPatient().getId()))
                    ? "patient"
                    : "";

            if (!columnName.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message.replace(NAME_PARAM.getValue(), columnName))
                        .addPropertyNode(columnName)
                        .addConstraintViolation();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}