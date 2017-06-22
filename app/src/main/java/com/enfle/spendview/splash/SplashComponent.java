package com.enfle.spendview.splash;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = SplashModule.class)
public interface SplashComponent extends AndroidInjector<SplashActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SplashActivity> {

    }
}
