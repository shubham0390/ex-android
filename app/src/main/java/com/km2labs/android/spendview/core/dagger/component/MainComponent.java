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

package com.km2labs.android.spendview.core.dagger.component;


import android.content.Context;
import android.content.SharedPreferences;

import com.km2labs.android.spendview.App;
import com.km2labs.android.spendview.core.dagger.component.api.DaggerObjectGraph;
import com.km2labs.android.spendview.core.dagger.module.DataModule;
import com.km2labs.android.spendview.core.dagger.module.FragmentModule;
import com.km2labs.android.spendview.core.dagger.module.MainModule;
import com.km2labs.android.spendview.core.dagger.module.NetworkModule;
import com.km2labs.android.spendview.database.api.CategoryDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.login.LoginService;
import com.km2labs.android.spendview.service.rest.service.MemberRestService;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {
        MainModule.class,
        DataModule.class,
        NetworkModule.class
})
public interface MainComponent extends DaggerObjectGraph {

    ConfigPersistentComponentV2 plus();

    Context getApplicationContext();

    SharedPreferences getSharedPreferences();

    BriteDatabase getBriteDatabase();

    AccountDataAdapter getAccountDataAdapter();

    ExpenseDataAdapter getExpenseDataAdapter();

    TransactionDataAdapter getTransactionDataAdapter();

    MemberExpenseDataAdapter getMemberExpenseDataAdapter();

    MemberDataAdapter getMemberDataAdapter();

    ExpenseBookDataAdapter getExpenseBookDataAdapter();

    UserInfoDataAdapter getUserInfoDataAdapter();

    CategoryDataAdapter getCategoryDataAdapter();

    Retrofit getRetrofit();

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

    MemberRestService memberRestService();

    LoginService loginService();

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static MainComponent init(App app) {
            return DaggerMainComponent.builder()
                    .mainModule(new MainModule(app))
                    .dataModule(new DataModule(app))
                    .networkModule(new NetworkModule())
                    .build();
        }
    }

}