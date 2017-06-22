package com.enfle.spendview.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 22/06/17.
 */

@Module
public class LoginModule {

    @Provides
    FacebookLoginHelper provideFacebookLoginHelper(LoginActivity loginActivity) {
        return new FacebookLoginHelper(loginActivity);
    }

    @Provides
    GoogleLoginHelper provideGoogleLoginHelper(LoginActivity loginActivity) {
        return new GoogleLoginHelper(loginActivity);
    }

    @Provides
    LoginContract.Presenter provideLoginPresenter(FacebookLoginHelper facebookLoginHelper, GoogleLoginHelper googleLoginHelper) {
        return new LoginPresenter(googleLoginHelper, facebookLoginHelper);
    }
}
