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

    @Bind(R.id.email_login_button)
    TextView mEmailLoginButton;

    @Bind(R.id.social_container)
    LinearLayout mSocialContainer;

    @Bind(R.id.login_container)
    FrameLayout frameLayout;

    @Bind(R.id.login_progress)
    View mProgressView;

    @Inject
    LoginActivityPresenter mLoginActivityPresenter;

    @Bind(R.id.login_card)
    CardView mLoginCard;

    private LoginActivityComponent mComponent;

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializeToolbar();
        getSupportActionBar().setElevation(0);
        mLoginActivityPresenter.setupFacebookLogin(mFacebookLoginButton);
        mLoginActivityPresenter.setupGoogleLogin(mPlusSignInButton, this);
        mLoginActivityPresenter.attachView(this);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        mComponent = DaggerLoginActivityComponent.builder()
                .loginModule(new LoginModule(this))
                .mainComponent(mainComponent).build();
        mComponent.inject(this);
    }

    public void onSignInClick() {
        SignInFragment fragment = new SignInFragment();
        fragment.setComponent(mComponent);
        installFragment(fragment);
    }

    @OnClick(R.id.email_login_button)
    public void onSignUpClick() {
        mLoginCard.setVisibility(View.VISIBLE);
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
        mLoginCard.setVisibility(View.INVISIBLE);
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
        showProgress(true);
        // mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
        //mProgressView.setVisibility(View.GONE);
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
    public void showError(int messageRes) {

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            frameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            frameLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    frameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            frameLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            frameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

