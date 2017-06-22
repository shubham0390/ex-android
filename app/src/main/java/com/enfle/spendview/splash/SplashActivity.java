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

import android.content.Intent;
import android.os.Bundle;

import com.enfle.spendview.core.activities.BaseActivity;
import com.enfle.spendview.home.HomeActivity;
import com.enfle.spendview.login.LoginActivity;
import com.enfle.spendview.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


/**
 * Created by Subham Tyagi,
 * on 22/Jul/2015,
 * 12:00 AM
 * TODO:Add class comment.
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mPresenter.subcribe(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.checkLoginStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubcribe(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}