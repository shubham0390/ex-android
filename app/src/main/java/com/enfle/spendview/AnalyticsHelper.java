package com.enfle.spendview;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


/**
 * Created by subhamtyagi on 18/01/17.
 */

public class AnalyticsHelper {
    private static final Object LOCK = new Object();
    private static AnalyticsHelper analyticsHelper;
    private Tracker mTracker;

    public static AnalyticsHelper instance(Context context) {
        if (analyticsHelper == null) {
            synchronized (LOCK) {
                if (analyticsHelper == null) {
                    analyticsHelper = new AnalyticsHelper(context);
                }
            }
        }
        return analyticsHelper;
    }

    public AnalyticsHelper(Context context) {

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        mTracker = analytics.newTracker("UA-63640256-1");

        // Provide unhandled exceptions reports. Do that first after creating the tracker
        mTracker.enableExceptionReporting(true);

        // Enable Remarketing, Demographics & Interests reports
        // https://developers.google.com/analytics/devguides/collection/android/display-features
        mTracker.enableAdvertisingIdCollection(true);

        // Enable automatic activity tracking for your app
        mTracker.enableAutoActivityTracking(true);
    }
}
