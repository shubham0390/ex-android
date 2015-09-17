package com.mmt.shubh.expensemanager.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.ui.component.DaggerLoginActivityComponent;
import com.mmt.shubh.expensemanager.ui.component.LoginActivityComponent;
import com.mmt.shubh.expensemanager.ui.fragment.login.SignInFragment;
import com.mmt.shubh.expensemanager.ui.fragment.login.SignUpFragment;
import com.mmt.shubh.expensemanager.ui.module.LoginModule;
import com.mmt.shubh.expensemanager.ui.presenters.LoginActivityPresenter;
import com.mmt.shubh.expensemanager.ui.views.ILoginActivityView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends ToolBarActivity implements ILoginActivityView {

    @Bind(R.id.plus_sign_in_button)
    SignInButton mPlusSignInButton;

    @Bind(R.id.facebook_login_button)
    LoginButton mFacebookLoginButton;

    @Bind(R.id.social_container)
    LinearLayout mSocialContainer;

    @Inject
    LoginActivityPresenter mLoginActivityPresenter;

    private LoginActivityComponent mComponent;

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializeToolbar();
        mLoginActivityPresenter = new LoginActivityPresenter(getApplicationContext());

        mLoginActivityPresenter.setupFacebookLogin(mFacebookLoginButton);
        mLoginActivityPresenter.setupGoogleLogin(mPlusSignInButton, this);
        mLoginActivityPresenter.attachView(this);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {

        mComponent = DaggerLoginActivityComponent.builder()
                .loginModule(new LoginModule())
                .mainComponent(mainComponent).build();
        mComponent.inject(this);
    }

    @OnClick(R.id.signin)
    public void onSignInClick() {
        SignInFragment fragment = new SignInFragment();
        fragment.setComponent(mComponent);
        installFragment(fragment);
    }

    @OnClick(R.id.signup)
    public void onSignUpClick() {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setComponent(mComponent);
        installFragment(fragment);
    }

    private void installFragment(Fragment fragment) {
        if (mCurrentFragment == null) {
            showBackButton(true);
        }
        mCurrentFragment = fragment;
        mSocialContainer.setVisibility(View.GONE);
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }


    private void showBackButton(boolean value) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(value);
        }
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
        getFragmentManager().beginTransaction().remove(mCurrentFragment).commit();
        mCurrentFragment = null;
        mSocialContainer.setVisibility(View.VISIBLE);
        showBackButton(false);
    }

    @Override
    public void onBackPressed() {

        if (mCurrentFragment != null) {
            showSocialLogin();
        } else {
            super.onBackPressed();
        }
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

