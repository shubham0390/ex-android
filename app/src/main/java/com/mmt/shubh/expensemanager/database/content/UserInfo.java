/*
 * Copyright (c) 2014. The MMT group Project
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mmt.shubh.expensemanager.database.content;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo extends BaseContent implements Parcelable{


    protected UserInfo(Parcel in) {
        mUserPassword = in.readString();
        mEmailAddress = in.readString();
        mDisplayName = in.readString();
        mCoverPhotoUrl = in.readString();
        mProfilePhotoUrl = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserPassword);
        dest.writeString(mEmailAddress);
        dest.writeString(mDisplayName);
        dest.writeString(mCoverPhotoUrl);
        dest.writeString(mProfilePhotoUrl);
    }

    public enum Status {
        ACTIVE,
        LOG_OUT
    }

    private String mUserPassword;

    private String mEmailAddress;

    private String mDisplayName;

    private String mCoverPhotoUrl;

    private String mProfilePhotoUrl;

    private Status mStatus;

    private String mPhoneNumber;

    private UserInfo() {
    }

    @Override
    public long getId() {
        return mId;
    }


    public String getCoverPhotoUrl() {
        return mCoverPhotoUrl;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getProfilePhotoUrl() {
        return mProfilePhotoUrl;
    }

    public Status getStatus() {
        return mStatus;
    }


    public String getUserPassword() {
        return mUserPassword;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public Builder buildUpon() {
        Builder builder = new Builder(this);
        return builder;
    }

    public static class Builder {

        private UserInfo mUserInfo;

        public Builder() {
            mUserInfo = new UserInfo();
        }

        private Builder(UserInfo userInfo) {
            mUserInfo = userInfo;
        }

        public Builder setId(long id) {
            mUserInfo.setId(id);
            return this;
        }

        public Builder setCoverPhotoUrl(String coverPhotoUrl) {
            mUserInfo.mCoverPhotoUrl = coverPhotoUrl;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            mUserInfo.mDisplayName = displayName;
            return this;
        }

        public Builder setEmailAddress(String emailAddress) {
            mUserInfo.mEmailAddress = emailAddress;
            return this;
        }

        public Builder setProfilePhotoUrl(String profilePhotoUrl) {
            mUserInfo.mProfilePhotoUrl = profilePhotoUrl;
            return this;
        }

        public Builder setStatus(Status status) {
            mUserInfo.mStatus = status;
            return this;
        }

        public Builder setUserPassword(String userPassword) {
            mUserInfo.mUserPassword = userPassword;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            mUserInfo.mPhoneNumber = phoneNumber;
            return this;
        }

        public UserInfo build() {
            return mUserInfo;
        }

        public void setMobileNo(String mobileNo) {

        }
    }
}