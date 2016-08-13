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


import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.content.Device;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.android.spendview.setup.ProfileFetcher;
import com.km2labs.android.spendview.setup.SeedDataTask;

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
public class LoginModel implements LoginContract.Model {

    private UserInfoDataAdapter mUserInfoDataAdapter;
    private LoginService mLoginService;
    private SeedDataTask mSeedDataTask;
    private SignupRequest mSignupRequest;

    @Inject
    public LoginModel(SeedDataTask seedDataTask, UserInfoDataAdapter userInfoDataAdapter, LoginService loginService) {
        Timber.tag(getClass().getSimpleName());
        mUserInfoDataAdapter = userInfoDataAdapter;
        mLoginService = loginService;
        mSeedDataTask = seedDataTask;
        mSignupRequest = new SignupRequest();
    }

    @Override
    public Observable<Boolean> createUser(ProfileFetcher profileFetcher) {
        return Observable.create(subscriber -> {
            User user = profileFetcher.getUserProfileDetails();
            user.setStatus(User.Status.USER_CREATED);
            user = mUserInfoDataAdapter.create(user);
            UserSettings.getInstance().setUser(user);
            mSignupRequest.setAuthenticationToken(profileFetcher.getAuthenticationToken());
            mSeedDataTask.execute();
        });
    }

    public Observable<Boolean> signupUser(String phoneNo, String loginType) {
        return Observable.create(subscriber -> {
            User user = UserSettings.getInstance().getUser();
            user.setPhoneNumber(phoneNo);
            Device device = new Device();
            user.setDevice(device);

            mSignupRequest.setUser(user);
            mSignupRequest.setLoginType(loginType);
            SignupResponse response = mLoginService.signup(mSignupRequest);
            mUserInfoDataAdapter.updateDeviceId(response.getUser().getServerId());
        });
    }
}
