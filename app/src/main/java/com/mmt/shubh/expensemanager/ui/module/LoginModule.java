package com.mmt.shubh.expensemanager.ui.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.ActivityScope;
import com.mmt.shubh.expensemanager.ui.presenters.LoginActivityPresenter;
import com.mmt.shubh.expensemanager.ui.presenters.SignInPresenter;
import com.mmt.shubh.expensemanager.ui.presenters.SignUpPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 07/Sep/2015,
 * 5:38 PM
 * TODO:Add class comment.
 */

@Module
public class LoginModule {

    @Provides
    @ActivityScope
    SignInPresenter provideSignInPresenter() {
        return new SignInPresenter();
    }

    @Provides
    @ActivityScope
    SignUpPresenter provideSignUpPresenter(Context context) {
        return new SignUpPresenter(context);
    }

    @Provides
    @ActivityScope
    LoginActivityPresenter provideLoginActivityPresenter(Context context) {
        return new LoginActivityPresenter(context);
    }
}
