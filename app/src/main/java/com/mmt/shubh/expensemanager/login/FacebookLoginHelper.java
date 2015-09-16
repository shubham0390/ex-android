package com.mmt.shubh.expensemanager.login;

import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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

    public FacebookLoginHelper(Context applicationContext, SignUpCallback iSignUpPresenter) {
        mContext = applicationContext.getApplicationContext();
        mCallback = iSignUpPresenter;
    }

    public void setUp(Object object) {
        LoginButton loginButton = (LoginButton) object;
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, this);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
    }


    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

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
