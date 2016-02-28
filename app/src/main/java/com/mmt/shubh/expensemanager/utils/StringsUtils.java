package com.mmt.shubh.expensemanager.utils;

/**
 * Created by Subham Tyagi,
 * on 28/Jul/2015,
 * 10:34 AM
 * TODO:Add class comment.
 */
public class StringsUtils {


    public static String getLocalisedAmountString(int amount) {

        String value = "Rs. " + amount;
        return value;
    }

    public static String getLocalisedAmountString(String amountString) {
        try {
            int amount = Integer.parseInt(amountString);
            return getLocalisedAmountString(amount);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid amount " + amountString);
        }
    }

}
