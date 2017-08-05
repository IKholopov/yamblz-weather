package com.example.toor.yamblzweather.domain.utils;

import java.util.GregorianCalendar;

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
}
