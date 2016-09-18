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

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.core.dagger.component.api.DaggerObjectGraph;

import java.util.concurrent.atomic.AtomicLong;


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

        new InitlizarionTask().execute();
    }

    private class InitlizarionTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                    .build());
            AndroidThreeTen.init(App.instance);
            return null;
        }
    }
}
