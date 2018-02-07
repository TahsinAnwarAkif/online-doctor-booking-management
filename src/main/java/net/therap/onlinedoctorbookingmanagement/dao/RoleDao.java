package net.therap.onlinedoctorbookingmanagement.dao;

import javafx.util.Pair;
import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.UPDATE;

/**
 * @author anwar
 * @since 2/12/18
 */
@Repository
public class RoleDao {

    @Autowired
    private CommonUtilsForDao commonUtilsForDao;

    public List<Role> findAll() {
        return (List<Role>) commonUtilsForDao.findAllByColumnNames(Role.class);
    }

    public Role findOneByType(String type) {
        return (Role) commonUtilsForDao.findOneByColumnNames(Role.class,
                new Pair<>("type", ROLE_TYPES.valueOf(type)));
    }

    @Transactional
    public Role update(Role role) throws BadRequestException {
        commonUtilsForDao.saveOrUpdate(UPDATE, role);
        return role;
    }
}