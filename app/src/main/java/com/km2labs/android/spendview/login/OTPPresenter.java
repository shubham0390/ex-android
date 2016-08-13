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

import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.settings.UserSettings;

import javax.inject.Inject;

import rx.functions.Func1;

/**
 * Created by Subham Tyagi on 29/07/16.
 */

@ConfigPersistent
public class OTPPresenter extends BasePresenter<OTPContract.View> implements OTPContract.Presenter {

    private LoginModel mLoginModel;

    @Inject
    public OTPPresenter(LoginModel loginModel) {
        mLoginModel = loginModel;
    }

    @Override
    public Func1<TextViewAfterTextChangeEvent, Boolean> isPhoneNumberValid() {
        return s -> s.toString().length() >= 10;
    }

    @Override
    public Func1<TextViewAfterTextChangeEvent, Boolean> isValidOTP() {
        return s -> s.toString().length() >= 6;
    }

    @Override
    public void generateOTP(String phoneNo) {
        String loginType = UserSettings.getInstance().getLoginType();
        mLoginModel.signupUser(phoneNo, loginType);
    }

}
