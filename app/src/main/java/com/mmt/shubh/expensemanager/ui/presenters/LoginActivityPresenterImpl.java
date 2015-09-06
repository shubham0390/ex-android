package com.mmt.shubh.expensemanager.ui.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.login.FacebookLoginHelper;
import com.mmt.shubh.expensemanager.login.GoogleLoginHelper;
import com.mmt.shubh.expensemanager.login.ILoginHelper;
import com.mmt.shubh.expensemanager.login.SignUpCallback;
import com.mmt.shubh.expensemanager.setup.FacebookProfileFetcher;
import com.mmt.shubh.expensemanager.setup.GoogleProfileFetcher;
import com.mmt.shubh.expensemanager.setup.ProfileFetcher;
import com.mmt.shubh.expensemanager.ui.models.SigUpModelImpl;
import com.mmt.shubh.expensemanager.ui.models.api.ISignUpModel;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.views.ILoginActivityView;

/**
 * Created by Subham Tyagi,
 * on 04/Sep/2015,
 * 5:18 PM
 * TODO:Add class comment.
 */
public class LoginActivityPresenterImpl extends MVPAbstractPresenter<ILoginActivityView> implements
        ILoginActivityPresenter<ILoginActivityView>, SignUpCallback, ISignUpModel.SignUpModelCallback {

    private final String TAG = getClass().getName();
    private final Context mContext;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    private ISignUpModel mSignUpModel;

    public LoginActivityPresenterImpl(Context context) {
        mSignUpModel = new SigUpModelImpl(context, this);
        mContext = context;
    }

    @Override
    public void socialSignUp(ILoginHelper loginHelper) {
        if (loginHelper instanceof GoogleLoginHelper) {
            mGoogleLoginHelper = (GoogleLoginHelper) loginHelper;
        }
        if (loginHelper instanceof FacebookLoginHelper) {
            mFacebookLoginHelper = (FacebookLoginHelper) loginHelper;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == GoogleLoginHelper.OUR_REQUEST_CODE) {
            mGoogleLoginHelper.onActivityResult(requestCode, responseCode, intent);
        } else {
            mFacebookLoginHelper.onActivityResult(requestCode, responseCode, intent);
        }
    }

    @Override
    public void setupGoogleLogin(SignInButton plusSignInButton, Activity activity) {
        Logger.debug(TAG, "Setting Up Google login");
        mGoogleLoginHelper = new GoogleLoginHelper(activity, this);
        mGoogleLoginHelper.setUp(plusSignInButton);
    }

    @Override
    public void setupFacebookLogin(LoginButton faceBookLoginButton) {
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
                profileFetcher = new GoogleProfileFetcher(mGoogleLoginHelper.getClient());
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
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
