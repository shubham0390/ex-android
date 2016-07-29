package com.km2labs.android.spendview.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.setup.FacebookProfileFetcher;
import com.km2labs.shubh.expensemanager.R;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.debug.Logger;
import com.km2labs.android.spendview.setup.GoogleProfileFetcher;
import com.km2labs.android.spendview.setup.ProfileFetcher;

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
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter, SignUpCallback {

    private final String TAG = getClass().getName();

    private LoginModel mSignUpModel;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    @Inject
    public LoginPresenter(LoginModel signUpModel) {
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
