package com.enfle.spendview.core.dagger;

import android.app.Activity;

import com.enfle.spendview.login.LoginActivity;
import com.enfle.spendview.login.LoginComponent;
import com.enfle.spendview.splash.SplashActivity;
import com.enfle.spendview.splash.SplashComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by subhamtyagi on 21/06/17.
 */

@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSplashActivity(SplashComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(LoginActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindLoginActivity(LoginComponent.Builder builder);
}
