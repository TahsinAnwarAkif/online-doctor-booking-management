package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.RoleService;
import net.therap.onlinedoctorbookingmanagement.service.SpecialtyService;
import net.therap.onlinedoctorbookingmanagement.service.UserService;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.SAVE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS.DOCTOR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DOMAINS.PATIENT;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES.ADMIN;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.*;

/**
 * @author anwar
 * @since 3//1/18
 */
public class GenericUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private CommonService commonService;

    public String showAllUsers(DOMAINS domain,
                               Model model) {

        boolean doShowAdmins = userService.isUserNeitherDoctorNorPatient(domain);

        model.addAttribute(domain.equals(DOCTOR)
                ? ALL_DOCTORS.getAttrName()
                : domain.equals(PATIENT)
                ? ALL_PATIENTS.getAttrName()
                : ALL_ADMINS.getAttrName(), roleService
                .findOneByType(String.valueOf(doShowAdmins
                        ? ADMIN
                        : domain)).getUsers());

        return domain.equals(DOCTOR)
                ? SHOW_ALL_DOCTORS_VIEW.getUrl()
                : domain.equals(PATIENT)
                ? SHOW_ALL_PATIENTS_VIEW.getUrl()
                : SHOW_ALL_ADMINS_VIEW.getUrl();
    }

    public String showCreateOrUpdateUserForm(Long id,
                                             HttpServletRequest request,
                                             Model model,
                                             DOMAINS domain) throws BadRequestException {

        DB_OPERATIONS operation = commonService.getOperation(id);

        if (id != null) {
            userService.checkIfUsersValidForGivenDomain(domain, id);
        }

        if (!model.containsAttribute(BINDING_RESULT_FOR_USER.getAttrName())) {
            model.addAttribute(USER.getAttrName(), operation.equals(SAVE)
                    ? (domain.equals(DOCTOR)
                    ? new Doctor()
                    : domain.equals(PATIENT)
                    ? new Patient()
                    : new User())
                    : userService.findOne(id));
        }

        model.addAttribute(ALL_SPECIALTIES.getAttrName(), domain.equals(DOCTOR)
                ? specialtyService.findAll()
                : null);
        if (domain.equals(DOCTOR)
                || domain.equals(PATIENT)) {
            commonService.pushUrlToHistoryForNonFilteredUrls(request);
        }

        return domain.equals(DOCTOR)
                ? SHOW_DOCTOR_FORM_VIEW.getUrl()
                : domain.equals(PATIENT)
                ? SHOW_PATIENT_FORM_VIEW.getUrl()
                : SHOW_ADMIN_FORM_VIEW.getUrl();
    }

    public String createOrUpdateUser(DB_OPERATIONS operation,
                                     DOMAINS domain,
                                     @Valid @ModelAttribute User user,
                                     HttpServletRequest request,
                                     Errors errors,
                                     RedirectAttributes redirectAttributes) throws Exception {

        userService.checkIfRoleAndScheduleAreValid(user, errors);

        if (user.getId() != 0) {
            userService.checkIfUsersValidForGivenDomain(domain, user.getId());
        }

        if (errors.hasErrors()) {

            redirectAttributes.addFlashAttribute(BINDING_RESULT_FOR_USER.getAttrName(), errors);

            return redirectTo(operation.equals(SAVE)
                    ? (user instanceof Doctor
                    ? SHOW_DOCTOR_FORM_URL
                    : user instanceof Patient
                    ? SHOW_PATIENT_FORM_URL
                    : SHOW_ADMIN_FORM_URL)
                    : (user instanceof Doctor
                    ? SHOW_DOCTOR_FORM_WITH_ID_URL + user.getId()
                    : user instanceof Patient
                    ? SHOW_PATIENT_FORM_WITH_ID_URL + user.getId()
                    : SHOW_ADMIN_FORM_WITH_ID_URL + user.getId()));
        }

        userService.saveOrUpdate(user, operation);

        String goToUrl = goToUrlAfterCreateOrUpdate(user, request.getSession());

        if (!goToUrl.equals(LOG_OUT_URL)) {
            addRedirectAttrsAfterSaveOrUpdate(redirectAttributes, user, operation);
        }

        return goToUrl;
    }

    public String deleteUsers(DOMAINS domain,
                              @RequestParam long[] ids,
                              RedirectAttributes redirectAttributes) throws BadRequestException {

        User[] users = userService.checkIfUsersValidForGivenDomain(domain, ids);

        String[] usernames = userService.deleteUsersAndGetUsernames(users);

        addRedirectAttrsAfterDelete(redirectAttributes, usernames);

        return redirectTo(domain.equals(DOCTOR)
                ? SHOW_ALL_DOCTORS_URL
                : domain.equals(PATIENT)
                ? SHOW_ALL_PATIENTS_URL
                : SHOW_ALL_ADMINS_URL);
    }

    private String goToUrlAfterCreateOrUpdate(User user, HttpSession session) {

        if (user instanceof Doctor) {

            return redirectTo(commonService
                    .isUserAuthenticatedWithThisAction(session, CREATE_OR_UPDATE_DOCTORS_AS_ADMIN)
                    ? SHOW_ALL_DOCTORS_URL
                    : LOG_OUT_URL);

        } else if (user instanceof Patient) {

            return redirectTo(commonService
                    .isUserAuthenticatedWithThisAction(session, SHOW_PATIENTS)
                    ? SHOW_ALL_PATIENTS_URL
                    : commonService
                    .isUserAuthenticatedWithThisAction(session, UPDATE_PATIENTS)
                    ? MENU_URL
                    : LOG_OUT_URL);

        } else {

            return redirectTo(commonService
                    .isUserAuthenticatedWithThisAction(session, ADMINS)
                    ? SHOW_ALL_ADMINS_URL
                    : LOG_OUT_URL);
        }
    }
}