package net.therap.onlinedoctorbookingmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.LOGIN_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.LOG_OUT_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.redirectTo;

/**
 * @author anwar
 * @since 2/4/18
 */
@Controller
public class LogoutController {

    @GetMapping(LOG_OUT_URL)
    private String logout(HttpSession session) throws IOException {

        session.invalidate();

        return redirectTo(LOGIN_URL);
    }
}