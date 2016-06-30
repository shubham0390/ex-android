package com.mmt.shubh.expensemanager.login;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;

import dagger.Subcomponent;

/**
 * Created by Subham Tyagi,
 * on 07/Sep/2015,
 * 5:37 PM
 * TODO:Add class comment.
 */
@ConfigPersistent
@Subcomponent(modules = LoginModule.class)
public interface LoginActivityComponent {

    void inject(LoginActivity loginActivity);
}
