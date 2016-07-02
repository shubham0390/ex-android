package com.mmt.shubh.expensemanager.setup;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.debug.Logger;

import timber.log.Timber;


public class GoogleProfileFetcher extends ProfileFetcher {
    private final String TAG = getClass().getName();

    private GoogleSignInAccount mGoogleSignInAccount;

    public GoogleProfileFetcher(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
        Timber.tag(getClass().getSimpleName());
    }

    @Override
    public UserInfo fetchUserAccountDetails() {
        UserInfo userInfo = new UserInfo();
        if (mGoogleSignInAccount != null) {
            Timber.d("Fetching user profile from google");
            userInfo.setDisplayName(mGoogleSignInAccount.getDisplayName());
            userInfo.setProfilePhotoUrl(mGoogleSignInAccount.getPhotoUrl().toString());
            String email = mGoogleSignInAccount.getEmail();
            userInfo.setEmailAddress(email);
            userInfo.setStatus(UserInfo.Status.ACTIVE);
        } else {
            Logger.debug(TAG, "Unable to load user information");
        }
        return userInfo;
    }


}
