/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.database.dataadapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.content.ModelFactory;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.database.content.contract.UserInfoContract;
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
public class UserInfoSQLDataAdapter extends BaseSQLDataAdapter<User> implements UserInfoDataAdapter, UserInfoContract {

    @Inject
    public UserInfoSQLDataAdapter(BriteDatabase briteDatabase) {
        super(TABLE_NAME, briteDatabase);
    }

    @Override
    public User parseCursor(Cursor cursor) {
        User user = ModelFactory.getUserInfo();
        user.setLocalId(cursor.getLong(cursor.getColumnIndex(RECORD_ID)));
        user.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(USER_PHONE_NUMBER)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL_ADDRESS)));
        user.setCoverImageUrl(cursor.getString(cursor.getColumnIndex(USER_COVER_IMAGE_URL)));
        user.setProfileImageUrl(cursor.getString(cursor.getColumnIndex(USER_PROFILE_IMAGE_URL)));
        user.setName(cursor.getString(cursor.getColumnIndex(USER_DISPLAY_NAME)));
        user.setStatus(User.Status.valueOf(cursor.getString(cursor.getColumnIndex(USER_STATUS))));
        return user;
    }

    public ContentValues toContentValues(User user) {
        ContentValues values = new ContentValues();

        if (!TextUtils.isEmpty(user.getEmail())) {
            values.put(USER_EMAIL_ADDRESS, user.getEmail());
        }
        if (!TextUtils.isEmpty(user.getPhoneNumber() + "")) {
            values.put(USER_PHONE_NUMBER, user.getPhoneNumber());
        }
        if (!TextUtils.isEmpty(user.getName())) {
            values.put(USER_DISPLAY_NAME, user.getName());
        }
        if (!TextUtils.isEmpty(user.getCoverImageUrl())) {
            values.put(USER_COVER_IMAGE_URL, user.getCoverImageUrl());
        }
        if (!TextUtils.isEmpty(user.getProfileImageUrl())) {
            values.put(USER_PROFILE_IMAGE_URL, user.getProfileImageUrl());
        }
        if (user.getStatus() != null) {
            values.put(USER_STATUS, user.getStatus().name());
        }
        return values;
    }

    @Override
    protected void setId(User user, long id) {
        user.setLocalId(id);
    }

    @Override
    public void updateDeviceId(String id) {

    }

    @Override
    public void updateUserId(String serverId) {

    }
}
