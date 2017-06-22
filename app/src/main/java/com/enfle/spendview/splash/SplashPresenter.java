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

package com.enfle.spendview.splash;


import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.database.content.User;
import com.enfle.spendview.settings.UserSettings;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    private SplashModel mSplashModel;

    public SplashPresenter(SplashModel mSplashModel) {
        this.mSplashModel = mSplashModel;
        Timber.tag(getClass().getName());
    }

    @Override
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
        getView().showLoginScreen();
        Timber.e(throwable.getMessage());
    }

    private void checkUserInfo(User user) {
        UserSettings.getInstance().setUser(user);
        getView().showHomeScreen();
    }
}
