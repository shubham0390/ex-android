package com.mmt.shubh.expensemanager.ui.fragment.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.ui.activity.HomeActivity;
import com.mmt.shubh.expensemanager.ui.component.LoginActivityComponent;
import com.mmt.shubh.expensemanager.ui.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.SignUpPresenter;
import com.mmt.shubh.expensemanager.ui.views.ISignUpViews;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Subham Tyagi,
 * on 17/Aug/2015,
 * 8:28 AM
 * TODO:Add class comment.
 */
public class SignUpFragment extends MVPFragment<ISignUpViews, SignUpPresenter> implements ISignUpViews {

    @Bind(R.id.full_name_edit_text)
    EditText mFullNameEditText;

    @Bind(R.id.email_edit_text)
    EditText mEmailAddressEditText;

    @Bind(R.id.mobile_no_edit_text)
    EditText mMobileNumberEditText;

    @Bind(R.id.password_edit_text)
    EditText mPasswordEditText;

    @Bind(R.id.signup_container)
    NestedScrollView mSignUpFormView;

    @Bind(R.id.login_progress)
    ProgressBar mProgressView;

    private LoginActivityComponent mComponent;

    public SignUpFragment() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_signup;
    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.button_submit)
    public void onSignUp() {
        mPresenter.userSignUp(mFullNameEditText.getText().toString(),
                mEmailAddressEditText.getText().toString(),
                mPasswordEditText.getText().toString(),
                mMobileNumberEditText.getText().toString());
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
    public void setFullNameError(int stringResId) {
        mFullNameEditText.setError(getString(stringResId));
    }

    @Override
    public void setPasswordError(int stringResId) {
        mPasswordEditText.setError(getString(stringResId));
    }

    @Override
    public void setMobileNoError(int stringResId) {
        mMobileNumberEditText.setError(getString(stringResId));
    }

    @Override
    public void setEmailAddressError(int stringResId) {
        mEmailAddressEditText.setError(getString(stringResId));
    }

    @Override
    public void navigateToHome() {
        final Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                getActivity().finish();
            }
        });
    }



    public void setComponent(LoginActivityComponent component) {
        mComponent = component;
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        super.injectDependencies(mainComponent);
        mComponent.inject(this);
    }
}
