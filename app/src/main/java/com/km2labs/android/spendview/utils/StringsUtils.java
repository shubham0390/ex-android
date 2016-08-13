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

    public static String getLocalisedAmountString(double expenseAmount) {

        String value = "Rs. " + expenseAmount;
        return value;
    }
}
