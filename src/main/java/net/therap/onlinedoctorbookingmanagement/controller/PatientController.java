package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.propertyeditors.GenericPropertyEditor;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.RoleService;
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
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.FIND;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS.PATIENT;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.*;

/**
 * @author anwar
 * @since 2/11/18
 */
@Controller
public class PatientController extends GenericUserController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new GenericPropertyEditor(null, roleService,
                null, PROPERTIES_TO_BE_BOUND.ROLE));
    }

    @GetMapping(SHOW_ALL_PATIENTS_URL)
    private String showAllPatients(HttpServletRequest request,
                                   Model model) throws BadRequestException {

        checkAccess(request, null, FIND);

        return super.showAllUsers(PATIENT, model);
    }

    @GetMapping(SHOW_PATIENT_FORM_URL)
    private String showCreateOrUpdatePatientForm(@RequestParam(required = false) Long id,
                                                 HttpServletRequest request,
                                                 Model model) throws BadRequestException {

        DB_OPERATIONS operation = commonService.getOperation(id);

        checkAccess(request, id, operation);

        return super.showCreateOrUpdateUserForm(id, request, model, PATIENT);
    }

    @PostMapping(CREATE_OR_UPDATE_PATIENT_ACTION_URL)
    private String createOrUpdatePatient(@Valid @ModelAttribute Patient patient,
                                         Errors errors,
                                         HttpServletRequest request,
                                         RedirectAttributes redirectAttributes) throws Exception {

        DB_OPERATIONS operation = commonService.getOperation(patient.getId());

        checkAccess(request, patient.getId(), operation);

        return super.createOrUpdateUser(operation, PATIENT, patient,
                request, errors, redirectAttributes);
    }

    @PostMapping(DELETE_PATIENT_URL)
    private String deletePatients(@RequestParam long[] idsOfPatientsToBeDeleted,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request, null, DELETE);

        return super.deleteUsers(PATIENT, idsOfPatientsToBeDeleted, redirectAttributes);
    }

    private void checkAccess(HttpServletRequest request,
                             Long userId,
                             DB_OPERATIONS operation) throws BadRequestException {

        switch (operation) {

            case FIND: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, SHOW_PATIENTS);
                break;
            }
            case UPDATE: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, UPDATE_PROFILE_FOR_PATIENT, UPDATE_PATIENTS);
                commonService.checkIfNonAdminAccessingOtherUsersResource(userId, request);
                break;
            }
            case DELETE: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, DELETE_PATIENTS);
                break;
            }
            default: {
                break;
            }
        }
    }
}