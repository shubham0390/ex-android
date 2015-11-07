package com.mmt.shubh.expensemanager.ui.dagger.component;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.ui.activity.LoginActivity;
import com.mmt.shubh.expensemanager.ui.fragment.login.SignInFragment;
import com.mmt.shubh.expensemanager.ui.fragment.login.SignUpFragment;
import com.mmt.shubh.expensemanager.ui.dagger.module.LoginModule;

import dagger.Component;

/**
 * Created by Subham Tyagi,
 * on 07/Sep/2015,
 * 5:37 PM
 * TODO:Add class comment.
 */
@ActivityScope
@Component(dependencies = MainComponent.class,
        modules = LoginModule.class)
public interface LoginActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(SignInFragment signInFragment);

    void inject(SignUpFragment signInFragment);
}
