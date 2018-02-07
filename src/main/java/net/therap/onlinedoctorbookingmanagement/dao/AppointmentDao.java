package net.therap.onlinedoctorbookingmanagement.dao;

import javafx.util.Pair;
import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * @author anwar
 * @since 2/15/18
 */
@Repository
public class AppointmentDao {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommonUtilsForDao commonUtilsForDao;

    public List<Appointment> findAll() {
        return (List<Appointment>) commonUtilsForDao.findAllByColumnNames(Appointment.class);
    }

    public Appointment findOne(long id) {
        return (Appointment) commonUtilsForDao.findOne(Appointment.class, id);
    }

    public List<Appointment> findAllByDoctorId(long id) {
        return (List<Appointment>) commonUtilsForDao.findAllByColumnNames(Appointment.class,
                new Pair<>("doctor", userDao.findOne(id)));
    }

    public Appointment findOneByTimeAndDateAndDoctorId(Date time, Date date, long doctorId) {
        return (Appointment) commonUtilsForDao.findOneByColumnNames(Appointment.class,
                new Pair<>("time", time),
                new Pair<>("date", date),
                new Pair<>("doctor", userDao.findOne(doctorId)));
    }

    public Appointment findOneByTimeAndDateAndPatientId(Date time, Date date, long patientId) {
        return (Appointment) commonUtilsForDao.findOneByColumnNames(Appointment.class,
                new Pair<>("time", time),
                new Pair<>("date", date),
                new Pair<>("patient", userDao.findOne(patientId)));
    }

    @Transactional
    public void saveOrUpdate(DB_OPERATIONS operation, Appointment appointment) throws BadRequestException {
        commonUtilsForDao.saveOrUpdate(operation, appointment);
    }

    @Transactional
    public void delete(Appointment... appointments) {
        commonUtilsForDao.delete(appointments);
    }
}