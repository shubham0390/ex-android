package com.mmt.shubh.expensemanager.dagger;

import android.app.Application;
import android.content.res.Resources;

import com.mmt.shubh.expensemanager.ExpenseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final ExpenseApplication app;

    public MainModule(ExpenseApplication application) {
        app = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    protected Resources provideResources() {
        return app.getResources();
    }
}