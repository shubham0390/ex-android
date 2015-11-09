package com.mmt.shubh.expensemanager.database.dataadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.mmt.shubh.expensemanager.database.api.UserInfoDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.ContentNotFoundException;
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
public class UserInfoSQLDataAdapter extends BaseSQLDataAdapter<UserInfo> implements UserInfoDataAdapter<UserInfo>, UserInfoContract {

    private Context mContext;

    public UserInfoSQLDataAdapter(Context context) {
        super(UserInfoContract.ACCOUNT_URI, context);
        mContext = context;
    }

    public ContentValues toContentValues(UserInfo userInfo) {
        ContentValues values = new ContentValues();

        if (!TextUtils.isEmpty(userInfo.getUserPassword())) {
            values.put(USER_PASSWORD, userInfo.getUserPassword());
        }
        if (!TextUtils.isEmpty(userInfo.getEmailAddress())) {
            values.put(USER_EMAIL_ADDRESS, userInfo.getEmailAddress());
        }
        if (!TextUtils.isEmpty(userInfo.getPhoneNumber()+"")) {
            values.put(USER_PHONE_NUMBER, userInfo.getPhoneNumber());
        }
        if (!TextUtils.isEmpty(userInfo.getDisplayName())) {
            values.put(USER_DISPLAY_NAME, userInfo.getDisplayName());
        }
        if (!TextUtils.isEmpty(userInfo.getCoverPhotoUrl())) {
            values.put(USER_COVER_IMAGE_URL, userInfo.getCoverPhotoUrl());
        }
        if (!TextUtils.isEmpty(userInfo.getProfilePhotoUrl())) {
            values.put(USER_PROFILE_IMAGE_URL, userInfo.getProfilePhotoUrl());
        }
        if (userInfo.getStatus() != null) {
            values.put(USER_STATUS, userInfo.getStatus().name());
        }
        values.put(MEMBER_KEY, userInfo.getMemberKey());
        return values;
    }

    public void restore(Cursor cursor, UserInfo userInfo) {
        userInfo.setId(cursor.getLong(USER_ID_COLUMN));
        userInfo.setUserPassword(cursor.getString(USER_PASSWORD_COLUMN));
        userInfo.setPhoneNumber(cursor.getInt(cursor.getColumnIndexOrThrow(USER_PHONE_NUMBER)));
        userInfo.setEmailAddress(cursor.getString(USER_EMAIL_ADDRESS_COLUMN));
        userInfo.setCoverPhotoUrl(cursor.getString(USER_COVER_IMAGE_URL_COLUMN));
        userInfo.setProfilePhotoUrl(cursor.getString(USER_PROFILE_IMAGE_URL_COLUMN));
        userInfo.setDisplayName(cursor.getString(USER_DISPLAY_NAME_COLUMN));
        userInfo.setMemberKey(cursor.getLong(MEMBER_KEY_COLUMN));
        /*.valueOf(cursor.getString(USER_STATUS_COLUMN))*/
        userInfo.setStatus(UserInfo.Status.ACTIVE);
    }

    @Override
    public long create(UserInfo userInfo) {
        Uri uri = super.save(userInfo);
        List paths = uri.getPathSegments();
        long id = Long.parseLong((String) paths.get(paths.size() - 1));
        userInfo.setId(id);
        return id;
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
        UserInfo userInfo = new UserInfo();
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
                    UserInfo userInfo = new UserInfo();
                    restore(cursor, userInfo);
                    userInfoList.add(userInfo);
                }
            } finally {
                cursor.close();
            }
        }
        return userInfoList;
    }
}
