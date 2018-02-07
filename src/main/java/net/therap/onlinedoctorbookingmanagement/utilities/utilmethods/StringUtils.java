package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.UTILS.BLANK_INPUT;

/**
 * @author anwar
 * @since 2/8/18
 */
public class StringUtils {

    public static boolean checkIfAParamIsNullOrEmpty(String... params) {

        if (params == null) {
            return true;
        }

        for (String param : params) {
            if (param == null || BLANK_INPUT.getValue().equals(param)) {
                return true;
            }
        }

        return false;
    }
}