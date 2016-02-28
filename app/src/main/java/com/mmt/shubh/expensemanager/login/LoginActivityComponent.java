package com.mmt.shubh.expensemanager.login;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;

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
