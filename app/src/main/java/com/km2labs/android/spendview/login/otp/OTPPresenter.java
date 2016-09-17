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

package com.km2labs.android.spendview.login.otp;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.login.UserModel;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.android.spendview.utils.RxUtils;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi on 29/07/16.
 */

@ConfigPersistent
public class OTPPresenter extends BasePresenter<OTPContract.View> implements OTPContract.Presenter {

    private UserModel mUserModel;

    @Inject
    public OTPPresenter(UserModel userModel) {
        mUserModel = userModel;
    }

   /* @Override
    public Func1<TextViewAfterTextChangeEvent, Boolean> isPhoneNumberValid() {
        return s -> s.toString().length() >= 10;
    }

    @Override
    public Func1<TextViewAfterTextChangeEvent, Boolean> isValidOTP() {
        return s -> s.toString().length() >= 6;
    }*/

    @Override
    public void generateOTP(String phoneNo) {
        UserSettings settings = UserSettings.getInstance();
        mUserModel.login(settings.getLoginType(), settings.getAuthToken(), phoneNo)
                .flatMap(mUserModel::createUser)
                .compose(RxUtils.applyMainIOSchedulers())
                .subscribe();
    }

    public void validateOtp(String otp) {
        mUserModel.validateOTP(otp);
    }
}
