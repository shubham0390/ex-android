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
    public static String getLocalizedDate(long aLong) {

        DateTime dateTime = new DateTime(aLong);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("hh:mma");

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today " + timeFormatter.print(dateTime);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday " + timeFormatter.print(dateTime);
        } else {
            return DateTimeFormat.forPattern("EEE hh:mma MMM d, yyyy").toString();
        }
    }
}
