package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.propertyeditors.GenericPropertyEditor;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.RoleService;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
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
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS.USER;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.PROPERTIES_TO_BE_BOUND.ROLE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.ADMINS;

/**
 * @author anwar
 * @since 2/12/18
 */
@Controller
public class AdminController extends GenericUserController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, new GenericPropertyEditor(null, roleService,
                null, ROLE));
    }

    @GetMapping(SHOW_ALL_ADMINS_URL)
    private String showAllAdmins(HttpServletRequest request,
                                 Model model) throws BadRequestException {

        checkAccess(request);

        return super.showAllUsers(USER, model);
    }

    @GetMapping(SHOW_ADMIN_FORM_URL)
    private String showCreateOrUpdateAdminForm(@RequestParam(required = false) Long id,
                                               HttpServletRequest request,
                                               Model model) throws BadRequestException {

        checkAccess(request);

        return super.showCreateOrUpdateUserForm(id, request, model, USER);
    }

    @PostMapping(CREATE_OR_UPDATE_ADMIN_ACTION_URL)
    private String createOrUpdateAdmin(@Valid @ModelAttribute User user,
                                       Errors errors,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) throws Exception {

        checkAccess(request);

        DB_OPERATIONS operation = commonService.getOperation(user.getId());

        return super.createOrUpdateUser(operation, USER, user,
                request, errors, redirectAttributes);
    }

    @PostMapping(DELETE_ADMIN_URL)
    private String deleteAdmins(@RequestParam long[] idsOfAdminsToBeDeleted,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request);

        return super.deleteUsers(USER, idsOfAdminsToBeDeleted, redirectAttributes);
    }

    private void checkAccess(HttpServletRequest request) throws BadRequestException {
        commonService.throwErrorIfAllActionsInvalid(request, ADMINS);
    }
}