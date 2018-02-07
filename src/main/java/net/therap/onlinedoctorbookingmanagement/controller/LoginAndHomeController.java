package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.USER_NAME;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.USER_PASSWORD;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.LOGIN_VIEW;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.MENU_VIEW;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.redirectTo;

/**
 * @author anwar
 * @since 2/4/18
 */
@Controller
public class LoginAndHomeController {

    @Autowired
    private CommonService commonService;

    @GetMapping(LOGIN_URL)
    private String showLoginForm(HttpServletRequest request) {

        commonService.pushUrlToHistoryForNonFilteredUrls(request);

        return LOGIN_VIEW.getUrl();
    }

    @PostMapping(LOGIN_ACTION_URL)
    private String validateLoginInput(@RequestParam String userName,
                                      @RequestParam String userPassword,
                                      HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }

        synchronized (session) {
            session.setAttribute(USER_NAME.getAttrName(), userName);
            session.setAttribute(USER_PASSWORD.getAttrName(), userPassword);
            session.setMaxInactiveInterval(30 * 60);
        }

        return redirectTo(MENU_URL);
    }

    @GetMapping(MENU_URL)
    private String showWelcomeMenu() {
        return MENU_VIEW.getUrl();
    }
}