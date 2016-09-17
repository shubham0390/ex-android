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

package com.km2labs.android.spendview.core.dagger.module;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import com.km2labs.android.spendview.App;
import com.km2labs.android.spendview.core.dagger.module.api.IMainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule implements IMainModule {

    private final App app;

    public MainModule(App application) {
        app = application;
    }

    @Override
    @Provides
    @Singleton
    public Application provideExpenseApplication() {
        return app;
    }

    @Override
    @Provides
    @Singleton
    public Resources provideResources() {
        return app.getResources();
    }

    @Override
    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return app.getApplicationContext();
    }

    @Override
    @Provides
    @Singleton
    public ContentResolver provideContentResolver() {
        return app.getContentResolver();
    }


}