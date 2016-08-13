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

package com.km2labs.android.spendview;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.core.dagger.component.api.DaggerObjectGraph;
import com.km2labs.spendview.android.R;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Subham Tyagi,
 * on 23/Jul/2015,
 * 8:57 AM
 * TODO:Add class comment.
 */
public class ExpenseApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "OOlmNKupKeuhkuDmZCJBraTPk";
    private static final String TWITTER_SECRET = "br6Qk80BxzqKVaE26Um3eNauPnIR1a8zyH7Pa2ljlrW1iPMVUy";

    private static DaggerObjectGraph graph;
    public static ExpenseApplication instance;
    private Tracker mTracker;

    public static DaggerObjectGraph component() {
        return graph;
    }

    public static ExpenseApplication get(Context context) {
        return (ExpenseApplication) context.getApplicationContext();
    }

    public MainComponent getMainComponent() {
        return (MainComponent) graph;
    }

    public static void buildComponentAndInject() {
        graph = MainComponent.Initializer.init(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentAndInject();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        new InitlizarionTask().execute();
    }

    private class InitlizarionTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(ExpenseApplication.this, new Crashlytics(), new TwitterCore(authConfig), new Digits());
            //generateHashKey();

            Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                    .build());
            AndroidThreeTen.init(ExpenseApplication.instance);
            return null;
        }
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
