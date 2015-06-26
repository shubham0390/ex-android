package com.mmt.shubh.expensemanager.utils;

import android.text.TextUtils;

/**
 * Created by STyagi on 5/3/2014.
 */
public class Validator {

    /**
     * Check if provided string as correct email format or not.
     * It does not check the validity of email address
     *
     * @param emailAdrress
     * @return
     */
    public static boolean isEmailAddressValid(String emailAdrress) {
        // Check for a valid email address.
        if (TextUtils.isEmpty(emailAdrress)) {
            return false;
        }


        return false;
    }
}
