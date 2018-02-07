package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.dao.UserDao;
import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.CUSTOM_ERROR_CODE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.PasswordUtils.isPasswordMatched;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.StringUtils.checkIfAParamIsNullOrEmpty;

/**
 * @author anwar
 * @since 2/7/18
 */
@Service
public class OtherUserValidators {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommonValidator commonValidator;

    public User[] areAllUsersValid(long... ids) throws BadRequestException {
        return (User[]) commonValidator.areAllEntitiesValidThenReturnAll(new User(), ids);
    }

    public boolean isUserAuthenticated(HttpSession session) {

        String inputtedUsername = (String) session.getAttribute(USER_NAME.getAttrName());
        String inputtedPassword = (String) session.getAttribute(USER_PASSWORD.getAttrName());
        User foundUser = userDao.findOneByUsername(inputtedUsername);
        boolean isFoundPasswordMatched;

        try {
            isFoundPasswordMatched = isPasswordMatched(inputtedPassword, !checkIfAnObjectIsNull(foundUser)
                    ? foundUser.getPassword()
                    : null);
        } catch (Exception e) {
            return false;
        }

        synchronized (session) {
            session.setAttribute(AUTH_ERROR.getAttrName(),
                    checkIfAParamIsNullOrEmpty(inputtedUsername, inputtedPassword)
                            ? BLANK_INPUT_ERROR.getMessage()
                            : checkIfAnObjectIsNull(foundUser) || !isFoundPasswordMatched
                            ? USER_NOT_AVAILABLE_ERROR.getMessage()
                            : null);
        }

        return checkIfAnObjectIsNull(session.getAttribute(AUTH_ERROR.getAttrName()));
    }

    public boolean isUserOfProperRole(User user, Errors errors) {

        if (user instanceof Doctor
                ? user.getRole().getType().equals(DOCTOR)
                : user instanceof Patient
                ? user.getRole().getType().equals(PATIENT)
                : user.getRole().getType().equals(ADMIN)) {
            return true;
        }

        errors.rejectValue("role",
                CUSTOM_ERROR_CODE.getValue(), INVALID_ROLE_TYPE_ERROR.getMessage());

        return false;
    }

    public void throwErrorIfAllActionsInvalid(HttpServletRequest request,
                                              ROLE_ACTIONS... actions) throws BadRequestException {

        for (ROLE_ACTIONS action : actions) {
            if (this.isUserRoleActionValid(request.getSession(), action)) {
                return;
            }
        }

        throw new BadRequestException(NOT_AUTHORIZED_ERROR.getMessage());
    }

    public boolean isUserRoleActionValid(HttpSession session, ROLE_ACTIONS action) {
        return !checkIfAnObjectIsNull(session.getAttribute(USER_ROLE_ACTIONS.getAttrName()))
                && ((List) session.getAttribute(USER_ROLE_ACTIONS.getAttrName())).contains(String.valueOf(action));
    }

    public boolean isUserSuperAdminOrAdmin(HttpSession session) {
        return session.getAttribute(USER_ROLE_TYPE.getAttrName()).equals(String.valueOf(SUPER_ADMIN))
                || session.getAttribute(USER_ROLE_TYPE.getAttrName()).equals(String.valueOf(ADMIN));
    }

    public void checkIfUserValidForGivenDomain(DOMAINS domain, long... ids) throws BadRequestException {

        String errorInEntity = "";

        for (long id : ids) {

            User user = userDao.findOne(id);

            if (user.getRole().getType().equals(SUPER_ADMIN)) {
                errorInEntity = User.class.getSimpleName();
                break;
            }

            switch (domain) {

                case DOCTOR: {

                    errorInEntity = !(user instanceof Doctor)
                            ? Doctor.class.getSimpleName()
                            : "";

                    break;
                }

                case PATIENT: {

                    errorInEntity = !(user instanceof Patient)
                            ? Patient.class.getSimpleName()
                            : "";
                    break;
                }

                case USER: {

                    errorInEntity = user instanceof Doctor
                            || user instanceof Patient
                            ? User.class.getSimpleName()
                            : "";
                    break;
                }

                default: {
                    break;
                }
            }
        }

        if (!checkIfAParamIsNullOrEmpty(errorInEntity)) {
            throw new BadRequestException(NULL_SELECTION_ERROR.getMessage()
                    .replace(NAME_PARAM.getValue(),
                            errorInEntity));
        }
    }

    public boolean isUserNeitherDoctorNorPatient(DOMAINS domain) {
        return !domain.equals(DOMAINS.DOCTOR)
                && !domain.equals(DOMAINS.PATIENT);
    }
}