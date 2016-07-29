package com.km2labs.android.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.km2labs.android.spendview.database.content.UserInfo;
import com.km2labs.android.spendview.database.content.contract.UserInfoContract;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.content.ModelFactory;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Subham Tyagi,
 * on 01/Jul/2015,
 * 7:26 AM
 * TODO:Add class comment.
 */
@Singleton
public class UserInfoSQLDataAdapter extends BaseSQLDataAdapter<UserInfo> implements UserInfoDataAdapter, UserInfoContract {

    @Inject
    public UserInfoSQLDataAdapter(BriteDatabase briteDatabase) {
        super(TABLE_NAME, briteDatabase);
    }

    @Override
    public UserInfo parseCursor(Cursor cursor) {
        UserInfo userInfo = ModelFactory.getUserInfo();
        userInfo.setId(cursor.getLong(cursor.getColumnIndex(RECORD_ID)));
        userInfo.setUserPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
        userInfo.setPhoneNumber(cursor.getInt(cursor.getColumnIndexOrThrow(USER_PHONE_NUMBER)));
        userInfo.setEmailAddress(cursor.getString(cursor.getColumnIndex(USER_EMAIL_ADDRESS)));
        userInfo.setCoverPhotoUrl(cursor.getString(cursor.getColumnIndex(USER_COVER_IMAGE_URL)));
        userInfo.setProfilePhotoUrl(cursor.getString(cursor.getColumnIndex(USER_PROFILE_IMAGE_URL)));
        userInfo.setDisplayName(cursor.getString(cursor.getColumnIndex(USER_DISPLAY_NAME)));
        userInfo.setMemberKey(cursor.getLong(cursor.getColumnIndex(MEMBER_KEY)));
        /*.valueOf(cursor.getString(USER_STATUS_COLUMN))*/
        userInfo.setStatus(UserInfo.Status.ACTIVE);
        return userInfo;
    }

    public ContentValues toContentValues(UserInfo userInfo) {
        ContentValues values = new ContentValues();

        if (!TextUtils.isEmpty(userInfo.getUserPassword())) {
            values.put(USER_PASSWORD, userInfo.getUserPassword());
        }
        if (!TextUtils.isEmpty(userInfo.getEmailAddress())) {
            values.put(USER_EMAIL_ADDRESS, userInfo.getEmailAddress());
        }
        if (!TextUtils.isEmpty(userInfo.getPhoneNumber() + "")) {
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

    @Override
    protected void setTaskId(UserInfo userInfo, long id) {
        userInfo.setId(id);
    }
}
