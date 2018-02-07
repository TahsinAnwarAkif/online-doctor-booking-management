package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

import java.util.List;

/**
 * @author anwar
 * @since 3/1/18
 */
public class CollectionUtils {

    public static Long[] autoBoxLongArray(long[] longs) {

        if (checkIfLongsNullOrEmpty(longs)) {
            return null;
        }

        Long[] wrappedLongs = new Long[longs.length];

        int idx = 0;
        for (long value : longs) {
            wrappedLongs[idx++] = value;
        }

        return wrappedLongs;
    }

    public static boolean checkIfLongsNullOrEmpty(long... longs) {
        return longs == null
                || longs.length == 0;
    }

    public static long[] unboxLongArray(Long[] wrappedLongs) {

        if (checkIfAnObjectIsNull(wrappedLongs)) {
            return null;
        }

        long[] longs = new long[wrappedLongs.length];

        int idx = 0;
        for (Long value : wrappedLongs) {
            longs[idx++] = value;
        }

        return longs;
    }

    public static boolean checkIfAnObjectIsNull(Object... objects) {

        if (objects == null) {
            return true;
        }

        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    public static String[] convertStringListToArray(List<String> strings) {

        if (isListEmpty(strings)) {
            return null;
        }

        String[] stringArray = new String[strings.size()];

        int idx = 0;
        for (String string : strings) {
            stringArray[idx++] = string;
        }
        return stringArray;
    }

    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }
}