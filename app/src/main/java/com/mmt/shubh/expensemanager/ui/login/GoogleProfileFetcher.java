package com.mmt.shubh.expensemanager.ui.login;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.content.contract.UserInfoContract;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

/**
 * Created by styagi on 6/4/2015.
 */
public class GoogleProfileFetcher implements ProfileFetcher {

    private GoogleApiClient mGoogleApiClient;

    public GoogleProfileFetcher(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    public void createAccount() {

    }

    @Override
    public UserInfo fetchUserAccountDetails(Context context) {
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        UserInfo.Builder builder = new UserInfo.Builder();
        if (currentPerson != null) {
            builder.setUserName(currentPerson.getNickname());
            builder.setDisplayName(currentPerson.getDisplayName());
            Person.Image personPhoto = currentPerson.getImage();
            builder.setProfilePhotoUrl(personPhoto.getUrl());
            Person.Cover personCover = currentPerson.getCover();

            if(personCover!=null && personCover.hasCoverPhoto())
            builder.setCoverPhotoUrl(personCover.getCoverPhoto().getUrl());

            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            builder.setEmailAddress(email);
            builder.setStatus(UserInfo.Status.ACTIVE);
            UserInfo userInfo = builder.build();
            AsyncQueryHandler queryHandler = new UserInfoAsyncQueryHandler(context.getContentResolver());
            UserInfoSQLDataAdapter userInfoSQLDataAdapter = new UserInfoSQLDataAdapter(context);
            queryHandler.startInsert(1, null, UserInfoContract.ACCOUNT_URI, userInfoSQLDataAdapter.toContentValues(userInfo));
            return userInfo;
        }
        return null;
    }

    static class UserInfoAsyncQueryHandler extends AsyncQueryHandler {
        public UserInfoAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);
        }
    }


}
