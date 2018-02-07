package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.SpecialtyService;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.SHOW_ALL_SPECIALTIES_VIEW;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.SHOW_SPECIALTY_FORM_VIEW;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.*;

/**
 * @author anwar
 * @since 2/1218
 */
@Controller
public class SpecialtyController {

    private static final Logger logger = Logger.getLogger(SpecialtyController.class);

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private CommonService commonService;

    @GetMapping(SHOW_ALL_SPECIALTIES_URL)
    private String showAllSpecialties(HttpServletRequest request,
                                      Model model) throws BadRequestException {

        checkAccess(request, FIND);

        model.addAttribute(ALL_SPECIALTIES.getAttrName(), specialtyService.findAll());

        return SHOW_ALL_SPECIALTIES_VIEW.getUrl();
    }

    @GetMapping(SHOW_SPECIALTY_FORM_URL)
    private String showCreateOrUpdateSpecialtyForm(@RequestParam(required = false) Long id,
                                                   HttpServletRequest request,
                                                   Model model) throws BadRequestException {

        DB_OPERATIONS operation = commonService.getOperation(id);

        checkAccess(request, operation);

        if (!model.containsAttribute(BINDING_RESULT_FOR_SPECIALTY.getAttrName())) {
            model.addAttribute(SPECIALTY.getAttrName(), operation.equals(SAVE)
                    ? new Specialty()
                    : specialtyService.findOneOnlyIfValid(id));
        }

        return SHOW_SPECIALTY_FORM_VIEW.getUrl();
    }

    @PostMapping(CREATE_OR_UPDATE_SPECIALTY_ACTION_URL)
    private String createOrUpdateSpecialty(@Valid @ModelAttribute Specialty specialty,
                                           Errors errors,
                                           HttpServletRequest request,
                                           RedirectAttributes redirectAttributes) throws BadRequestException {

        DB_OPERATIONS operation = commonService.getOperation(specialty.getId());

        checkAccess(request, operation);

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_FOR_SPECIALTY.getAttrName(), errors);
            return redirectTo(operation.equals(SAVE)
                    ? SHOW_SPECIALTY_FORM_URL
                    : SHOW_SPECIALTY_FORM_WITH_ID_URL + specialty.getId());
        }

        specialtyService.saveOrUpdate(specialty, operation);

        addRedirectAttrsAfterSaveOrUpdate(redirectAttributes, specialty, operation);

        return redirectTo(commonService.isUserAuthenticatedWithThisAction(request.getSession(), SHOW_SPECIALTIES)
                ? SHOW_ALL_SPECIALTIES_URL
                : MENU_URL);
    }

    @PostMapping(DELETE_SPECIALTY_URL)
    private String deleteSpecialty(@RequestParam long[] idsOfSpecialtiesToBeDeleted,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request, DELETE);

        Specialty[] specialties = specialtyService.checkIfAllSpecialtiesValid(idsOfSpecialtiesToBeDeleted);
        specialtyService.isAnySpecialtyAssociatedWithDoctors(specialties);

        String[] deletedSpecialtyNames = specialtyService
                .deleteSpecialtiesAndGetNames(specialties);

        addRedirectAttrsAfterDelete(redirectAttributes, deletedSpecialtyNames);

        return redirectTo(SHOW_ALL_SPECIALTIES_URL);
    }

    private void checkAccess(HttpServletRequest request,
                             DB_OPERATIONS operation) throws BadRequestException {

        commonService.throwErrorIfAllActionsInvalid(request, operation.equals(SAVE)
                ? CREATE_SPECIALTIES
                : operation.equals(UPDATE)
                ? UPDATE_SPECIALTIES
                : operation.equals(DELETE)
                ? DELETE_SPECIALTIES
                : SHOW_SPECIALTIES);
    }
}