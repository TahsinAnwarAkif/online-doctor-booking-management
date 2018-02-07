package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.RoleService;
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
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.UPDATE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.SHOW_ALL_ROLES_VIEW;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.UPDATE_ROLE_FORM_VIEW;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.ROLES;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.addRedirectAttrsAfterSaveOrUpdate;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.redirectTo;

/**
 * @author anwar
 * @since 2/12/18
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @GetMapping(SHOW_ALL_ROLES_URL)
    private String showAllRoles(HttpServletRequest request,
                                Model model) throws BadRequestException {

        checkAccess(request);

        model.addAttribute(ALL_ROLES.getAttrName(), roleService.findAll());

        return SHOW_ALL_ROLES_VIEW.getUrl();
    }

    @GetMapping(UPDATE_ROLE_FORM_URL)
    private String showUpdateRoleForm(@RequestParam String type,
                                      HttpServletRequest request,
                                      Model model) throws BadRequestException {

        checkAccess(request);

        roleService.checkIfRoleTypeValidAndNotSuperAdmin(type);

        if (!model.containsAttribute(BINDING_RESULT_FOR_ROLE.getAttrName())) {
            model.addAttribute(ROLE.getAttrName(), roleService.findOneByType(type));
            model.addAttribute(ALL_ROLE_ACTIONS.getAttrName(), roleService.findValidActionsByType(type));
        }

        return UPDATE_ROLE_FORM_VIEW.getUrl();
    }

    @PostMapping(UPDATE_ROLE_ACTION_URL)
    private String updateRole(@Valid @ModelAttribute Role role,
                              Errors errors,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request);

        roleService.checkIfTypeAndActionsAreValid(role);

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_FOR_ROLE.getAttrName(), errors);
            return redirectTo(UPDATE_ROLE_FORM_WITH_TYPE_URL + role.getType());
        }

        roleService.update(role);

        addRedirectAttrsAfterSaveOrUpdate(redirectAttributes, role, UPDATE);

        return redirectTo(SHOW_ALL_ROLES_URL);
    }

    private void checkAccess(HttpServletRequest request) throws BadRequestException {
        commonService.throwErrorIfAllActionsInvalid(request, ROLES);
    }
}