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
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.core.mvp.MVPView;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by Subham Tyagi on 29/07/16.
 */

public interface OTPContract {


    @Subcomponent(modules = OTPModule.class)
    @ConfigPersistent
    public interface OTPComponent {
        void inject(OTPFragment otpFragment);
    }

    @Module
    public class OTPModule {

    }

    interface View extends MVPView {

        void showOtpContainer(boolean value);
    }

    interface Presenter extends MVPPresenter<View> {

       /* Func1<TextViewAfterTextChangeEvent, Boolean> isPhoneNumberValid();

        Func1<TextViewAfterTextChangeEvent, Boolean> isValidOTP();*/

        void generateOTP(String phoneNo);
    }
}
