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
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.km2labs.android.spendview.utils.ResUtils;
import com.km2labs.expenseview.android.R;

import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 27/Aug/2015,
 * 2:03 PM
 * TODO:Add class comment.
 */
class GoogleLoginHelper implements ILoginHelper, GoogleApiClient.OnConnectionFailedListener {

    static final int OUR_REQUEST_CODE = 49404;

    private GoogleSignInOptions gso;
    private GoogleApiClient mPlusClient;
    private LoginCallback mCallback;
    private WeakReference<AppCompatActivity> mActivityWeakReference;
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;

    private View.OnClickListener mGoogleLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Timber.i("User choose Google Login");
            GoogleLoginHelper.this.signIn(mActivityWeakReference.get());
        }
    };

    GoogleLoginHelper(AppCompatActivity context, LoginCallback loginCallback) {
        Timber.tag(getClass().getName());
        mActivityWeakReference = new WeakReference<>(context);
        mCallback = loginCallback;
        mContext = context.getApplicationContext();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile().requestEmail().requestIdToken(ResUtils.getString(R.string.google_server_client_id)).build();
        mPlusClient = new GoogleApiClient.Builder(context).enableAutoManage(context, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    public void setUp(Object plusSignInButton) {
        SignInButton signInButton = (SignInButton) plusSignInButton;
        if (!supportsGooglePlayServices()) {
            signInButton.setVisibility(View.GONE);
        } else {
            signInButton.setStyle(SignInButton.SIZE_WIDE, SignInButton.COLOR_LIGHT);
            signInButton.setScopes(gso.getScopeArray());
            signInButton.setOnClickListener(mGoogleLoginClickListener);
        }
    }

    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext) == ConnectionResult.SUCCESS;
    }

    /**
     * Try to sign in the user.
     *
     * @param activity
     */
    @Override
    public void signIn(Activity activity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mPlusClient);
        activity.startActivityForResult(signInIntent, OUR_REQUEST_CODE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // TODO: 2/19/16 handle it gracefully
    }


    public GoogleApiClient getClient() {
        return mPlusClient;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
        if (result.isSuccess()) {
            GoogleSignInAccount googleAccount = result.getSignInAccount();
            if (googleAccount == null) {
                mCallback.onSignInFailed("Unable to sign in");
                return;
            }
            loginWithFirebase(googleAccount);
        } else {
            mCallback.onSignInFailed("Unable to Signin");
        }

    }

    private void loginWithFirebase(GoogleSignInAccount googleAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivityWeakReference.get(), task -> {
                    Timber.d("signInWithCredential:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Timber.w("signInWithCredential", task.getException());
                    } else {
                        mCallback.onSignInComplete(Type.GOOGLE, googleAccount.getIdToken());
                    }
                });
    }
}
