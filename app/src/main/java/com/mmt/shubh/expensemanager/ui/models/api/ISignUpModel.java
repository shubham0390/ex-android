package com.mmt.shubh.expensemanager.ui.models.api;

import com.mmt.shubh.expensemanager.setup.ProfileFetcher;

/**
 * Created by Subham Tyagi,
 * on 19/Aug/2015,
 * 11:31 PM
 * TODO:Add class comment.
 */
public interface ISignUpModel {

    void registerUser(String fullName, String emailAddress, String password, String mobileNo);

    void registerUserWithSocial(ProfileFetcher profileFetcher);


    interface SignUpModelCallback {
        void onSuccess();
    }
}
