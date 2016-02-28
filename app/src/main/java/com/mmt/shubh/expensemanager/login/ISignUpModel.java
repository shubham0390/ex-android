package com.mmt.shubh.expensemanager.login;

import android.support.annotation.StringRes;

import com.mmt.shubh.expensemanager.setup.ProfileFetcher;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:31 PM
 * TODO:Add class comment.
 */
public interface ISignUpModel {

    void registerCallback(SignUpModelCallback callback);

    void unregisterCallback();

    void registerUser(String fullName, String emailAddress, String password, int mobileNo);

    void registerUserWithSocial(ProfileFetcher profileFetcher);


    interface SignUpModelCallback {
        void onSuccess();

        void updateProgress(@StringRes int about);

        void onError(int statusCode);
    }
}
