package com.mmt.shubh.expensemanager.onboarding;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by subhamtyagi on 3/15/16.
 */
@Component(dependencies = MainComponent.class,modules = SplashActivityModule.class)
@ActivityScope
public interface SplashActivityComponent {

    void inject(SplashActivity splashActivity);
}
