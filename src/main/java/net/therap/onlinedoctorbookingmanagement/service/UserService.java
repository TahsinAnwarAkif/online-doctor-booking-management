package net.therap.onlinedoctorbookingmanagement.service;

import net.therap.onlinedoctorbookingmanagement.dao.UserDao;
import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS;
import net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.PasswordUtils;
import net.therap.onlinedoctorbookingmanagement.validators.OtherDoctorValidators;
import net.therap.onlinedoctorbookingmanagement.validators.OtherUserValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.USER_NAME;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.SAVE;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfLongsNullOrEmpty;

/**
 * @author anwar
 * @since 2/8/18
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OtherUserValidators otherUserValidators;

    @Autowired
    private OtherDoctorValidators otherDoctorValidators;

    @Autowired
    private AppointmentScheduleGeneratorService appointmentScheduleGeneratorService;

    public User[] checkIfUsersValidForGivenDomain(DOMAINS domain, long... ids) throws BadRequestException {

        User[] validUsers = otherUserValidators.areAllUsersValid(ids);

        if (!checkIfLongsNullOrEmpty(ids)
                && !checkIfAnObjectIsNull(validUsers)) {
            otherUserValidators.checkIfUserValidForGivenDomain(domain, ids);
        }

        return validUsers;
    }

    public User findOne(long id) {
        return userDao.findOne(id);
    }

    public User findOneByNameAndPassword(HttpSession session) {
        return otherUserValidators.isUserAuthenticated(session)
                ? userDao.findOneByUsername((String) session.getAttribute(USER_NAME.getAttrName()))
                : null;
    }

    public void checkIfRoleAndScheduleAreValid(User user, Errors errors) {

        if (!errors.hasErrors()
                && otherUserValidators.isUserOfProperRole(user, errors)) {

            if (user instanceof Doctor) {
                otherDoctorValidators
                        .validateScheduleThenGetAllocatedTime((Doctor) user, errors);
            }
        }
    }

    public void saveOrUpdate(User user, DB_OPERATIONS operation) throws Exception {

        if (user instanceof Doctor) {

            Doctor doctorBeforeSaveOrUpdate = (Doctor) userDao.findOne(user.getId());

            user.setPassword(PasswordUtils.getSaltedHash(user.getPassword()));
            userDao.saveOrUpdate(operation, user);

            if (operation.equals(SAVE)
                    || otherDoctorValidators
                    .checkIfUpdatedScheduleIsDifferentThanLastOne(doctorBeforeSaveOrUpdate, (Doctor) user)) {
                appointmentScheduleGeneratorService
                        .generateValidScheduleForDoctorAfterSaving((Doctor) user, operation);
            }

        } else {

            user.setPassword(PasswordUtils.getSaltedHash(user.getPassword()));
            userDao.saveOrUpdate(operation, user);
        }
    }

    public String[] deleteUsersAndGetUsernames(User... users) {

        long[] ids = new long[users.length];

        int idx = 0;

        for (User user : users) {
            ids[idx++] = user.getId();
        }

        String[] usernames = userDao.findAllUsernamesByGivenIds(ids);

        userDao.delete(users);

        return usernames;
    }

    public boolean isUserNeitherDoctorNorPatient(DOMAINS domain) {
        return otherUserValidators.isUserNeitherDoctorNorPatient(domain);
    }
}