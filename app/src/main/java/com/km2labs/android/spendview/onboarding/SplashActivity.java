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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.km2labs.android.spendview.core.dagger.module.ActivityModule;
import com.km2labs.android.spendview.home.HomeActivity;
import com.km2labs.android.spendview.login.LoginActivity;
import com.km2labs.expenseview.android.R;
import com.km2labs.android.spendview.core.dagger.component.ConfigPersistentComponent;
import com.km2labs.android.spendview.core.mvp.MVPActivity2;

/**
 * Created by Subham Tyagi,
 * on 22/Jul/2015,
 * 12:00 AM
 * TODO:Add class comment.
 */
public class SplashActivity extends MVPActivity2<SplashPresenter> implements SplashView {

    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        mPresenter.subcribe(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubcribe(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkLoginStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
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

    @Override
    protected void injectDependencies(ConfigPersistentComponent component) {
        component.activityComponent(new ActivityModule(this))
                .inject(this);
    }
}