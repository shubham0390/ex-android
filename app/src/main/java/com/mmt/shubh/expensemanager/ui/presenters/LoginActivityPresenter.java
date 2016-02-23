package com.mmt.shubh.expensemanager.ui.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.login.FacebookLoginHelper;
import com.mmt.shubh.expensemanager.login.GoogleLoginHelper;
import com.mmt.shubh.expensemanager.login.ILoginHelper;
import com.mmt.shubh.expensemanager.login.SignUpCallback;
import com.mmt.shubh.expensemanager.setup.FacebookProfileFetcher;
import com.mmt.shubh.expensemanager.setup.GoogleProfileFetcher;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.task.TaskResultStatus;
import com.mmt.shubh.expensemanager.ui.models.api.ISignUpModel;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.mvp.MVPPresenter;
import com.mmt.shubh.expensemanager.ui.views.ILoginActivityView;

import javax.inject.Inject;

/**
 * Created by Subham Tyagi,
 * on 04/Sep/2015,
 * 5:18 PM
 * TODO:Add class comment.
 */
@ActivityScope
public class LoginActivityPresenter extends MVPAbstractPresenter<ILoginActivityView>
        implements MVPPresenter<ILoginActivityView>, SignUpCallback, ISignUpModel.SignUpModelCallback {

    private final String TAG = getClass().getName();

    protected ISignUpModel mSignUpModel;

    protected Context mContext;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    @Inject
    public LoginActivityPresenter(Context context, ISignUpModel signUpModel) {
        mContext = context;
        mSignUpModel = signUpModel;
        mSignUpModel.registerCallback(this);
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

    public void setupFacebookLogin(TextView faceBookLoginButton) {
        Logger.debug(TAG, "Setting Up facebook login");
        mFacebookLoginHelper = new FacebookLoginHelper(mContext, this);
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
        mSignUpModel.registerUserWithSocial(profileFetcher);
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

    @Override
    public void onSuccess() {
        getView().navigateToHome();
    }

    @Override
    public void updateProgress(@StringRes int about) {

    }

    @Override
    public void onError(int statusCode) {
        if (statusCode == TaskResultStatus.NO_INTERNET_CONNECTION)
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
