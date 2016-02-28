package com.mmt.shubh.expensemanager.login;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 10:56 PM
 * TODO:Add class comment.
 */
public interface ILoginModel {
    void login(String emailAddress, String password);


    interface LoginModelCallback {

        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void onInvalidCredential();
    }

}
