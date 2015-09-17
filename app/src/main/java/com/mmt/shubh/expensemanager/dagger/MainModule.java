package com.mmt.shubh.expensemanager.dagger;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.api.IMainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule implements IMainModule {

    private final ExpenseApplication app;

    public MainModule(ExpenseApplication application) {
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