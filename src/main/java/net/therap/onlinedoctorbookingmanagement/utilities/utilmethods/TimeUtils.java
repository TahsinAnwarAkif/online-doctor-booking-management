package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.util.Date;

import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.DATE_PATTERN;
import static net.therap.onlinedoctorbookingmanagement.utilities.constants.RegexPatterns.TIME_PATTERN;

/**
 * @author anwar
 * @since 2/8/18
 */
public class TimeUtils {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_PATTERN + TIME_PATTERN);
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(DATE_PATTERN);
    private static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern(TIME_PATTERN);

    public static long calculateDiffBetweenTimesInMinutes(Date start, Date end) throws ParseException {
        return (new DateTime(end).getMillis() - new DateTime(start).getMillis()) / (60 * 1000);
    }

    public static boolean isScheduleNotGreaterThanCurrentTime(Date date, Date time) throws ParseException {
        LocalDateTime inputtedDateTime = dateTimeFormatter
                .parseLocalDateTime(getDateTimeCombinedAsString(date, time));

        return inputtedDateTime.isBefore(LocalDateTime.now())
                || inputtedDateTime.isEqual(LocalDateTime.now());
    }

    public static boolean isFirstDateGreaterThanOrEqualToAnother(Date firstInput, Date secondInput) {

        DateTime firstDate = new DateTime(firstInput);
        DateTime secondDate = new DateTime(secondInput);

        return firstDate.isAfter(secondDate)
                || firstDate.isEqual(secondDate);
    }

    public static boolean isGivenTimeWithinRange(Date input, Date low, Date high) {

        DateTime date = new DateTime(input);
        DateTime start = new DateTime(low);
        DateTime end = new DateTime(high);

        return (date.isAfter(start)
                || date.isEqual(start))
                && date.isBefore(end);
    }

    public static Date addADayToCurrentDate() {
        return LocalDate.now().plusDays(1).toDate();
    }

    public static Date addADayToGivenDate(Date input) {
        return new DateTime(input).plusDays(1).toDate();
    }

    public static Date addMinutesToGivenTime(Date input, int minutes) {
        return new DateTime(input).plusMinutes(minutes).toDate();
    }

    public static String getDateTimeCombinedAsString(Date date, Date... times) {

        StringBuilder timeStrings = new StringBuilder();

        for (Date time : times) {
            timeStrings.append(timeFormatter.print(new DateTime(time)));
        }

        return dateFormatter.print(new DateTime(date))
                + timeStrings;
    }
}