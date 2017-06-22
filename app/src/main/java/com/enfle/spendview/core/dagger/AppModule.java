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

package com.enfle.spendview.core.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.enfle.spendview.AnalyticsHelper;
import com.enfle.spendview.login.LoginComponent;
import com.enfle.spendview.splash.SplashComponent;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {
        SplashComponent.class,
        LoginComponent.class})
public class AppModule {

    private static final String PREFERENCE_NAME = "expense_manager";

    @Provides
    public Context provideContext(Application app) {
        return app.getApplicationContext();
    }

    @Provides
    public AnalyticsHelper provideAnalyticsHelper(Application app) {
        return new AnalyticsHelper(app);
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }
}