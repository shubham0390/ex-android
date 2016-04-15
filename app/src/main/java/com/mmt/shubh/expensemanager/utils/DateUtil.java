package com.mmt.shubh.expensemanager.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Subham Tyagi,
 * on 10/Nov/2015,
 * 3:23 PM
 * TODO:Add class comment.
 */
public class DateUtil {
    public static String getLocalizedDate(long time) {

        DateTime dateTime = new DateTime(time);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("hh:mma");

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today " + timeFormatter.print(dateTime);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday ";
        } else {
            return DateTimeFormat.forPattern("MMM d, yyyy").print(time);
        }
    }

    public static long getTimeDifference(long time1, long time2) {
        return Math.abs(time1 - time2);
    }

    public static String getMonthName(int key) {

        switch (key) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return null;
    }

    public static int getMonthCount(long key) {
        DateTime dateTime = new DateTime(key);
        return dateTime.getMonthOfYear();
    }

    public static int getMonthYear(long key) {
        DateTime dateTime = new DateTime(key);
        return dateTime.getYear();
    }
}
