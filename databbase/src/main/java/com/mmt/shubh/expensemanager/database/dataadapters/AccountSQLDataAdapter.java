package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import api.AccountDataAdapter;
import api.exceptions.ContentNotFoundException;

import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public class AccountSQLDataAdapter implements AccountDataAdapter<Account>, AccountContract {

    private Context mContext;

    public AccountSQLDataAdapter(Context context) {
        mContext = context;
    }

    public ContentValues toContentValues(Account account) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, account.getUserName());
        values.put(USER_PASSWORD, account.getUserPassword());
        values.put(USER_EMAIL_ADDRESS, account.getEmailAddress());
        values.put(USER_DISPLAY_NAME, account.getDisplayName());
        values.put(USER_COVER_IMAGE_URL, account.getCoverPhotoUrl());
        values.put(USER_PROFILE_IMAGE_URL, account.getProfilePhotoUrl());
        values.put(USER_STATUS, account.getStatus().name());
        return values;
    }

    public void restore(Cursor cursor, Account account) {
        Account.Builder builder = account.buildUpon();
        account.setId(cursor.getLong(USER_ID_COLUMN));
        builder.setUserName(cursor.getString(USER_NAME_COLUMN));
        builder.setUserPassword(cursor.getString(USER_PASSWORD_COLUMN));
        builder.setEmailAddress(cursor.getString(USER_EMAIL_ADDRESS_COLUMN));
        builder.setCoverPhotoUrl(cursor.getString(USER_COVER_IMAGE_URL_COLUMN));
        builder.setProfilePhotoUrl(cursor.getString(USER_PROFILE_IMAGE_URL_COLUMN));
        builder.setDisplayName(cursor.getString(USER_DISPLAY_NAME_COLUMN));
        builder.setStatus(Account.Status.valueOf(cursor.getString(USER_STATUS_COLUMN)));
    }

    @Override
    public long create(Account account) {
        Uri uri = mContext.getContentResolver().insert(AccountContract.ACCOUNT_URI, toContentValues(account));
        List paths = uri.getPathSegments();
        return Long.parseLong((String) paths.get(paths.size() - 1));
    }

    @Override
    public int update(Account account) {
        return mContext.getContentResolver().update(AccountContract.ACCOUNT_URI, toContentValues(account), ID_SELECTION
                , new String[]{String.valueOf(account.getId())});
    }

    @Override
    public int delete(Account account) {
        return delete(account.getId());
    }

    @Override
    public int delete(long id) {
        return mContext.getContentResolver().delete(AccountContract.ACCOUNT_URI, ID_SELECTION, new String[]{String.valueOf(id)});
    }

    @Override
    public int deleteAll() {
        return mContext.getContentResolver().delete(AccountContract.ACCOUNT_URI, null, null);
    }

    @Override
    public Account get(long id) {
        Cursor cursor = mContext.getContentResolver().query(AccountContract.ACCOUNT_URI,
                AccountContract.USER_PROJECTION, ID_SELECTION, new String[]{String.valueOf(id)}, null);
        Account account = new Account.Builder().build();
        try {
            if (cursor == null || !cursor.moveToNext()) {
                throw new ContentNotFoundException("Account not found with id " + id);
            } else {
                restore(cursor, account);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accountList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(AccountContract.ACCOUNT_URI,
                AccountContract.USER_PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Account account = new Account.Builder().build();
                    restore(cursor, account);
                    accountList.add(account);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return accountList;
    }
}
