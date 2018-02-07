package net.therap.onlinedoctorbookingmanagement.service;

import net.therap.onlinedoctorbookingmanagement.dao.RoleDao;
import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anwar
 * @since 2/12/18
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleValidator roleValidator;

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public void checkIfRoleTypeValidAndNotSuperAdmin(String type) throws BadRequestException {
        roleValidator.checkIfRoleTypeInvalidOrNotSuperAdmin(type);
    }

    public Role findOneByType(String type) {
        return roleDao.findOneByType(type);
    }

    public Enum[] findValidActionsByType(String type) {
        return roleValidator.findValidActionsBasedOnType(type);
    }

    public void checkIfTypeAndActionsAreValid(Role role) throws BadRequestException {

        roleValidator
                .checkIfRoleTypeInvalidOrNotSuperAdmin(String.valueOf(role.getType()));

        roleValidator.areAllActionsValidForThisRole(role);
    }

    public void update(Role role) throws BadRequestException {
        roleDao.update(role);
    }
}