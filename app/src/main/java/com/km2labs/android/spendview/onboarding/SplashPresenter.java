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

package com.km2labs.android.spendview.onboarding;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.android.spendview.utils.Constants;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by subhamtyagi on 3/15/16.
 */
@ConfigPersistent
public class SplashPresenter extends BasePresenter<SplashView> implements MVPPresenter<SplashView> {

    private SplashModel mSplashModel;
    private SharedPreferences mSharedPreferences;

    @Inject
    public SplashPresenter(SplashModel mSplashModel, SharedPreferences sharedPreferences) {
        this.mSplashModel = mSplashModel;
        mSharedPreferences = sharedPreferences;
        Timber.tag(getClass().getName());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void checkLoginStatus() {
        User user = UserSettings.getInstance().getUser();
        if (user != null) {
            getView().showHomeScreen();
        } else
            mSplashModel.getUserInfo()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::checkUserInfo, this::checkError);
    }

    private void checkError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    private void checkUserInfo(User user) {
        String phoneNumber = mSharedPreferences.getString(Constants.SF_KEY_PHONE_NUMBER, null);
        if (user == null || TextUtils.isEmpty(phoneNumber)) {
            getView().showLoginScreen();
        } else {
            user.setPhoneNumber(phoneNumber);
            UserSettings.getInstance().setUser(user);
            getView().showHomeScreen();
        }
    }
}
