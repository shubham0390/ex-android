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

package com.enfle.spendview.login;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 12:11 PM
 * TODO:Add class comment.
 */
public class FacebookLoginHelper implements LoginHelper, FacebookCallback<LoginResult> {

    private Activity mContext;

    private CallbackManager mCallbackManager;

    private BehaviorSubject<LoginResponse> mBehaviorSubject;

    FacebookLoginHelper(Activity mContext) {
        this.mContext = mContext;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    @Override
    public void setup() {
    }

    @Override
    public Observable<LoginResponse> signIn() {
        mBehaviorSubject = BehaviorSubject.create();
        LoginManager.getInstance().logInWithReadPermissions(mContext, Arrays.asList("public_profile", "user_friends"));
        return mBehaviorSubject;
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
        LoginResponse loginResponse = new LoginResponse(LoginType.FACEBOOK, accessToken.getToken());
        mBehaviorSubject.onNext(loginResponse);
        mBehaviorSubject.onCompleted();
    }

    @Override
    public void onCancel() {
        mBehaviorSubject.onError(new IllegalStateException());
        mBehaviorSubject.onCompleted();
    }

    @Override
    public void onError(FacebookException e) {
        mBehaviorSubject.onError(new IllegalStateException(e));
        mBehaviorSubject.onCompleted();
    }
}
