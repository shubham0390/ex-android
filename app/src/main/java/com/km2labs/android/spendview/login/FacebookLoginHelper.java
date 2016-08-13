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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 12:11 PM
 * TODO:Add class comment.
 */
public class FacebookLoginHelper implements ILoginHelper, FacebookCallback<LoginResult> {

    private Context mContext;

    private CallbackManager mCallbackManager;

    private LoginCallback mCallback;


    private View.OnClickListener mFacebookLoginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn((Activity) mContext);
        }
    };

    public FacebookLoginHelper(Activity applicationContext, LoginCallback iSignUpPresenter) {
        mContext = applicationContext;
        mCallback = iSignUpPresenter;
    }

    public void setUp(Object object) {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
        ((TextView) object).setOnClickListener(mFacebookLoginButtonClickListener);
    }

    @Override
    public void signIn(Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void signOut() {
        LoginManager.getInstance().logOut();
    }

    @Override
    public void revokeAccess() {

    }

    @Override
    public Object getClient() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        mCallbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        mCallback.onSignInComplete(Type.FACEBOOK, accessToken.getToken());
    }

    @Override
    public void onCancel() {
        mCallback.onSignInCanceled();
    }

    @Override
    public void onError(FacebookException e) {
        mCallback.onSignInFailed(e.getMessage());
    }
}
