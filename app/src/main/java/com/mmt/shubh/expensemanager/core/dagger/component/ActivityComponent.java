package com.mmt.shubh.expensemanager.core.dagger.component;

import com.mmt.shubh.expensemanager.account.AccountActivity;
import com.mmt.shubh.expensemanager.cash.CashActivity;
import com.mmt.shubh.expensemanager.core.dagger.module.ActivityModule;
import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.home.HomeActivity;
import com.mmt.shubh.expensemanager.onboarding.SplashActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    void inject(AccountActivity accountActivity);

    void inject(CashActivity cashActivity);
}
