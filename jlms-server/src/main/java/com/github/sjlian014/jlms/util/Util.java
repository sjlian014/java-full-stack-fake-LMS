package com.github.sjlian014.jlms.util;

import java.util.Date;
import java.util.Calendar;

public class Util {
    public static Date getDate(int year, int month, int day) {
        var cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }
}
