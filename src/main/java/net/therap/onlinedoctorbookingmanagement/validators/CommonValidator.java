package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.dao.AppointmentDao;
import net.therap.onlinedoctorbookingmanagement.dao.SpecialtyDao;
import net.therap.onlinedoctorbookingmanagement.dao.UserDao;
import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.USER_ID;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.NULL_SELECTION_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfLongsNullOrEmpty;

/**
 * @author anwar
 * @since 2/10/18
 */
@Service
public class CommonValidator {

    @Autowired
    private SpecialtyDao specialtyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private OtherUserValidators otherUserValidators;

    public Object[] areAllEntitiesValidThenReturnAll(Object entity, long... ids) throws BadRequestException {

        if (checkIfLongsNullOrEmpty(ids)) {
            this.throwErrorForThisEntity(entity);
        }

        DOMAINS domain = DOMAINS.getEnumByName(entity.getClass().getSimpleName());

        switch (domain) {

            case SPECIALTY: {

                Specialty[] specialties = new Specialty[ids.length];

                for (int idx = 0; idx < ids.length; idx++) {

                    if (checkIfAnObjectIsNull(specialtyDao.findOne(ids[idx]))) {
                        this.throwErrorForThisEntity(entity);
                    }

                    specialties[idx] = specialtyDao.findOne(ids[idx]);
                }

                return specialties;
            }

            case APPOINTMENT: {

                Appointment[] appointments = new Appointment[ids.length];

                for (int idx = 0; idx < ids.length; idx++) {

                    if (checkIfAnObjectIsNull(appointmentDao.findOne(ids[idx]))) {
                        this.throwErrorForThisEntity(entity);
                    }

                    appointments[idx] = appointmentDao.findOne(ids[idx]);
                }

                return appointments;
            }

            default: {

                User[] users = new User[ids.length];

                for (int idx = 0; idx < ids.length; idx++) {

                    if (checkIfAnObjectIsNull(userDao.findOne(ids[idx]))) {
                        this.throwErrorForThisEntity(entity);
                    }

                    users[idx] = userDao.findOne(ids[idx]);
                }

                return users;
            }
        }
    }

    public void throwErrorIfAllActionsInvalid(HttpServletRequest request,
                                              ROLE_ACTIONS... actions) throws BadRequestException {
        otherUserValidators.throwErrorIfAllActionsInvalid(request, actions);
    }

    public boolean isUserAccessingHisOwnResources(Long userId, HttpServletRequest request) {
        return request.getSession().getAttribute(USER_ID.getAttrName()).equals(userId);
    }

    public boolean isUserAuthenticatedWithThisRoleAction(HttpSession session, ROLE_ACTIONS action) {
        return otherUserValidators.isUserRoleActionValid(session, action);
    }

    public boolean isUserSuperAdminOrAdmin(HttpSession session) {
        return otherUserValidators.isUserSuperAdminOrAdmin(session);
    }

    public void throwErrorForThisEntity(Object entity) throws BadRequestException {
        throw new BadRequestException(NULL_SELECTION_ERROR.getMessage()
                .replace(NAME_PARAM.getValue(),
                        entity.getClass().getSimpleName()));
    }
}