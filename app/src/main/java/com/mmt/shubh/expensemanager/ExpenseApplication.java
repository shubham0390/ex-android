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

import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mmt.shubh.expensemanager.dagger.api.DaggerObjectGraph;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.database.dataadapters.AbsRealmDataAdapter;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Subham Tyagi,
 * on 23/Jul/2015,
 * 8:57 AM
 * TODO:Add class comment.
 */
public class ExpenseApplication extends Application {
    private Tracker mTracker;

    private static DaggerObjectGraph graph;

    private static ExpenseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        generateHashKey();
        instance = this;
        buildComponentAndInject();
        intiliazeStetho();

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name(AbsRealmDataAdapter.DATABASE_NAME)
                /*.encryptionKey(getKey())*/
                .schemaVersion(AbsRealmDataAdapter.DATABASE_VERSION)
                /*.setModules(new MySchemaModule())
                .migration(new MyMigration())*/
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void intiliazeStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .withFolder(getCacheDir())
                                /*.withEncryptionKey("encrypted.realm", key)*/
                                .withMetaTables()
                                .withDescendingOrder()
                                .withLimit(1000)
                                .databaseNamePattern(Pattern.compile(".+\\.realm"))
                                .build())
                        .build());
    }

    public static DaggerObjectGraph component() {
        return graph;
    }

    public static void buildComponentAndInject() {
        graph = MainComponent.Initializer.init(instance);
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
