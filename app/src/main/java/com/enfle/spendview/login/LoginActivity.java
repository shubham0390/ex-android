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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.enfle.spendview.core.mvp.MVPActivity;
import com.enfle.spendview.database.content.User;
import com.enfle.spendview.home.HomeActivity;
import com.enfle.spendview.settings.UserSettings;
import com.google.android.gms.common.SignInButton;
import com.enfle.spendview.utils.Constants;
import com.enfle.spendview.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class LoginActivity extends MVPActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.plus_sign_in_button)
    SignInButton mPlusSignInButton;

    @BindView(R.id.facebook_login_button)
    AppCompatButton mFacebookLoginButton;

    @BindView(R.id.social_container)
    LinearLayout mSocialContainer;

    @BindView(R.id.login_progress)
    View mProgressView;

    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        initializeToolbar();
        getSupportActionBar().setElevation(0);
        mPresenter.subcribe(this);
        mPlusSignInButton.setStyle(SignInButton.SIZE_WIDE, SignInButton.COLOR_LIGHT);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    private void showBackButton(boolean value) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(value);
        }
    }

    @OnClick(R.id.plus_sign_in_button)
    void onGoogleLoginClicked() {
        mPresenter.login(LoginType.GOOGLE);
    }

    @OnClick(R.id.facebook_login_button)
    void onFacebookLoginClicked() {
        mPresenter.login(LoginType.FACEBOOK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showSocialLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSocialLogin() {
        mSocialContainer.setVisibility(View.VISIBLE);
        showBackButton(false);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(int messageRes) {

    }

    @Override
    public void onSocialAuthenticated() {
        mSharedPreferences.edit().putString(Constants.SF_KEY_PHONE_NUMBER, "9663295153").apply();
        UserSettings.getInstance().getUser().setPhoneNumber("9663295153");
        UserSettings.getInstance().getUser().setStatus(User.Status.LOGGED_IN);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mSocialContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        mSocialContainer.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mSocialContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mSocialContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}

