package com.km2labs.android.spendview.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.km2labs.android.spendview.core.dagger.module.ActivityModule;
import com.km2labs.android.spendview.home.HomeActivity;
import com.km2labs.android.spendview.login.LoginActivity;
import com.km2labs.shubh.expensemanager.R;
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
        mPresenter.attachView(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView(false);
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