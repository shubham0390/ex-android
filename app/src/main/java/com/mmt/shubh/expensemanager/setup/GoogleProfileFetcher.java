package com.mmt.shubh.expensemanager.setup;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
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

    private GoogleApiClient mGoogleApiClient;

    public GoogleProfileFetcher(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    @Override
    public UserInfo fetchUserAccountDetails(Context context) {
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        UserInfo.Builder builder = new UserInfo.Builder();
        if (currentPerson != null) {
            builder.setDisplayName(currentPerson.getDisplayName());
            Person.Image personPhoto = currentPerson.getImage();
            builder.setProfilePhotoUrl(personPhoto.getUrl());
            Person.Cover personCover = currentPerson.getCover();

            if (personCover != null && personCover.hasCoverPhoto())
                builder.setCoverPhotoUrl(personCover.getCoverPhoto().getUrl());

            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            builder.setEmailAddress(email);
            builder.setStatus(UserInfo.Status.ACTIVE);
            UserInfo userInfo = builder.build();
            return userInfo;
        }else{
            Logger.debug(TAG,"Unable to load user information");
        }
        return null;
    }


}
