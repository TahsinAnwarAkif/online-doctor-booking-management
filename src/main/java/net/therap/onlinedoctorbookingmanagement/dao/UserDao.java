package net.therap.onlinedoctorbookingmanagement.dao;

import javafx.util.Pair;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.convertStringListToArray;

/**
 * @author anwar
 * @since 2/7/18
 */
@Repository
public class UserDao {

    @Autowired
    private CommonUtilsForDao commonUtilsForDao;

    public User findOneByUsername(String username) {
        return (User) commonUtilsForDao.findOneByColumnNames(User.class, new Pair<>("username", username));
    }

    public User findOneByPhone(String phone) {
        return (User) commonUtilsForDao.findOneByColumnNames(User.class, new Pair<>("phone", phone));
    }

    public User findOneByEmail(String email) {
        return (User) commonUtilsForDao.findOneByColumnNames(User.class, new Pair<>("email", email));
    }

    public String[] findAllUsernamesByGivenIds(long[] ids) {
        return convertStringListToArray(commonUtilsForDao
                .findSpecificColumnValuesOfEntity(User.class,
                        "username",
                        ids));
    }

    public User findOne(long id) {
        return (User) commonUtilsForDao.findOne(User.class, id);
    }

    @Transactional
    public void saveOrUpdate(DB_OPERATIONS operation, User user) throws BadRequestException {
        commonUtilsForDao.saveOrUpdate(operation, user);
    }

    @Transactional
    public void delete(User... users) {
        commonUtilsForDao.delete(users);
    }
}