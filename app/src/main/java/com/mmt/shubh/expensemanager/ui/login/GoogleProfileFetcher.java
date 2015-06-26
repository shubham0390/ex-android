package com.mmt.shubh.expensemanager.ui.login;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;

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
    public Account fetchUserAccountDetails(Context context) {
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Account.Builder builder = new Account.Builder();
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
            builder.setStatus(Account.Status.ACTIVE);
            Account account = builder.build();
            AsyncQueryHandler queryHandler = new AccountAsyncQueryHandler(context.getContentResolver());
            AccountSQLDataAdapter accountSQLDataAdapter = new AccountSQLDataAdapter(context);
            queryHandler.startInsert(1, null, AccountContract.ACCOUNT_URI, accountSQLDataAdapter.toContentValues(account));
            return account;
        }
        return null;
    }

    static class AccountAsyncQueryHandler extends AsyncQueryHandler {
        public AccountAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);
        }
    }


}
