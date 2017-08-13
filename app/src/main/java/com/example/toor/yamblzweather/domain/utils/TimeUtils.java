package com.example.toor.yamblzweather.domain.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by igor on 8/3/17.
 */

public class TimeUtils {
    public static long normalizeDate(long time) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time * 1000);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 12);
        calendar.set(GregorianCalendar.MINUTE, 0);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    public static long getCurrentNormalizedDate() {
        return TimeUtils.normalizeDate(new GregorianCalendar().getTimeInMillis() / 1000);
    }

    public static String formatDayShort(long time) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time * 1000);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }

    public static String formatDayAndDateShort(long time) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time * 1000);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) +
                " " +
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }
}
