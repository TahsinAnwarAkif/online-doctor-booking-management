package net.therap.onlinedoctorbookingmanagement.utilities.constants;

/**
 * @author anwar
 * @since 2/7/18
 */
public class RegexPatterns {

    public static final String USERNAME_PATTERN = "^[^\\s]+$";
    public static final String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String PHONE_NO_PATTERN = "^01[0-9]{9}$";
    public static final String SSN_PATTERN = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
}