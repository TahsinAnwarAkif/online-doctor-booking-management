package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.dao.AppointmentDao;
import net.therap.onlinedoctorbookingmanagement.domain.*;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.APPOINTMENT_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.text.ParseException;
import java.util.Arrays;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES.DOCTOR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.CUSTOM_ERROR_CODE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.EnumUtils.isEnumValidThenReturnMatchingValue;

/**
 * @author anwar
 * @since 2/15/18
 */
@Service
public class AppointmentValidator {

    @Autowired
    private CommonValidator commonValidator;

    @Autowired
    private AppointmentDao appointmentDao;

    public void checkIfAppointmentIdIsNull(Long id) throws BadRequestException {
        if (checkIfAnObjectIsNull(id)) {
            throw new BadRequestException(REQUEST_PARAM_ERROR.getMessage());
        }
    }

    public void checkIfNonAdminTryingToAccessAdminResources(Long userId) throws BadRequestException {
        if (checkIfAnObjectIsNull(userId)) {
            throw new BadRequestException(NOT_AUTHORIZED_ERROR.getMessage());
        }
    }

    public boolean checkIfBothDateAndTimeAreNotGiven(Appointment appointment) {
        return checkIfAnObjectIsNull(appointment.getDate())
                && checkIfAnObjectIsNull(appointment.getTime());
    }

    public boolean checkIfOneOfDateAndTimeIsNotGiven(Appointment appointment) {
        return checkIfAnObjectIsNull(appointment.getDate())
                ^ checkIfAnObjectIsNull(appointment.getTime());
    }

    public void extractErrorsInDateField(Errors errors) {
        errors.rejectValue("date",
                CUSTOM_ERROR_CODE.getValue(), APPOINTMENT_SCHEDULE_NOT_BE_NULL_ERROR.getMessage());
    }

    public void checkSelectedAppointmentsBelongToThisUser(User user, long... ids) throws BadRequestException {

        Appointment[] appointments = this.areAllAppointmentIdsValid(ids);

        if (user.getRole().getType().equals(DOCTOR)) {

            if (!((Doctor) user).getAppointments().containsAll(Arrays.asList(appointments))) {
                throw new BadRequestException(NOT_AUTHORIZED_ERROR.getMessage());
            }

        } else {

            if (!((Patient) user).getAppointments().containsAll(Arrays.asList(appointments))) {
                throw new BadRequestException(NOT_AUTHORIZED_ERROR.getMessage());
            }
        }
    }

    public Appointment[] areAllAppointmentIdsValid(long... ids) throws BadRequestException {
        return (Appointment[]) commonValidator.areAllEntitiesValidThenReturnAll(new Appointment(), ids);
    }

    public boolean checkIfAutoGeneratedScheduleIsFree(Appointment appointment,
                                                      AppointmentScheduleGenerator generatedSchedule) throws ParseException {
        return !checkIfAnObjectIsNull(appointment,
                appointment.getDoctor(),
                appointment.getPatient())
                && appointment.isDateTimeGenerationTypeAuto()
                && (!checkIfAnObjectIsNull(appointmentDao.findOneByTimeAndDateAndDoctorId(generatedSchedule.getNextTime(),
                generatedSchedule.getNextDate(),
                appointment.getDoctor().getId()))
                || !checkIfAnObjectIsNull(appointmentDao.findOneByTimeAndDateAndPatientId(generatedSchedule.getNextTime(),
                generatedSchedule.getNextDate(),
                appointment.getPatient().getId())));
    }

    public void checkIfStatusIsValid(String status) throws BadRequestException {

        if (checkIfAnObjectIsNull(isEnumValidThenReturnMatchingValue(APPOINTMENT_STATUS.class, status))) {

            throw new BadRequestException(NULL_SELECTION_ERROR.getMessage()
                    .replace(NAME_PARAM.getValue(), status));
        }
    }
}