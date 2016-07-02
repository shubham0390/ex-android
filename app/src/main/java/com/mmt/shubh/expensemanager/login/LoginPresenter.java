package com.mmt.shubh.expensemanager.login;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.setup.FacebookProfileFetcher;
import com.mmt.shubh.expensemanager.setup.GoogleProfileFetcher;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 04/Sep/2015,
 * 5:18 PM
 * TODO:Add class comment.
 */
@ConfigPersistent
public class LoginPresenter extends BasePresenter<ILoginActivityView>
        implements MVPPresenter<ILoginActivityView>, SignUpCallback {

    private final String TAG = getClass().getName();

    protected ISignUpModel mSignUpModel;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;


    @Inject
    public LoginPresenter(ISignUpModel signUpModel) {
        mSignUpModel = signUpModel;

    }

    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GoogleLoginHelper.OUR_REQUEST_CODE) {
            mGoogleLoginHelper.onActivityResult(requestCode, responseCode, intent);
        } else {
            mFacebookLoginHelper.onActivityResult(requestCode, responseCode, intent);
        }
    }

    public void setupGoogleLogin(SignInButton plusSignInButton, AppCompatActivity activity) {
        Logger.debug(TAG, "Setting Up Google login");
        mGoogleLoginHelper = new GoogleLoginHelper(activity, this);
        mGoogleLoginHelper.setUp(plusSignInButton);
    }

    public void setupFacebookLogin(TextView faceBookLoginButton, AppCompatActivity activity) {
        Logger.debug(TAG, "Setting Up facebook login");
        mFacebookLoginHelper = new FacebookLoginHelper(activity, this);
        mFacebookLoginHelper.setUp(faceBookLoginButton);
    }

    @Override
    public void onSignInComplete(ILoginHelper.Type type) {
        ProfileFetcher profileFetcher = null;
        switch (type) {
            case GOOGLE:
                Logger.debug(TAG, "Google login finished. Fetching User profile");
                profileFetcher = new GoogleProfileFetcher(mGoogleLoginHelper.getGoogleAccount());
                break;
            case FACEBOOK:
                Logger.debug(TAG, "Facebook login finished. Fetching User profile");
                profileFetcher = new FacebookProfileFetcher();
                break;
        }

        mSignUpModel.registerUserWithSocial(profileFetcher)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    getView().navigateToHome();
                }, error -> {
                    onError(2);
                });
    }


    @Override
    public void onSignInFailed(String message) {
        getView().hideProgress();
    }

    @Override
    public void onSignInCanceled() {
        getView().hideProgress();
    }

    @Override
    public void onBlockingUI(boolean show) {
        Logger.debug(TAG, "show progressbar");
        getView().showProgress();
    }

    public void onError(int statusCode) {
        if (statusCode == 2)
            getView().showError(R.string.no_internet_connection);
        else {
            getView().showError(R.string.login_failed);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
