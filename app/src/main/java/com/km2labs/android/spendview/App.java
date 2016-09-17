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

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.core.dagger.component.api.DaggerObjectGraph;
import com.km2labs.expenseview.android.R;
import com.urbanairship.UAirship;

import java.util.concurrent.atomic.AtomicLong;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Subham Tyagi,
 * on 23/Jul/2015,
 * 8:57 AM
 * TODO:Add class comment.
 */
public class App extends Application {

    public static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static DaggerObjectGraph graph;
    public static App instance;

    private Tracker mTracker;

    public static DaggerObjectGraph component() {
        return graph;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static MainComponent getAppComponent() {
        return (MainComponent) graph;
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

        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        FirebaseInstanceId instanceId = FirebaseInstanceId.getInstance(firebaseApp);
        instanceId.getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("Expense");
        UAirship.takeOff(this, airship -> {
            airship.getPushManager().setUserNotificationsEnabled(true);
        });
        new InitlizarionTask().execute();
    }

    private class InitlizarionTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Fabric.with(App.this, new Crashlytics());

            Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                    .build());
            AndroidThreeTen.init(App.instance);
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
}
