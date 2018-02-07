package net.therap.onlinedoctorbookingmanagement.controller;

import net.therap.onlinedoctorbookingmanagement.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Stack;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.GENERIC_URL_FOR_FILTERED_ONES;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.SHOW_ERROR_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.IS_A_POST_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.MAPPING_VIEWS.ERROR_VIEW;

/**
 * @author anwar
 * @since 3/4/18
 */
@Controller
public class ErrorController {

    @Autowired
    private CommonService commonService;

    @GetMapping(SHOW_ERROR_URL)
    private String showError(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Stack<String> urlsOfHistory = commonService.getOrInitHistoryStack(session);

        boolean isPostUrlCausingError = session.getAttribute(IS_A_POST_URL.getAttrName()) != null
                && (boolean) session.getAttribute(IS_A_POST_URL.getAttrName());

        if (!urlsOfHistory.peek().contains(GENERIC_URL_FOR_FILTERED_ONES)
                || isPostUrlCausingError) {
            commonService.pushUrlToHistoryForNonFilteredUrls(request);
        }

        return ERROR_VIEW.getUrl();
    }
}