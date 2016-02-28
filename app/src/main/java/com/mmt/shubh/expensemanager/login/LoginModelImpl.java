package com.mmt.shubh.expensemanager.login;

import android.text.TextUtils;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 10:58 PM
 * TODO:Add class comment.
 */
public class LoginModelImpl implements ILoginModel {

    private LoginModelCallback mLoginModelCallback;

    public LoginModelImpl(LoginModelCallback loginModelCallback) {
        mLoginModelCallback = loginModelCallback;
    }

    @Override
    public void login(String emailAddress, String password) {
        if (!isCredentialEmpty(emailAddress, password)) {

        }
    }

    private boolean isCredentialEmpty(String emailAddress, String password) {
        boolean isEmpty = false;

        if (TextUtils.isEmpty(emailAddress)) {
            isEmpty = true;
            mLoginModelCallback.onUsernameError();
        }
        if (TextUtils.isEmpty(password)) {
            isEmpty = true;
            mLoginModelCallback.onPasswordError();
        }
        return isEmpty;
    }

}
