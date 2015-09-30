package com.mmt.shubh.expensemanager.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mmt.shubh.expensemanager.dagger.api.IDataModule;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberRealmDataAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule implements IDataModule {

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Override
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("daggerdemo", Context.MODE_PRIVATE);
    }


    @Override
    @Provides
    public MemberDataAdapter provideMemberDataAdapter(Application application){
        return new MemberRealmDataAdapter(application);
    }

}
