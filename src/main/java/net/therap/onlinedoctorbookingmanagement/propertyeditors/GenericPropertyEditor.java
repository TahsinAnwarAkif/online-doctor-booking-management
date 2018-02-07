package net.therap.onlinedoctorbookingmanagement.propertyeditors;

import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.service.RoleService;
import net.therap.onlinedoctorbookingmanagement.service.SpecialtyService;
import net.therap.onlinedoctorbookingmanagement.service.UserService;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.PROPERTIES_TO_BE_BOUND;

import java.beans.PropertyEditorSupport;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.BAD_REQUEST_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.PROPERTIES_TO_BE_BOUND.ROLE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.PROPERTIES_TO_BE_BOUND.SPECIALTY;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.StringUtils.checkIfAParamIsNullOrEmpty;

/**
 * @author anwar
 * @since 3/9/18
 */
public class GenericPropertyEditor extends PropertyEditorSupport {

    private static final int zeroToAvoidNullPointer = 0;

    private PROPERTIES_TO_BE_BOUND propertyToBeBound;

    private UserService userService;

    private RoleService roleService;

    private SpecialtyService specialtyService;

    public GenericPropertyEditor(UserService userService,
                                 RoleService roleService,
                                 SpecialtyService specialtyService,
                                 PROPERTIES_TO_BE_BOUND propertyToBeBound) {
        this.userService = userService;
        this.roleService = roleService;
        this.specialtyService = specialtyService;
        this.propertyToBeBound = propertyToBeBound;
    }

    @Override
    public String getAsText() {
        return !checkIfAnObjectIsNull(getValue())
                ? (propertyToBeBound.equals(ROLE)
                ? String.valueOf(((Role) getValue()).getType())
                : Long.toString(propertyToBeBound.equals(PROPERTIES_TO_BE_BOUND.DOCTOR)
                ? ((Doctor) getValue()).getId()
                : propertyToBeBound.equals(PROPERTIES_TO_BE_BOUND.PATIENT)
                ? ((Patient) getValue()).getId()
                : propertyToBeBound.equals(SPECIALTY)
                ? ((Specialty) getValue()).getId()
                : zeroToAvoidNullPointer))
                : null;
    }

    @Override
    public void setAsText(String input) {

        if (!checkIfAParamIsNullOrEmpty(input)) {

            try {

                switch (propertyToBeBound) {

                    case ROLE: {
                        roleService.checkIfRoleTypeValidAndNotSuperAdmin(input);
                        setValue(roleService.findOneByType(input));
                        break;
                    }

                    case DOCTOR: {
                        setAsTextForUsers(input, DOMAINS.DOCTOR);
                        break;
                    }

                    case PATIENT: {
                        setAsTextForUsers(input, DOMAINS.PATIENT);
                        break;
                    }

                    case SPECIALTY: {
                        setValue(specialtyService.findOneOnlyIfValid(Long.parseLong(input)));
                        break;
                    }

                    default: {
                        throw new BadRequestException(BAD_REQUEST_ERROR.getMessage());
                    }
                }
            } catch (BadRequestException e) {
                e.printStackTrace();
            }
        }
    }

    private void setAsTextForUsers(String input, DOMAINS domain) throws BadRequestException {
        userService.checkIfUsersValidForGivenDomain(domain, Integer.parseInt(input));
        setValue(userService.findOne(Long.parseLong(input)));
    }
}