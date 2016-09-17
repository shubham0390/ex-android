/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.debug.Logger;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.expenseview.android.R;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 04/Sep/2015,
 * 5:18 PM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter, LoginCallback {

    private final String TAG = getClass().getName();

    private UserModel mUserModel;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    @Inject
    public LoginPresenter(UserModel userModel) {
        mUserModel = userModel;

    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GoogleLoginHelper.OUR_REQUEST_CODE) {
            mGoogleLoginHelper.onActivityResult(requestCode, responseCode, intent);
        } else {
            mFacebookLoginHelper.onActivityResult(requestCode, responseCode, intent);
        }
    }

    public void setupGoogleLogin(SignInButton plusSignInButton, AppCompatActivity activity) {
        mGoogleLoginHelper = new GoogleLoginHelper(activity, this);
        mGoogleLoginHelper.setUp(plusSignInButton);
    }

    public void setupFacebookLogin(TextView faceBookLoginButton, AppCompatActivity activity) {
        mFacebookLoginHelper = new FacebookLoginHelper(activity, this);
        mFacebookLoginHelper.setUp(faceBookLoginButton);
    }

    @Override
    public void onSignInComplete(ILoginHelper.Type type, String token) {
        UserSettings userSettings = UserSettings.getInstance();
        switch (type) {
            case GOOGLE:
                userSettings.setLoginType(LoginType.GOOGLE);
                Logger.debug(TAG, "Google login finished. Fetching User profile");
                break;
            case FACEBOOK:
                userSettings.setLoginType(LoginType.FACEBOOK);
                Logger.debug(TAG, "Facebook login finished. Fetching User profile");
                break;
        }
        getView().showAskMobileScreen();
    }

    @Override
    public void onSignInFailed(String message) {
        getView().hideProgress();
        onError(1);
    }

    @Override
    public void onSignInCanceled() {
        getView().hideProgress();
        onError(1);
    }

    private void onError(int statusCode) {
        if (statusCode == 2)
            getView().showError(R.string.no_internet_connection);
        else {
            getView().showError(R.string.login_failed);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
