package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.annotations.UniqueUsernamePhoneAndEmail;
import net.therap.onlinedoctorbookingmanagement.dao.UserDao;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;

/**
 * @author anwar
 * @since 2/9/18
 */
@Service
public class UniqueUserValidator implements ConstraintValidator<UniqueUsernamePhoneAndEmail, User> {

    private String message;

    @Autowired
    private UserDao userDao;

    @Override
    public void initialize(UniqueUsernamePhoneAndEmail constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {

        if (checkIfAnObjectIsNull(user)) {
            return true;
        }

        String columnName;

        try {
            if (user.getId() == 0) {
                columnName = !checkIfAnObjectIsNull(userDao.findOneByUsername(user.getUsername()))
                        ? "username"
                        : !checkIfAnObjectIsNull(userDao.findOneByPhone(user.getPhone()))
                        ? "phone"
                        : !checkIfAnObjectIsNull(userDao.findOneByEmail(user.getEmail()))
                        ? "email"
                        : "";
            } else {
                User userBeforeUpdate = userDao.findOne(user.getId());
                columnName = !userBeforeUpdate.getUsername().equals(user.getUsername())
                        && !checkIfAnObjectIsNull(userDao.findOneByUsername(user.getUsername()))
                        ? "username"
                        : !userBeforeUpdate.getPhone().equals(user.getPhone())
                        && !checkIfAnObjectIsNull(userDao.findOneByPhone(user.getPhone()))
                        ? "phone"
                        : !userBeforeUpdate.getEmail().equals(user.getEmail())
                        && !checkIfAnObjectIsNull(userDao.findOneByEmail(user.getEmail()))
                        ? "email"
                        : "";
            }

            if (!columnName.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(message.replace(NAME_PARAM.getValue(), columnName))
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