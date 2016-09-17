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


import android.support.annotation.NonNull;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.content.Device;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.login.beans.LoginRequest;
import com.km2labs.android.spendview.login.beans.SignupRequest;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.android.spendview.setup.SeedDataTask;
import com.km2labs.android.spendview.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:32 PM
 * <p>
 * TODO:Add class comment.
 */
@ConfigPersistent
public class UserModel implements LoginContract.Model {

    private UserInfoDataAdapter mUserInfoDataAdapter;
    private LoginService mLoginService;
    private SeedDataTask mSeedDataTask;
    private SignupRequest mSignupRequest;

    @Inject
    public UserModel(SeedDataTask seedDataTask, UserInfoDataAdapter userInfoDataAdapter, LoginService loginService) {
        Timber.tag(getClass().getSimpleName());
        mUserInfoDataAdapter = userInfoDataAdapter;
        mLoginService = loginService;
        mSeedDataTask = seedDataTask;
        mSignupRequest = new SignupRequest();
    }

    @Override
    public Observable<Boolean> createUser(final User user) {
        return Observable.create(subscriber -> {
            user.setStatus(User.Status.USER_CREATED);
            UserSettings.getInstance().setUser(user);
            mUserInfoDataAdapter.create(user);
        });
    }

    public Observable<User> login(LoginType loginType, String authToken, String mobileNo) {
        return Observable.create(subscriber -> {
            LoginRequest loginRequest = getLoginRequest(loginType, authToken, mobileNo);
            mLoginService.login(loginRequest).compose(RxUtils.applySchedulers())
                    .subscribe(loginResponse -> {
                                subscriber.onNext(loginResponse.getUser());
                            },
                            subscriber::onError);
        });
    }

    @NonNull
    private LoginRequest getLoginRequest(LoginType loginType, String authToken, String mobileNo) {
        LoginRequest loginRequest = new LoginRequest();
        Device device = new Device();
        loginRequest.setDevice(device);
        loginRequest.setLoginType(loginType.name());
        loginRequest.setAuthenticationToken(authToken);
        loginRequest.setMobileNo(mobileNo);
        return loginRequest;
    }

    public void validateOTP(String otp) {

    }
}
