package com.enfle.spendview.login;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by subhamtyagi on 21/06/17.
 */

@Subcomponent(modules = LoginModule.class)
public interface LoginComponent extends AndroidInjector<LoginActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<LoginActivity> {
    }
}
