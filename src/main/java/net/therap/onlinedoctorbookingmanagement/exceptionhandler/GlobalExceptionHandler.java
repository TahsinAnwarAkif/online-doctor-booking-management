package net.therap.onlinedoctorbookingmanagement.exceptionhandler;

import org.apache.log4j.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.MappingUrls.SHOW_ERROR_URL;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.PAGE_NOT_FOUND_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.REQUEST_PARAM_ERROR;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.AlertAndRedirectUtils.redirectTo;

/**
 * @author anwar
 * @since 2/10/18
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    private String defaultErrorHandler(Exception e, RedirectAttributes redirectAttributes) throws Exception {

        redirectAttributes.addFlashAttribute(ERROR.getAttrName(),
                e instanceof MissingServletRequestParameterException
                        ? REQUEST_PARAM_ERROR.getMessage()
                        : e instanceof NoHandlerFoundException
                        ? PAGE_NOT_FOUND_ERROR.getMessage()
                        : e.getMessage());

        return redirectTo(SHOW_ERROR_URL);
    }
}