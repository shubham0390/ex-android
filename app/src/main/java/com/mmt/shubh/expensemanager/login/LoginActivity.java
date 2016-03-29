package com.mmt.shubh.expensemanager.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.home.HomeActivity;
import com.mmt.shubh.expensemanager.base.ToolBarActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends ToolBarActivity implements ILoginActivityView {

    @Bind(R.id.plus_sign_in_button)
    SignInButton mPlusSignInButton;

    @Bind(R.id.facebook_login_button)
    AppCompatButton mFacebookLoginButton;

    @Bind(R.id.social_container)
    LinearLayout mSocialContainer;

    @Bind(R.id.login_progress)
    View mProgressView;

    @Inject
    LoginActivityPresenter mLoginActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializeToolbar();
        getSupportActionBar().setElevation(0);
        mLoginActivityPresenter.setupFacebookLogin(mFacebookLoginButton, this);
        mLoginActivityPresenter.setupGoogleLogin(mPlusSignInButton, this);
        mLoginActivityPresenter.attachView(this);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        LoginActivityComponent mComponent = DaggerLoginActivityComponent.builder()
                .loginModule(new LoginModule(this))
                .mainComponent(mainComponent).build();
        mComponent.inject(this);
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
    public void showAskMobileScreen() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginActivityPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mSocialContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        mSocialContainer.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mSocialContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mSocialContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}

