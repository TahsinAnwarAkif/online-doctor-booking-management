package net.therap.onlinedoctorbookingmanagement.service;

import net.therap.onlinedoctorbookingmanagement.dao.AppointmentDao;
import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import net.therap.onlinedoctorbookingmanagement.domain.AppointmentScheduleGenerator;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.APPOINTMENT_STATUS;
import net.therap.onlinedoctorbookingmanagement.validators.AppointmentValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.DATE_TO_BE_SCHEDULED;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.TIME_TO_BE_SCHEDULED;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.SAVE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.UPDATE;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfLongsNullOrEmpty;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.addADayToCurrentDate;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.isScheduleNotGreaterThanCurrentTime;

/**
 * @author anwar
 * @since 2/15/18
 */
@Service
public class AppointmentService {

    private static final Logger logger = Logger.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private AppointmentValidator appointmentValidator;

    @Autowired
    private AppointmentScheduleGeneratorService scheduleGeneratorService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    public void checkIfNonAdminTryingToAccessNotPermittedResources(HttpServletRequest request,
                                                                   Long userId,
                                                                   long... appointmentIds) throws BadRequestException {

        appointmentValidator.checkIfNonAdminTryingToAccessAdminResources(userId);

        commonService.checkIfNonAdminAccessingOtherUsersResource(userId, request);

        if (!checkIfLongsNullOrEmpty(appointmentIds)) {

            User foundUser = userService.findOne(userId);

            appointmentValidator.checkSelectedAppointmentsBelongToThisUser(foundUser, appointmentIds);
        }
    }

    public void checkIfAppointmentIdIsNull(Long id) throws BadRequestException {
        appointmentValidator.checkIfAppointmentIdIsNull(id);
    }

    public void extractValidTimeAndDateIfBothNotInputted(Appointment appointment) throws BadRequestException, ParseException {

        if (appointmentValidator.checkIfBothDateAndTimeAreNotGiven(appointment)) {

            appointment.setDateTimeGenerationTypeAuto(true);

            extractTimeAndDateFromGeneratorIfDoctorIsValid(appointment);
        }
    }

    public void checkIfBothDateAndTimeGivenForManualInput(Appointment appointment, Errors errors) {

        if (!appointment.isDateTimeGenerationTypeAuto()
                && appointmentValidator.checkIfOneOfDateAndTimeIsNotGiven(appointment)) {

            appointmentValidator.extractErrorsInDateField(errors);
        }
    }

    public void updateStatus(long id, String status) throws BadRequestException {

        Appointment appointment = this.findOneOnlyIfValid(id);

        appointment.setStatus(APPOINTMENT_STATUS.valueOf(status));

        appointmentDao.saveOrUpdate(UPDATE, appointment);
    }

    public Appointment findOneOnlyIfValid(long id) throws BadRequestException {
        return appointmentValidator.areAllAppointmentIdsValid(id)[0];
    }

    public void checkIfStatusIsValid(String status) throws BadRequestException {
        appointmentValidator.checkIfStatusIsValid(status);
    }

    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    public void save(Appointment appointment) throws BadRequestException {
        appointmentDao.saveOrUpdate(SAVE, appointment);
    }

    public void delete(Appointment[] appointments) {
        appointmentDao.delete(appointments);
    }

    public Appointment[] checkIfAllAppointmentsValid(long[] ids) throws BadRequestException {
        return appointmentValidator.areAllAppointmentIdsValid(ids);
    }

    private void extractTimeAndDateFromGeneratorIfDoctorIsValid(Appointment appointment) throws BadRequestException, ParseException {

        if (!checkIfAnObjectIsNull(appointment.getDoctor(),
                appointment.getPatient())) {

            AppointmentScheduleGenerator foundSchedule = scheduleGeneratorService.findOne(appointment.getDoctor().getId());

            foundSchedule = isScheduleNotGreaterThanCurrentTime(foundSchedule.getNextDate(), foundSchedule.getNextTime())
                    ? new AppointmentScheduleGenerator(appointment.getDoctor().getDailyScheduleStart(),
                    addADayToCurrentDate(), appointment.getDoctor())
                    : foundSchedule;

            Map<String, Date> allocatedDateAndTime = extractFreeDateAndTimeToBeScheduled(appointment, foundSchedule);
            Date dateToBeScheduled = allocatedDateAndTime.get(DATE_TO_BE_SCHEDULED.getAttrName());
            Date timeToBeScheduled = allocatedDateAndTime.get(TIME_TO_BE_SCHEDULED.getAttrName());

            appointment.setDate(dateToBeScheduled);
            appointment.setTime(timeToBeScheduled);
        }
    }

    private Map<String, Date> extractFreeDateAndTimeToBeScheduled(Appointment appointment,
                                                                  AppointmentScheduleGenerator schedule) throws BadRequestException, ParseException {

        Map<String, Date> result = new HashMap<>();

        Date dateToBeScheduled = schedule.getNextDate();
        Date timeToBeScheduled = schedule.getNextTime();

        while (appointmentValidator.checkIfAutoGeneratedScheduleIsFree(appointment, schedule)) {

            appointment.setDate(dateToBeScheduled);
            appointment.setTime(timeToBeScheduled);

            scheduleGeneratorService
                    .generateNextScheduleForAutoGeneration(appointment);

            schedule = scheduleGeneratorService.findOne(appointment.getDoctor().getId());

            dateToBeScheduled = schedule.getNextDate();
            timeToBeScheduled = schedule.getNextTime();
        }

        result.put(DATE_TO_BE_SCHEDULED.getAttrName(), dateToBeScheduled);
        result.put(TIME_TO_BE_SCHEDULED.getAttrName(), timeToBeScheduled);

        return result;
    }
}