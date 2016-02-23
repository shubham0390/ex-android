package com.mmt.shubh.expensemanager.setup;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.debug.Logger;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 8:48 AM
 * TODO:Add class comment.
 */
public class GoogleProfileFetcher extends ProfileFetcher {
    private final String TAG = getClass().getName();

    private GoogleSignInAccount mGoogleSignInAccount;

    public GoogleProfileFetcher(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
    }

    @Override
    public UserInfo fetchUserAccountDetails(Context context) {
        UserInfo userInfo = new UserInfo();
        if (mGoogleSignInAccount != null) {
            userInfo.setDisplayName(mGoogleSignInAccount.getDisplayName());
            userInfo.setProfilePhotoUrl(mGoogleSignInAccount.getPhotoUrl().toString());
            String email = mGoogleSignInAccount.getEmail();
            userInfo.setEmailAddress(email);
            userInfo.setStatus(UserInfo.Status.ACTIVE);
            return userInfo;
        } else {
            Logger.debug(TAG, "Unable to load user information");
        }
        return userInfo;
    }


}
