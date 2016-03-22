package com.mmt.shubh.expensemanager.onboarding;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 3/15/16.
 */
@Module
public class SplashActivityModule {

    @Provides
    @ActivityScope
    public SplashModel provideSplashModel(UserInfoDataAdapter userInfoDataAdapter, ExpenseBookDataAdapter expenseBookDataAdapter) {
        return new SplashModel(userInfoDataAdapter, expenseBookDataAdapter);
    }

    @Provides
    @ActivityScope
    public SplashActivityPresenter provideSplashActivityPresenter(SplashModel splashModel) {
        return new SplashActivityPresenter(splashModel);
    }

}
