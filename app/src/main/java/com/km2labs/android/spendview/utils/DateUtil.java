/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.utils;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Subham Tyagi,
 * on 10/Nov/2015,
 * 3:23 PM
 * TODO:Add class comment.
 */
public class DateUtil {
    public static String getLocalizedDateTime(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today ";
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday ";
        } else {
            return dateTime.format(timeFormatter);
        }
    }

    public static String getLocalizedDate(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today ";
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday ";
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
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

    public static int getMonthCount(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTime.getMonthValue();
    }

    public static int getYear(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTime.getYear();
    }

    public static long getCurrentTimeInMilli() {

        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long toMilliSeconds(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
