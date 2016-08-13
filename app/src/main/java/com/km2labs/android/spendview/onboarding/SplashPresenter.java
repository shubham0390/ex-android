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

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.settings.UserSettings;

import java.util.List;

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

    @Inject
    public SplashPresenter(SplashModel mSplashModel) {
        this.mSplashModel = mSplashModel;
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

    private void checkUserInfo(List<User> userInfos) {
        if (userInfos != null && userInfos.size() > 0)
            checkUserInfo(userInfos.get(0));
        else
            getView().showLoginScreen();
    }

    private void checkError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    private void checkUserInfo(User user) {
        if (user == null) {
            getView().showLoginScreen();
        } else {
            UserSettings.getInstance().setUser(user);
            getView().showHomeScreen();
           /* mSplashModel.getPrivateExpenseBook()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(d -> {
                        UserSettings.getInstance().setPersonalExpenseBook(d.get(0));
                        getView().showHomeScreen();
                    }, this::checkError);*/
        }
    }
}
