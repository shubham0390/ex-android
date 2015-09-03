package com.mmt.shubh.expensemanager.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("daggerdemo", Context.MODE_PRIVATE);
    }


    @Provides
    MemberDataAdapter provideMemberDataAdapter(Application application){
        return new MemberSQLDataAdapter(application);
    }

}
