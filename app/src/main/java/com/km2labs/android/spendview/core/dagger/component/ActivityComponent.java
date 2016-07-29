package com.km2labs.android.spendview.core.dagger.component;

import com.km2labs.android.spendview.account.AccountActivity;
import com.km2labs.android.spendview.core.dagger.module.ActivityModule;
import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.home.HomeActivity;
import com.km2labs.android.spendview.login.LoginActivity;
import com.km2labs.android.spendview.member.detail.MemberDetailActivity;
import com.km2labs.android.spendview.expensebook.detail.ExpenseBookActivity;
import com.km2labs.android.spendview.onboarding.SplashActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(HomeActivity homeActivity);

    void inject(AccountActivity accountActivity);

    void inject(ExpenseBookActivity expenseBookActivity);

    void inject(MemberDetailActivity memberDetailActivity);

    void inject(LoginActivity loginActivity);
}
