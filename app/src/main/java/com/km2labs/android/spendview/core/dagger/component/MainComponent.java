package com.km2labs.android.spendview.core.dagger.component;


import android.content.Context;
import android.content.SharedPreferences;

import com.km2labs.android.spendview.ExpenseApplication;
import com.km2labs.android.spendview.core.dagger.module.DataModule;
import com.km2labs.android.spendview.database.api.CategoryDataAdapter;
import com.km2labs.android.spendview.service.rest.service.MemberRestService;
import com.km2labs.shubh.expensemanager.core.dagger.component.DaggerMainComponent;
import com.km2labs.android.spendview.core.dagger.component.api.DaggerObjectGraph;
import com.km2labs.android.spendview.core.dagger.module.FragmentModule;
import com.km2labs.android.spendview.core.dagger.module.MainModule;
import com.km2labs.android.spendview.core.dagger.module.NetworkModule;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

@Singleton
@Component(modules = {
        MainModule.class,
        DataModule.class,
        NetworkModule.class
})
public interface MainComponent extends DaggerObjectGraph {

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

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static MainComponent init(ExpenseApplication app) {
            return DaggerMainComponent.builder()
                    .mainModule(new MainModule(app))
                    .dataModule(new DataModule(app))
                    .networkModule(new NetworkModule())
                    .build();
        }
    }

}