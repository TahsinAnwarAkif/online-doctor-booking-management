package net.therap.onlinedoctorbookingmanagement.dao;

import net.therap.onlinedoctorbookingmanagement.domain.AppointmentScheduleGenerator;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author anwar
 * @since 2/14/18
 */
@Repository
public class AppointmentScheduleGeneratorDao {

    @Autowired
    private CommonUtilsForDao commonUtilsForDao;

    public AppointmentScheduleGenerator findOne(long id) {
        return (AppointmentScheduleGenerator) commonUtilsForDao.findOne(AppointmentScheduleGenerator.class, id);
    }

    @Transactional
    public void saveOrUpdate(AppointmentScheduleGenerator scheduleGenerator,
                             DB_OPERATIONS operation) throws BadRequestException {
        commonUtilsForDao.saveOrUpdate(operation, scheduleGenerator);
    }
}