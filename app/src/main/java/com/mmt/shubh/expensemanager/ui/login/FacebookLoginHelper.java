package com.mmt.shubh.expensemanager.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by styagi on 6/5/2015.
 */
public class FacebookLoginHelper implements ILoginHelper, FacebookCallback<LoginResult> {

    private Context mContext;

    private CallbackManager mCallbackManager;

    private LoginCallback mCallback;

    public FacebookLoginHelper(Activity context) {
        mContext = context.getApplicationContext();
        mCallback = (LoginCallback) context;
    }

    public void setUp(Object object) {
        LoginButton loginButton = (LoginButton) object;
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, this);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_status", "user_friends"));
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
