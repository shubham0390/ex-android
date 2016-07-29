package com.km2labs.android.spendview.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by Subham Tyagi,
 * on 09/Sep/2015,
 * 12:11 PM
 * TODO:Add class comment.
 */
public class FacebookLoginHelper implements ILoginHelper, FacebookCallback<LoginResult> {

    private Context mContext;

    private CallbackManager mCallbackManager;

    private SignUpCallback mCallback;


    private View.OnClickListener mFacebookLoginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn((Activity) mContext);
        }
    };

    public FacebookLoginHelper(Activity applicationContext, SignUpCallback iSignUpPresenter) {
        mContext = applicationContext.getApplicationContext();
        mCallback = iSignUpPresenter;
    }

    public void setUp(Object object) {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
        ((TextView) object).setOnClickListener(mFacebookLoginButtonClickListener);
    }

    @Override
    public void signIn(Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void signOut() {
        LoginManager.getInstance().logOut();
    }

    @Override
    public void revokeAccess() {

    }

    @Override
    public Object getClient() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        mCallbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        mCallback.onSignInComplete(Type.FACEBOOK);
    }

    @Override
    public void onCancel() {
        mCallback.onSignInCanceled();
    }

    @Override
    public void onError(FacebookException e) {
        mCallback.onSignInFailed(e.getMessage());
    }
}
