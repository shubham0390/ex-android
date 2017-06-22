package com.enfle.spendview.splash;


import dagger.Module;
import dagger.Provides;

@Module()
public class SplashModule {


    @Provides
    SplashModel provideSplashModel() {
        return new SplashModel();
    }

    @Provides
    SplashPresenter provideSplashPresenter(SplashModel splashModel) {
        return new SplashPresenter(splashModel);
    }
}
