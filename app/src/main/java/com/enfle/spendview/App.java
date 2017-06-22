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

package com.enfle.spendview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.enfle.spendview.core.dagger.DaggerAppComponent;
import com.facebook.stetho.Stetho;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Created by Subham Tyagi,
 * on 23/Jul/2015,
 * 8:57 AM
 * TODO:Add class comment.
 */
public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityDispatchingAndroidInjector;

    public static App instance;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentAndInject();

        AnalyticsHelper.instance(this);

        FirebaseInstanceId instanceId = FirebaseInstanceId.getInstance();
        instanceId.getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("Expense");
        new InitializationTask().execute();
    }

    public void buildComponentAndInject() {
        DaggerAppComponent.builder().
                application(this)
                .build()
                .inject(this);
    }


    private class InitializationTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                    .build());
            return null;
        }
    }
}
