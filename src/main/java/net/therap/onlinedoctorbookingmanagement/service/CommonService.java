package net.therap.onlinedoctorbookingmanagement.service;

import net.therap.onlinedoctorbookingmanagement.exceptions.BadRequestException;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.ROLE_ACTIONS;
import net.therap.onlinedoctorbookingmanagement.validators.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Stack;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.HISTORY;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.SAVE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS.UPDATE;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.NOT_AUTHORIZED_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.BLANK_INPUT;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.StringUtils.checkIfAParamIsNullOrEmpty;

/**
 * @author anwar
 * @since 2/20/18
 */
@Service
public class CommonService {

    @Autowired
    private CommonValidator commonValidator;

    public void pushUrlToHistoryForNonFilteredUrls(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Stack<String> urlsOfHistory = this.getOrInitHistoryStack(session);

        synchronized (session) {

            if (urlsOfHistory.isEmpty()
                    || this.checkIfRedirectedUrlNeitherPostNorTheSame(request, urlsOfHistory)) {
                urlsOfHistory.push(this.getFullUrlForGetUrls(request));
            }

            session.setAttribute(HISTORY.getAttrName(), urlsOfHistory);
        }
    }

    public Stack<String> getOrInitHistoryStack(HttpSession session) {
        return session.getAttribute(HISTORY.getAttrName()) != null
                ? (Stack<String>) session.getAttribute(HISTORY.getAttrName())
                : new Stack<>();
    }

    public boolean checkIfRedirectedUrlNeitherPostNorTheSame(HttpServletRequest request, Stack<String> urlsOfHistory) {
        return !this.getFullUrlForGetUrls(request).equals(BLANK_INPUT.getValue())
                && !this.getFullUrlForGetUrls(request).equals(!urlsOfHistory.isEmpty()
                ? urlsOfHistory.peek()
                : null);
    }

    public String getFullUrlForGetUrls(HttpServletRequest request) {

        if (request.getMethod().equals("GET")) {

            String requestUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();

            return checkIfAParamIsNullOrEmpty(queryString)
                    ? requestUrl
                    : requestUrl.concat("?").concat(queryString);

        }
        return BLANK_INPUT.getValue();
    }

    public void throwErrorIfAllActionsInvalid(HttpServletRequest request,
                                              ROLE_ACTIONS... actions) throws BadRequestException {
        commonValidator.throwErrorIfAllActionsInvalid(request, actions);
    }

    public void checkIfNonAdminAccessingOtherUsersResource(Long userId,
                                                           HttpServletRequest request) throws BadRequestException {
        if (userId != null
                && !commonValidator.isUserSuperAdminOrAdmin(request.getSession())
                && !commonValidator.isUserAccessingHisOwnResources(userId, request)) {
            throw new BadRequestException(NOT_AUTHORIZED_ERROR.getMessage());
        }
    }

    public boolean isUserAuthenticatedWithThisAction(HttpSession session,
                                                     ROLE_ACTIONS action) {
        return commonValidator.isUserAuthenticatedWithThisRoleAction(session, action);
    }

    public DB_OPERATIONS getOperation(Long id) {
        return id == null || id == 0 ? SAVE : UPDATE;
    }
}