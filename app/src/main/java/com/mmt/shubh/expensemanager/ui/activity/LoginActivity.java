package com.mmt.shubh.expensemanager.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.fragment.SignInFragment;
import com.mmt.shubh.expensemanager.ui.fragment.SignUpFragment;
import com.mmt.shubh.expensemanager.ui.mvp.MVPActivity;
import com.mmt.shubh.expensemanager.ui.presenters.ILoginActivityPresenter;
import com.mmt.shubh.expensemanager.ui.presenters.LoginActivityPresenterImpl;
import com.mmt.shubh.expensemanager.ui.views.ILoginActivityView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


public class LoginActivity extends MVPActivity implements ILoginActivityView {


    @Bind(R.id.plus_sign_in_button)
    SignInButton mPlusSignInButton;

    @Bind(R.id.facebook_login_button)
    LoginButton mFacebookLoginButton;

    @Bind(R.id.social_container)
    LinearLayout mSocialContainer;

    ILoginActivityPresenter mLoginActivityPresenter;

    @Inject
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        mLoginActivityPresenter = new LoginActivityPresenterImpl(getApplicationContext());

        mLoginActivityPresenter.setupFacebookLogin(mFacebookLoginButton);
        mLoginActivityPresenter.setupGoogleLogin(mPlusSignInButton, this);

        mLoginActivityPresenter.attachView(this);
    }


    @Override
    protected void injectDependencies() {
        ExpenseApplication.component().inject(this);
    }

    @OnClick(R.id.signin)
    public void onSignInClick() {
        SignUpFragment fragment = new SignUpFragment();
        installFragment(fragment);
    }

    @OnClick(R.id.signup)
    public void onSignUpClick() {
        SignInFragment fragment = new SignInFragment();
        installFragment(fragment);
    }

    private void installFragment(Fragment fragment) {
        mSocialContainer.setVisibility(View.GONE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public void showProgress() {
        // TODO: 9/4/2015 add progress bar
    }

    @Override
    public void hideProgress() {
        // TODO: 9/4/2015 remove progress bar
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setInvalidCredentialError() {
        //// TODO: 9/4/2015 show any error if occurred.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginActivityPresenter.onActivityResult(requestCode, resultCode, data);
    }
}

