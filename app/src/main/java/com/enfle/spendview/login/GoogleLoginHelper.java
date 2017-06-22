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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.enfle.spendview.utils.ResUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.enfle.spendview.R;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 27/Aug/2015,
 * 2:03 PM
 * TODO:Add class comment.
 */
class GoogleLoginHelper implements LoginHelper, GoogleApiClient.OnConnectionFailedListener {

    static final int OUR_REQUEST_CODE = 49404;

    private GoogleApiClient mPlusClient;
    private GoogleSignInAccount mSignInAccount;

    private BehaviorSubject<LoginResponse> mBehaviorSubject ;

    private WeakReference<AppCompatActivity> mActivityWeakReference;
    private Context mContext;

    public GoogleLoginHelper(AppCompatActivity context) {
        Timber.tag(getClass().getName());
        this.mContext = context.getApplicationContext();
        mActivityWeakReference = new WeakReference<>(context);
    }

    @Override
    public void setup() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestIdToken(ResUtils.getString(R.string.google_server_client_id))
                .build();

        mPlusClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(mActivityWeakReference.get(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public Observable<LoginResponse> signIn() {
        mBehaviorSubject = BehaviorSubject.create();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mPlusClient);
        mActivityWeakReference.get().startActivityForResult(signInIntent, OUR_REQUEST_CODE);
        return mBehaviorSubject;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        mBehaviorSubject.onError(new IllegalStateException(result.getErrorMessage()));
        mBehaviorSubject.onCompleted();
    }


    public GoogleApiClient getClient() {
        return mPlusClient;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
        if (result.isSuccess()) {
            mSignInAccount = result.getSignInAccount();
            if (mSignInAccount == null) {
                mBehaviorSubject.onError(new IllegalStateException());
                mBehaviorSubject.onCompleted();
                return;
            }
            LoginResponse loginResponse = new LoginResponse(LoginType.GOOGLE, result.getSignInAccount().getIdToken());
            mBehaviorSubject.onNext(loginResponse);
            mBehaviorSubject.onCompleted();
        } else {
            mBehaviorSubject.onError(new IllegalStateException());
            mBehaviorSubject.onCompleted();
        }
    }

    public GoogleSignInAccount getSignInAccount() {
        return mSignInAccount;
    }
}
