package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.propertyeditors.GenericPropertyEditor;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.RoleService;
import net.therap.onlinedoctorbookingmanagement.service.SpecialtyService;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.PROPERTIES_TO_BE_BOUND;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.DELETE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS.DOCTOR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.*;

/**
 * @author anwar
 * @since 2/8/18
 */
@Controller
public class DoctorController extends GenericUserController {

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new GenericPropertyEditor(null, roleService,
                null, PROPERTIES_TO_BE_BOUND.ROLE));
        binder.registerCustomEditor(Specialty.class, new GenericPropertyEditor(null, null,
                specialtyService, PROPERTIES_TO_BE_BOUND.SPECIALTY));
    }

    @GetMapping(SHOW_ALL_DOCTORS_URL)
    private String showAllDoctors(Model model) throws BadRequestException {

        return super.showAllUsers(DOCTOR, model);
    }

    @GetMapping(SHOW_DOCTOR_FORM_URL)
    private String showCreateOrUpdateDoctorForm(@RequestParam(required = false) Long id,
                                                HttpServletRequest request,
                                                Model model) throws BadRequestException {

        DB_OPERATIONS operation = commonService.getOperation(id);

        checkAccess(request, id, operation);

        return super.showCreateOrUpdateUserForm(id, request, model, DOCTOR);
    }

    @PostMapping(CREATE_OR_UPDATE_DOCTOR_ACTION_URL)
    private String createOrUpdateDoctor(@Valid @ModelAttribute Doctor doctor,
                                        Errors errors,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes) throws Exception {

        DB_OPERATIONS operation = commonService.getOperation(doctor.getId());

        checkAccess(request, doctor.getId(), operation);

        return super.createOrUpdateUser(operation, DOCTOR, doctor,
                request, errors, redirectAttributes);
    }

    @PostMapping(DELETE_DOCTOR_URL)
    private String deleteDoctors(@RequestParam long[] idsOfDoctorsToBeDeleted,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request, null, DELETE);

        return super.deleteUsers(DOCTOR, idsOfDoctorsToBeDeleted, redirectAttributes);
    }

    private void checkAccess(HttpServletRequest request,
                             Long userId,
                             DB_OPERATIONS operation) throws BadRequestException {

        switch (operation) {

            case UPDATE: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, UPDATE_PROFILE_FOR_DOCTOR, CREATE_OR_UPDATE_DOCTORS_AS_ADMIN);
                commonService.checkIfNonAdminAccessingOtherUsersResource(userId, request);
                break;
            }

            case DELETE: {

                commonService.throwErrorIfAllActionsInvalid(request, DELETE_DOCTORS);
                break;
            }

            default: {
                break;
            }
        }
    }
}