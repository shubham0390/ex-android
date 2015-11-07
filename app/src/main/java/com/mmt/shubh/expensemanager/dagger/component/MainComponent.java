package com.mmt.shubh.expensemanager.dagger.component;


import android.content.Context;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.component.api.DaggerObjectGraph;
import com.mmt.shubh.expensemanager.dagger.module.DataModule;
import com.mmt.shubh.expensemanager.dagger.module.MainModule;
import com.mmt.shubh.expensemanager.dagger.module.NetworkModule;

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

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static MainComponent init(ExpenseApplication app) {
            return DaggerMainComponent.builder()
                    .mainModule(new MainModule(app))
                    .build();
        }
    }

    /*ExpenseApplication getExpenseApplication();*/

    Context getApplicationContext();

    Retrofit getRetrofit();

}