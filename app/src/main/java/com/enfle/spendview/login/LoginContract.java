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
import android.support.annotation.StringRes;

import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.core.mvp.MVPView;
import com.enfle.spendview.database.content.User;

import rx.Observable;

/**
 * Created by Subham Tyagi on 29/07/16.
 */

public interface LoginContract {

    interface View extends MVPView {

        void showProgress();

        void hideProgress();

        void navigateToHome();

        void showError(@StringRes int messageRes);

        void onSocialAuthenticated();
    }

    interface Presenter extends MVPPresenter<View> {

        void login(LoginType loginType);

        void onActivityResult(int requestCode, int responseCode, Intent intent);
    }

    interface Model {
        Observable<Boolean> createUser(User user);
    }

}