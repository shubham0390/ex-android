package com.mmt.shubh.expensemanager.ui.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mmt.shubh.expensemanager.R;
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
import com.mmt.shubh.expensemanager.ui.views.ISignUpViews;

/**
 * Created by Subham Tyagi,
 * on 17/Aug/2015,
 * 7:41 AM
 * TODO:Add class comment.
 */
public class SignUpPresenterImpl extends MVPAbstractPresenter<ISignUpViews> implements ISignUpPresenter<ISignUpViews>, SignUpCallback, ISignUpModel.SignUpModelCallback {

    private final String TAG = getClass().getName();

    private ISignUpModel mSignUpModel;

    private GoogleLoginHelper mGoogleLoginHelper;

    private FacebookLoginHelper mFacebookLoginHelper;

    private Context mContext;

    public SignUpPresenterImpl(Context context) {
        mContext = context;
        mSignUpModel = new SigUpModelImpl(context, this);
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
    public void userSignUp(String fullName, String emailAddress, String password, String mobileNo) {
        Logger.debug(TAG, "Manually registering user");
        getView().showProgress();
        if (!isEmpty(fullName, emailAddress, password, mobileNo)) {
            mSignUpModel.registerUser(fullName, emailAddress, password, mobileNo);
        }
    }

    private boolean isEmpty(String fullName, String emailAddress, String password, String mobileNo) {
        if (isViewAttached()) {
            boolean isEmpty = false;
            if (TextUtils.isEmpty(fullName)) {
                isEmpty = true;
                getView().setFullNameError(R.string.error_field_required);
            }
            if (TextUtils.isEmpty(emailAddress)) {
                isEmpty = true;
                getView().setEmailAddressError(R.string.error_field_required);
            }
            if (TextUtils.isEmpty(password)) {
                isEmpty = true;
                getView().setPasswordError(R.string.error_field_required);
            }
            if (TextUtils.isEmpty(fullName)) {
                isEmpty = true;
                getView().setEmailAddressError(R.string.error_field_required);
            }
            return isEmpty;
        }
        return false;
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
    public void resume() {

    }

    @Override
    public void pause() {

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
        Logger.debug(TAG, "launching home activity");
        getView().hideProgress();
        getView().navigateToHome();
    }

}
