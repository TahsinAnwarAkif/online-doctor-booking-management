package net.therap.onlinedoctorbookingmanagement.service;

import net.therap.onlinedoctorbookingmanagement.dao.AppointmentScheduleGeneratorDao;
import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import net.therap.onlinedoctorbookingmanagement.domain.AppointmentScheduleGenerator;
import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.validators.OtherDoctorValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.UPDATE;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.addADayToGivenDate;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.TimeUtils.addMinutesToGivenTime;

/**
 * @author anwar
 * @since 2/15/18
 */
@Service
public class AppointmentScheduleGeneratorService {

    @Autowired
    private AppointmentScheduleGeneratorDao scheduleGeneratorDao;

    @Autowired
    private OtherDoctorValidators otherDoctorValidators;

    public void generateValidScheduleForDoctorAfterSaving(Doctor doctor, DB_OPERATIONS operation) throws BadRequestException {

        AppointmentScheduleGenerator bookedSchedule = new AppointmentScheduleGenerator(doctor.getDailyScheduleStart(),
                doctor.getJoiningDate(), doctor);

        scheduleGeneratorDao.saveOrUpdate(bookedSchedule, operation);
    }

    public void generateNextScheduleForAutoGeneration(Appointment appointment) throws BadRequestException {

        if (appointment.isDateTimeGenerationTypeAuto()) {

            int minutesToBeAddedForNext = otherDoctorValidators
                    .validateScheduleThenGetAllocatedTime(appointment.getDoctor(), null);

            Date nextDate = appointment.getDate();
            Date nextTime = addMinutesToGivenTime(appointment.getTime(), minutesToBeAddedForNext);
            Date scheduleEndTimeOfDoctor = appointment.getDoctor().getDailyScheduleEnd();

            if (nextTime.equals(scheduleEndTimeOfDoctor)) {
                nextDate = addADayToGivenDate(nextDate);
                nextTime = appointment.getDoctor().getDailyScheduleStart();
            }

            AppointmentScheduleGenerator appointmentScheduleGenerator = new AppointmentScheduleGenerator(nextTime,
                    nextDate, appointment.getDoctor());

            scheduleGeneratorDao.saveOrUpdate(appointmentScheduleGenerator, UPDATE);
        }
    }

    public AppointmentScheduleGenerator findOne(long id) {
        return scheduleGeneratorDao.findOne(id);
    }
}