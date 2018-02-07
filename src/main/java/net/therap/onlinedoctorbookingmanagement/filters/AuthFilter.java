package net.therap.onlinedoctorbookingmanagement.filters;

import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import net.therap.onlinedoctorbookingmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.LOGIN_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.BUTTON_ATTRIBUTES.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.CollectionUtils.checkIfAnObjectIsNull;

/**
 * @author anwar
 * @since 1/25/18
 */
public class AuthFilter implements Filter {

    private FilterConfig filterConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        User foundUser = userService.findOneByNameAndPassword(session);
        if (!checkIfAnObjectIsNull(foundUser)) {
            Stack<String> urlsOfHistory = commonService.getOrInitHistoryStack(session);

            boolean isRedirectedUrlNeitherPostNorTheSameNorBacked = commonService
                    .checkIfRedirectedUrlNeitherPostNorTheSame(request, urlsOfHistory)
                    && (session.getAttribute(IS_A_BACKED_URL.getAttrName()) == null
                    || !(boolean) session.getAttribute(IS_A_BACKED_URL.getAttrName()));

            synchronized (session) {

                if (isRedirectedUrlNeitherPostNorTheSameNorBacked) {
                    urlsOfHistory.push(commonService.getFullUrlForGetUrls(request));
                }

                session.setAttribute(IS_A_BACKED_URL.getAttrName(), false);

                session.setAttribute(IS_A_POST_URL.getAttrName(), request.getMethod().equals("POST"));

                session.setAttribute(USER_ID.getAttrName(), foundUser.getId());

                session.setAttribute(USER_ROLE_TYPE.getAttrName(), String.valueOf(foundUser.getRole().getType()));

                session.setAttribute(USER_ROLE_ACTIONS.getAttrName(), foundUser.getRole().getActions());

                setAttributesForDisplayingButtons(session);
            }

            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(LOGIN_URL);
        }
    }

    @Override
    public void destroy() {

    }

    private void setAttributesForDisplayingButtons(HttpSession session) {

        setAttributesForHeaderButtons(session);

        setAttributesForDoctorFormButtons(session);

        setAttributesForPatientFormButtons(session);

        setAttributesForCreateAppointmentFormButtons(session);

        setAttributesForShowAppointmentsPageButtons(session);

        setAttributesForShowDoctorsPageButtons(session);

        setAttributesForShowPatientsPageButtons(session);

        setAttributesForShowSpecialtiesPageButtons(session);
    }

    private void setAttributesForHeaderButtons(HttpSession session) {

        session.setAttribute(SHOW_PATIENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, SHOW_PATIENTS));

        session.setAttribute(SHOW_SPECIALTIES_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, SHOW_SPECIALTIES));

        session.setAttribute(SHOW_DOCTORS_PROFILE_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, UPDATE_PROFILE_FOR_DOCTOR));

        session.setAttribute(SHOW_PATIENTS_PROFILE_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, UPDATE_PROFILE_FOR_PATIENT));

        session.setAttribute(ROLES_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, ROLES));

        session.setAttribute(ADMINS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, ADMINS));

        session.setAttribute(SHOW_APPOINTMENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, SHOW_APPOINTMENTS));

        session.setAttribute(SHOW_MY_APPOINTMENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, SHOW_MY_APPOINTMENTS));
    }

    private void setAttributesForDoctorFormButtons(HttpSession session) {

        session.setAttribute(CREATE_OR_UPDATE_DOCTORS_AS_ADMIN_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, CREATE_OR_UPDATE_DOCTORS_AS_ADMIN));
    }

    private void setAttributesForPatientFormButtons(HttpSession session) {

        session.setAttribute(CREATE_OR_UPDATE_PATIENTS_AS_ADMIN_BUTTON.getAttrName(),
                session.getAttribute(USER_ROLE_ACTIONS.getAttrName()) != null
                        && !((List) session.getAttribute(USER_ROLE_ACTIONS.getAttrName()))
                        .contains(String.valueOf(UPDATE_PROFILE_FOR_PATIENT)));
    }

    private void setAttributesForCreateAppointmentFormButtons(HttpSession session) {

        session.setAttribute(CREATE_APPOINTMENT_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, CREATE_APPOINTMENTS));

        session.setAttribute(CREATE_MY_APPOINTMENT_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, CREATE_MY_APPOINTMENTS));
    }

    private void setAttributesForShowAppointmentsPageButtons(HttpSession session) {

        session.setAttribute(DELETE_APPOINTMENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, DELETE_APPOINTMENTS));

        session.setAttribute(DELETE_MY_APPOINTMENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, DELETE_MY_APPOINTMENTS));

        session.setAttribute(UPDATE_STATUS_OF_APPOINTMENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, UPDATE_APPOINTMENTS));

        session.setAttribute(UPDATE_MY_STATUS_OF_APPOINTMENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, UPDATE_MY_APPOINTMENTS));
    }

    private void setAttributesForShowDoctorsPageButtons(HttpSession session) {

        session.setAttribute(DELETE_DOCTORS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, DELETE_DOCTORS));
    }

    private void setAttributesForShowPatientsPageButtons(HttpSession session) {

        session.setAttribute(UPDATE_PATIENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, UPDATE_PATIENTS));

        session.setAttribute(DELETE_PATIENTS_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, DELETE_PATIENTS));
    }

    private void setAttributesForShowSpecialtiesPageButtons(HttpSession session) {

        session.setAttribute(CREATE_SPECIALTIES_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, CREATE_SPECIALTIES));

        session.setAttribute(UPDATE_SPECIALTIES_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, UPDATE_SPECIALTIES));

        session.setAttribute(DELETE_SPECIALTIES_BUTTON.getAttrName(),
                commonService.isUserAuthenticatedWithThisAction(session, DELETE_SPECIALTIES));
    }
}