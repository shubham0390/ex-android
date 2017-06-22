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

import android.content.Intent;

import com.enfle.spendview.database.content.User;
import com.enfle.spendview.debug.Logger;
import com.enfle.spendview.settings.UserSettings;
import com.enfle.spendview.setup.FacebookProfileFetcher;
import com.enfle.spendview.setup.GoogleProfileFetcher;
import com.enfle.spendview.utils.RxUtils;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.setup.ProfileFetcher;
import com.enfle.spendview.R;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,\
 * on 04/Sep/2015,
 * 5:18 PM
 * TODO:Add class comment.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter, LoginCallback {

    private final String TAG = getClass().getName();

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    private FirebaseAuth mFirebaseAuth;

    LoginPresenter(GoogleLoginHelper mGoogleLoginHelper, FacebookLoginHelper mFacebookLoginHelper) {
        this.mGoogleLoginHelper = mGoogleLoginHelper;
        this.mFacebookLoginHelper = mFacebookLoginHelper;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void subcribe(LoginContract.View view) {
        super.subcribe(view);
        mGoogleLoginHelper.setup();
        mFacebookLoginHelper.setup();
    }

    @Override
    public void login(LoginType loginType) {
        switch (loginType) {
            case FACEBOOK:
                mFacebookLoginHelper.signIn()
                        .compose(RxUtils.applyMainIOSchedulers())
                        .subscribe(this::onLoginSuccessful, this::onError);
                break;
            case GOOGLE:
                mGoogleLoginHelper.signIn().compose(RxUtils.applyMainIOSchedulers())
                        .subscribe(this::onLoginSuccessful, this::onError);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GoogleLoginHelper.OUR_REQUEST_CODE) {
            mGoogleLoginHelper.onActivityResult(requestCode, responseCode, intent);
        } else {
            mFacebookLoginHelper.onActivityResult(requestCode, responseCode, intent);
        }
    }

    @Override
    public void onSignInComplete(LoginType type, String token) {
        UserSettings userSettings = UserSettings.getInstance();
        ProfileFetcher profileFetcher;
        switch (type) {
            case GOOGLE:
                profileFetcher = new GoogleProfileFetcher(mGoogleLoginHelper.getSignInAccount());
                userSettings.setLoginType(LoginType.GOOGLE);
                Logger.debug(TAG, "Google login finished. Fetching User profile");
                break;
            case FACEBOOK:
                profileFetcher = new FacebookProfileFetcher(token);
                userSettings.setLoginType(LoginType.FACEBOOK);
                Logger.debug(TAG, "Facebook login finished. Fetching User profile");
                break;
            default:
                throw new IllegalStateException("Wrong Login type");
        }

        createUser(profileFetcher).compose(RxUtils.applyMainIOSchedulers())
                .subscribe(aBoolean -> getView().onSocialAuthenticated());
    }

    private void onLoginSuccessful(LoginResponse loginResponse) {
        AuthCredential credential = null;
        switch (loginResponse.getLoginType()) {

            case GOOGLE:
                credential = GoogleAuthProvider.getCredential(loginResponse.getToken(), null);
                break;
            case FACEBOOK:
                credential = FacebookAuthProvider.getCredential(loginResponse.getToken());
                break;
        }
        loginWithFirebase(credential);
    }

    private void loginWithFirebase(AuthCredential credential) {
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Timber.d("Login Successful");
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                UserSettings.getInstance().setUser(User.fromFirebaseUser(firebaseUser));
                getView().navigateToHome();
            } else {
                Timber.e(task.getException(), "Login failed");
            }
        });
    }

    private Observable<Boolean> createUser(ProfileFetcher profileFetcher) {
        return Observable.create(subscriber -> {
            User user = profileFetcher.getUserProfileDetails();
            user.setStatus(User.Status.LOGGED_IN);
            UserSettings.getInstance().setUser(user);
            subscriber.onNext(true);
        });
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

}
