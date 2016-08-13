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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.android.spendview.utils.RxUtils;
import com.km2labs.spendview.android.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Subham Tyagi on 29/07/16.
 */

public class OTPFragment extends MVPFragment<OTPPresenter> implements OTPContract.View {

    @BindView(R.id.phoneNumberEditText)
    AppCompatEditText mPhoneNumberEditText;

    @BindView(R.id.otpEditText)
    AppCompatEditText mOTPEditText;

    @BindView(R.id.button_submit)
    Button mButtonSubmit;

    @BindView(R.id.button_validate)
    Button mButtonValidate;

    @BindView(R.id.login_progress)
    ProgressBar mLoginProgress;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_otp;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RxTextView.afterTextChangeEvents(mPhoneNumberEditText).debounce(600, TimeUnit.MILLISECONDS)
                .map(mPresenter.isPhoneNumberValid()).compose(RxUtils.applyMainSchedulers())
                .subscribe(mButtonSubmit::setEnabled);

        RxTextView.afterTextChangeEvents(mOTPEditText).debounce(600, TimeUnit.MILLISECONDS)
                .map(mPresenter.isValidOTP()).compose(RxUtils.applyMainSchedulers())
                .subscribe(mButtonValidate::setEnabled);
    }


    @OnClick(R.id.button_submit)
    void onSubmit() {
        mPresenter.generateOTP(mPhoneNumberEditText.getText().toString());
    }

    @OnClick(R.id.button_validate)
    void onValidate() {

    }
}
