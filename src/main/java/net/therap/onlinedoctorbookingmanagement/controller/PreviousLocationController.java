package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Stack;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.IS_A_BACKED_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.redirectTo;

/**
 * @author anwar
 * @since 2/26/18
 */
@Controller
public class PreviousLocationController {

    @Autowired
    private CommonService commonService;

    @GetMapping(GO_BACK_URL)
    private String goToPreviousLocation(HttpSession session) {

        Stack<String> urlsOfHistory = commonService.getOrInitHistoryStack(session);

        if (!urlsOfHistory.isEmpty()) {
            urlsOfHistory.pop();
        }

        synchronized (session) {
            session.setAttribute(IS_A_BACKED_URL.getAttrName(), true);
        }

        return redirectTo(urlsOfHistory.isEmpty()
                || urlsOfHistory.peek().contains(LOGIN_URL)
                ? LOG_OUT_URL
                : urlsOfHistory.peek());
    }
}