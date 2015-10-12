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

package com.mmt.shubh.expensemanager;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.dagger.api.DaggerObjectGraph;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Subham Tyagi,
 * on 23/Jul/2015,
 * 8:57 AM
 * TODO:Add class comment.
 */
public class ExpenseApplication extends Application {
    private static DaggerObjectGraph graph;
    private static ExpenseApplication instance;
    private Tracker mTracker;
    private static Context mContext;

    public static DaggerObjectGraph component() {
        return graph;
    }

    public static void buildComponentAndInject() {
        graph = MainComponent.Initializer.init(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        generateHashKey();
        instance = this;
        mContext = getApplicationContext();
        buildComponentAndInject();
    }

    public static Context getContext(){
        return mContext;
    }
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.getLogger().setLogLevel(1);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void generateHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.mmt.shubh.expensemanager",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
