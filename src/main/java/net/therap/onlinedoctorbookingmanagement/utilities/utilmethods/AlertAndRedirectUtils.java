package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

import net.therap.onlinedoctorbookingmanagement.domain.Appointment;
import net.therap.onlinedoctorbookingmanagement.domain.Role;
import net.therap.onlinedoctorbookingmanagement.domain.Specialty;
import net.therap.onlinedoctorbookingmanagement.domain.User;
import net.therap.onlinedoctorbookingmanagement.utilities.enums.DB_OPERATIONS;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.ATTRIBUTES.ALERT_NOTIFICATION;
import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.*;
import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.StringUtils.checkIfAParamIsNullOrEmpty;

/**
 * @author anwar
 * @since 3/1/18
 */
public class AlertAndRedirectUtils {

    public static void addRedirectAttrsAfterSaveOrUpdate(RedirectAttributes redirectAttributes,
                                                         Object entity,
                                                         DB_OPERATIONS dbOperations) {
        String operation = String.valueOf(dbOperations);

        redirectAttributes.addFlashAttribute(ALERT_NOTIFICATION.getAttrName(),
                SUCCESS_ALERT.getValue()
                        .replace(NAME_PARAM.getValue(), entity instanceof Appointment
                                ? entity.getClass().getSimpleName()
                                : entity instanceof User
                                ? ((User) entity).getUsername()
                                : entity instanceof Specialty
                                ? ((Specialty) entity).getName()
                                : entity instanceof Role
                                ? String.valueOf(((Role) entity).getType())
                                : "")
                        .replace(OPERATION_PARAM.getValue(), operation.equals("SAVE")
                                ? "created"
                                : operation.equals("UPDATE")
                                ? "updated"
                                : ""));
    }

    public static void addRedirectAttrsAfterDelete(RedirectAttributes redirectAttributes,
                                                   String... deletedUsernames) {
        if (!checkIfAParamIsNullOrEmpty(deletedUsernames)) {
            redirectAttributes.addFlashAttribute(ALERT_NOTIFICATION.getAttrName(),
                    SUCCESS_ALERT.getValue()
                            .replace(NAME_PARAM.getValue(), String.join(",", deletedUsernames))
                            .replace(OPERATION_PARAM.getValue(), "deleted"));
        }
    }

    public static String redirectTo(String url) {
        return "redirect:" + url;
    }
}