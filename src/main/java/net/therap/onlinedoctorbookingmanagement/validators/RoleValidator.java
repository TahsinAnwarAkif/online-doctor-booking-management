package net.therap.onlinedoctorbookingmanagement.validators;

import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.VALID_ROLE_ACTIONS_FOR_ADMIN;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.VALID_ROLE_ACTIONS_FOR_DOCTOR;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.VALID_ROLE_ACTIONS_FOR_PATIENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.INVALID_ACTIONS_IN_ROLE_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.NOT_AUTHORIZED_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.NAME_PARAM;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.isListEmpty;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.EnumUtils.isEnumValidThenReturnMatchingValue;

/**
 * @author anwar
 * @since 2/14/18
 */
@Service
public class RoleValidator {

    @Autowired
    private CommonValidator commonValidator;

    public void checkIfRoleTypeInvalidOrNotSuperAdmin(String type) throws BadRequestException {

        if (checkIfAnObjectIsNull(isEnumValidThenReturnMatchingValue(ROLE_TYPES.class, type))) {
            commonValidator.throwErrorForThisEntity(new Role());
        }

        if (SUPER_ADMIN.equals(ROLE_TYPES.valueOf(type))) {
            throw new BadRequestException(NOT_AUTHORIZED_ERROR.getMessage());
        }
    }

    public void areAllActionsValidForThisRole(Role role) throws BadRequestException {

        if (isListEmpty(role.getActions())) {
            throwInvalidActionsError(role);
        }

        List<Enum> validActions = Arrays.asList(this.findValidActionsBasedOnType(String.valueOf(role.getType())));

        for (String action : role.getActions()) {

            if (!validActions
                    .contains(role.getType().equals(DOCTOR)
                            ? isEnumValidThenReturnMatchingValue(VALID_ROLE_ACTIONS_FOR_DOCTOR.class, action)
                            : role.getType().equals(PATIENT)
                            ? isEnumValidThenReturnMatchingValue(VALID_ROLE_ACTIONS_FOR_PATIENT.class, action)
                            : role.getType().equals(ADMIN)
                            ? isEnumValidThenReturnMatchingValue(VALID_ROLE_ACTIONS_FOR_ADMIN.class, action)
                            : null)) {
                throwInvalidActionsError(role);
            }
        }
    }

    public Enum[] findValidActionsBasedOnType(String type) {

        ROLE_TYPES roleType = ROLE_TYPES.valueOf(type);

        return roleType.equals(DOCTOR)
                ? VALID_ROLE_ACTIONS_FOR_DOCTOR.values()
                : roleType.equals(PATIENT)
                ? VALID_ROLE_ACTIONS_FOR_PATIENT.values()
                : roleType.equals(ADMIN)
                ? VALID_ROLE_ACTIONS_FOR_ADMIN.values()
                : null;
    }

    private void throwInvalidActionsError(Role role) throws BadRequestException {
        throw new BadRequestException(INVALID_ACTIONS_IN_ROLE_ERROR.getMessage()
                .replace(NAME_PARAM.getValue(),
                        role.getActions().toString()));
    }
}