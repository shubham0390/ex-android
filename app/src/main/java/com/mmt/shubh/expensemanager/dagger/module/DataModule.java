package com.mmt.shubh.expensemanager.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mmt.shubh.expensemanager.dagger.module.api.IDataModule;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule implements IDataModule {

    @Override
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("daggerdemo", Context.MODE_PRIVATE);
    }


    @Override
    @Provides
    public MemberDataAdapter provideMemberDataAdapter(Application application) {
        return new MemberSQLDataAdapter(application);
    }

}
