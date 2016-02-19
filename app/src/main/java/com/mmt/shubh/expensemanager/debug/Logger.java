/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mmt.shubh.expensemanager.debug;

/**
 * Created by STyagi on 4/4/14.
 */

import android.util.Log;

import com.mmt.shubh.expensemanager.BuildConfig;

/**
 * Wrapper over android logger
 */
public final class Logger {

    private static final String LOG_TAG = "EXManager";

    public static void alwaysLog(String className, String message) {
        Log.i(LOG_TAG, className + " -" + message);
    }

    public static void debug(String className, String message) {
        if (BuildConfig.DEBUG)
            Log.d(LOG_TAG, className + " -" + message);
    }

    public static void info(String className, String message) {
        Log.i(LOG_TAG, className + " -" + message);
    }

    public static void error(String className, String message) {
        Log.e(LOG_TAG, className + " -" + message);
    }

    public static void verbose(String className, String message) {
        Log.v(LOG_TAG, className + " -" + message);
    }

    public static void warn(String className, String message) {
        Log.w(LOG_TAG, className + " -" + message);
    }

    public static void methodStart(String className, String methodName) {
        Log.i(LOG_TAG, className + " - << Start -" + methodName);
    }
    public static void methodEnd(String className, String methodName) {
        Log.i(LOG_TAG, className + " - >> END -" + methodName);
    }


}
