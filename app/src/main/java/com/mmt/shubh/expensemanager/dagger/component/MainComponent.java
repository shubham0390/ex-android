package com.mmt.shubh.expensemanager.dagger.component;


import android.content.Context;
import android.content.SharedPreferences;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.component.api.DaggerObjectGraph;
import com.mmt.shubh.expensemanager.dagger.module.DataModule;
import com.mmt.shubh.expensemanager.dagger.module.MainModule;
import com.mmt.shubh.expensemanager.dagger.module.NetworkModule;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.ExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberExpenseDataAdapter;
import com.mmt.shubh.expensemanager.database.api.TransactionDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
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

    Retrofit getRetrofit();

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