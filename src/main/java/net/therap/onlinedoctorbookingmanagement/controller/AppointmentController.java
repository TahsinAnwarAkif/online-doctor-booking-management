package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import net.therap.onlinedoctorbookingmanagement.domain.Doctor;
import net.therap.onlinedoctorbookingmanagement.domain.Patient;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.propertyeditors.GenericPropertyEditor;
import net.therap.onlinedoctorbookingmanagement.service.*;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.APPOINTMENT_STATUS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.PROPERTIES_TO_BE_BOUND;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES.DOCTOR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_TYPES.PATIENT;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.*;

/**
 * @author anwar
 * @since 2/15/18
 */
@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private AppointmentScheduleGeneratorService scheduleGeneratorService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Doctor.class, new GenericPropertyEditor(userService, null,
                null, PROPERTIES_TO_BE_BOUND.DOCTOR));
        binder.registerCustomEditor(Patient.class, new GenericPropertyEditor(userService, null,
                null, PROPERTIES_TO_BE_BOUND.PATIENT));
    }

    @GetMapping(SHOW_ALL_APPOINTMENTS_URL)
    private String showAppointments(@RequestParam(required = false) Long userId,
                                    HttpServletRequest request,
                                    Model model) throws BadRequestException {

        boolean isAdmin = checkAccess(request, userId, FIND, SHOW_APPOINTMENTS);

        if (!isAdmin) {

            User foundUser = userService.findOne(userId);

            model.addAttribute(SELECTED_DOCTORS_OR_PATIENTS_APPOINTMENTS.getAttrName(),
                    foundUser.getRole().getType().equals(DOCTOR)
                            ? ((Doctor) foundUser).getAppointments()
                            : ((Patient) foundUser).getAppointments());

        } else {
            model.addAttribute(ALL_APPOINTMENTS.getAttrName(), appointmentService.findAll());
        }

        return SHOW_APPOINTMENTS_VIEW.getUrl();
    }

    @GetMapping(CREATE_APPOINTMENT_FORM_URL)
    private String showCreateAppointmentForm(@RequestParam(required = false) Long userId,
                                             HttpServletRequest request,
                                             Model model) throws BadRequestException {

        boolean isAdmin = checkAccess(request, userId, SAVE, CREATE_APPOINTMENTS);

        if (!model.containsAttribute(BINDING_RESULT_FOR_APPOINTMENT.getAttrName())) {
            model.addAttribute(APPOINTMENT.getAttrName(), new Appointment());
        }

        if (!isAdmin) {

            model.addAttribute(SELECTED_PATIENT.getAttrName(), userService.findOne(userId));
        } else {

            model.addAttribute(ALL_PATIENTS.getAttrName(),
                    roleService.findOneByType(String.valueOf(PATIENT)).getUsers());
        }

        model.addAttribute(ALL_DOCTORS.getAttrName(),
                roleService.findOneByType(String.valueOf(DOCTOR)).getUsers());

        return CREATE_APPOINTMENT_FORM_VIEW.getUrl();
    }

    @PostMapping(CREATE_APPOINTMENT_ACTION_URL)
    private String createAppointment(@RequestParam(required = false) Long userId,
                                     @Valid @ModelAttribute Appointment appointment,
                                     Errors errors,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) throws BadRequestException, ParseException {

        checkAccess(request, !checkIfAnObjectIsNull(appointment.getPatient())
                ? appointment.getPatient().getId()
                : null, SAVE, CREATE_APPOINTMENTS);

        appointmentService.extractValidTimeAndDateIfBothNotInputted(appointment);
        appointmentService.checkIfBothDateAndTimeGivenForManualInput(appointment, errors);

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_FOR_APPOINTMENT.getAttrName(), errors);
            return userId == null
                    ? redirectTo(CREATE_APPOINTMENT_FORM_URL)
                    : redirectTo(CREATE_APPOINTMENT_FORM_WITH_USERID_URL + userId);
        }

        appointmentService.save(appointment);

        scheduleGeneratorService
                .generateNextScheduleForAutoGeneration(appointment);

        addRedirectAttrsAfterSaveOrUpdate(redirectAttributes, appointment, SAVE);

        return redirectToProperUrlAfterOperation(userId);
    }

    @GetMapping(UPDATE_STATUS_OF_APPOINTMENT_FORM_URL)
    private String showUpdateStatusOfAppointmentForm(@RequestParam Long id,
                                                     @RequestParam(required = false) Long userId,
                                                     HttpServletRequest request,
                                                     Model model) throws BadRequestException {

        checkAccess(request, userId, UPDATE, UPDATE_APPOINTMENTS, id);

        model.addAttribute(ALL_STATUS.getAttrName(), APPOINTMENT_STATUS.values());
        model.addAttribute(SELECTED_APPOINTMENT.getAttrName(), appointmentService.findOneOnlyIfValid(id));

        return UPDATE_STATUS_OF_APPOINTMENT_FORM_VIEW.getUrl();
    }

    @PostMapping(UPDATE_STATUS_OF_APPOINTMENT_ACTION_URL)
    private String updateStatusOfAppointment(@RequestParam Long id,
                                             @RequestParam(required = false) Long userId,
                                             @RequestParam String status,
                                             HttpServletRequest request,
                                             RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request, userId, UPDATE, UPDATE_APPOINTMENTS, id);

        appointmentService.checkIfStatusIsValid(status);

        appointmentService.updateStatus(id, status);

        addRedirectAttrsAfterSaveOrUpdate(redirectAttributes, new Appointment(), UPDATE);

        return redirectToProperUrlAfterOperation(userId);
    }

    @PostMapping(DELETE_APPOINTMENTS_URL)
    private String deleteAppointments(@RequestParam(required = false) Long userId,
                                      @RequestParam long[] idsOfAppointmentsToBeDeleted,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) throws BadRequestException {

        checkAccess(request, userId, DELETE, DELETE_APPOINTMENTS, autoBoxLongArray(idsOfAppointmentsToBeDeleted));

        Appointment[] appointments = appointmentService.checkIfAllAppointmentsValid(idsOfAppointmentsToBeDeleted);

        appointmentService.delete(appointments);

        addRedirectAttrsAfterDelete(redirectAttributes, Appointment.class.getSimpleName());

        return redirectToProperUrlAfterOperation(userId);
    }


    private boolean checkAccess(HttpServletRequest request,
                                Long userId,
                                DB_OPERATIONS operation,
                                ROLE_ACTIONS adminAction,
                                Long... ids) throws BadRequestException {

        switch (operation) {

            case FIND: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, SHOW_APPOINTMENTS, SHOW_MY_APPOINTMENTS);

                break;

            }

            case SAVE: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, CREATE_APPOINTMENTS, CREATE_MY_APPOINTMENTS);

                break;
            }

            case UPDATE: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, UPDATE_APPOINTMENTS, UPDATE_MY_APPOINTMENTS);

                appointmentService.checkIfAppointmentIdIsNull(ids.length != 0 ? ids[0] : null);

                break;
            }
            case DELETE: {

                commonService
                        .throwErrorIfAllActionsInvalid(request, DELETE_APPOINTMENTS, DELETE_MY_APPOINTMENTS);

                break;
            }

            default: {
                break;
            }
        }

        boolean isAdmin = commonService
                .isUserAuthenticatedWithThisAction(request.getSession(), adminAction);

        if (!isAdmin) {
            appointmentService
                    .checkIfNonAdminTryingToAccessNotPermittedResources(request, userId, unboxLongArray(ids));
        }

        return isAdmin;
    }

    private String redirectToProperUrlAfterOperation(Long userId) {
        return redirectTo(userId == null
                ? SHOW_ALL_APPOINTMENTS_URL
                : SHOW_APPOINTMENTS_WITH_USERID_URL + userId);
    }
}