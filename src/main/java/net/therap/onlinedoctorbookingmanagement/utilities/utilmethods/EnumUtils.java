package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

/**
 * @author anwar
 * @since 2/22/18
 */
public class EnumUtils {

    public static Enum isEnumValidThenReturnMatchingValue(Class<? extends Enum> enumClass, String input) {
        try {
            return Enum.valueOf(enumClass, input);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}