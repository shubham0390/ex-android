package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import api.UserInfoDataAdapter;
import api.exceptions.ContentNotFoundException;

import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.content.contract.UserInfoContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 01/Jul/2015,
 * 7:26 AM
 * TODO:Add class comment.
 */
public class UserInfoSQLDataAdapter implements UserInfoDataAdapter<UserInfo>, UserInfoContract {

    private Context mContext;

    public UserInfoSQLDataAdapter(Context context) {
        mContext = context;
    }

    public ContentValues toContentValues(UserInfo userInfo) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userInfo.getUserName());
        values.put(USER_PASSWORD, userInfo.getUserPassword());
        values.put(USER_EMAIL_ADDRESS, userInfo.getEmailAddress());
        values.put(USER_DISPLAY_NAME, userInfo.getDisplayName());
        values.put(USER_COVER_IMAGE_URL, userInfo.getCoverPhotoUrl());
        values.put(USER_PROFILE_IMAGE_URL, userInfo.getProfilePhotoUrl());
        values.put(USER_STATUS, userInfo.getStatus().name());
        return values;
    }

    public void restore(Cursor cursor, UserInfo userInfo) {
        UserInfo.Builder builder = userInfo.buildUpon();
        userInfo.setId(cursor.getLong(USER_ID_COLUMN));
        builder.setUserName(cursor.getString(USER_NAME_COLUMN));
        builder.setUserPassword(cursor.getString(USER_PASSWORD_COLUMN));
        builder.setEmailAddress(cursor.getString(USER_EMAIL_ADDRESS_COLUMN));
        builder.setCoverPhotoUrl(cursor.getString(USER_COVER_IMAGE_URL_COLUMN));
        builder.setProfilePhotoUrl(cursor.getString(USER_PROFILE_IMAGE_URL_COLUMN));
        builder.setDisplayName(cursor.getString(USER_DISPLAY_NAME_COLUMN));
        builder.setStatus(UserInfo.Status.valueOf(cursor.getString(USER_STATUS_COLUMN)));
    }

    @Override
    public long create(UserInfo userInfo) {
        Uri uri = mContext.getContentResolver().insert(UserInfoContract.ACCOUNT_URI, toContentValues(userInfo));
        List paths = uri.getPathSegments();
        return Long.parseLong((String) paths.get(paths.size() - 1));
    }

    @Override
    public int update(UserInfo userInfo) {
        return mContext.getContentResolver().update(UserInfoContract.ACCOUNT_URI, toContentValues(userInfo), ID_SELECTION
                , new String[]{String.valueOf(userInfo.getId())});
    }

    @Override
    public int delete(UserInfo userInfo) {
        return delete(userInfo.getId());
    }

    @Override
    public int delete(long id) {
        return mContext.getContentResolver().delete(UserInfoContract.ACCOUNT_URI, ID_SELECTION, new String[]{String.valueOf(id)});
    }

    @Override
    public int deleteAll() {
        return mContext.getContentResolver().delete(UserInfoContract.ACCOUNT_URI, null, null);
    }

    @Override
    public UserInfo get(long id) {
        Cursor cursor = mContext.getContentResolver().query(UserInfoContract.ACCOUNT_URI,
                UserInfoContract.USER_PROJECTION, ID_SELECTION, new String[]{String.valueOf(id)}, null);
        UserInfo userInfo = new UserInfo.Builder().build();
        try {
            if (cursor == null || !cursor.moveToNext()) {
                throw new ContentNotFoundException("User not found not found with id " + id);
            } else {
                restore(cursor, userInfo);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userInfo;
    }

    @Override
    public List<UserInfo> getAll() {
        List<UserInfo> userInfoList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(UserInfoContract.ACCOUNT_URI,
                UserInfoContract.USER_PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    UserInfo userInfo = new UserInfo.Builder().build();
                    restore(cursor, userInfo);
                    userInfoList.add(userInfo);
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }
}
