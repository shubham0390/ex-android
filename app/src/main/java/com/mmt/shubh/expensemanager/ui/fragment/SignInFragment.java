package com.mmt.shubh.expensemanager.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.ILoginPresenter;
import com.mmt.shubh.expensemanager.ui.presenters.LoginPresenterImpl;
import com.mmt.shubh.expensemanager.ui.views.ILoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignInFragment extends MVPFragment<ILoginView, ILoginPresenter<ILoginView>> implements ILoginView {


    ILoginPresenter mILoginPresenter;

    @Bind(R.id.plus_sign_in_button)
    SignInButton mPlusSignInButton;

    @Bind(R.id.facebook_login_button)
    LoginButton mFacebookLoginButton;

    @Bind(R.id.email_edit_text)
    AppCompatEditText mEmailEditText;

    @Bind(R.id.password_edit_text)
    AppCompatEditText mPasswordEditText;

    @Bind(R.id.login_progress)
    ProgressBar mProgressView;

    @Bind(R.id.login_container)
    LinearLayout mSignUpFormView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getId() == R.id.email_edit_text) {
                    onSubmit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected ILoginPresenter<ILoginView> createPresenter() {
        return new LoginPresenterImpl();
    }


    @OnClick(R.id.button_submit)
    public void onSubmit() {
        mILoginPresenter.validateCredentials(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void setUsernameError() {
        mEmailEditText.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void setPasswordError() {
        mPasswordEditText.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void setInvalidCredentialError() {

    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
