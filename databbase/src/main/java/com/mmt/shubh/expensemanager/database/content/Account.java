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

public class Account extends BaseContent implements Parcelable{


    protected Account(Parcel in) {
        mUserName = in.readString();
        mUserPassword = in.readString();
        mEmailAddress = in.readString();
        mDisplayName = in.readString();
        mCoverPhotoUrl = in.readString();
        mProfilePhotoUrl = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserName);
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

    private String mUserName;

    private String mUserPassword;

    private String mEmailAddress;

    private String mDisplayName;

    private String mCoverPhotoUrl;

    private String mProfilePhotoUrl;

    private Status mStatus;


    private Account() {
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

    public String getUserName() {
        return mUserName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public Builder buildUpon() {
        Builder builder = new Builder(this);
        return builder;
    }

    public static class Builder {

        private Account mAccount;

        public Builder() {
            mAccount = new Account();
        }

        private Builder(Account account) {
            mAccount = account;
        }

        public Builder setId(long id) {
            mAccount.setId(id);
            return this;
        }

        public Builder setCoverPhotoUrl(String coverPhotoUrl) {
            mAccount.mCoverPhotoUrl = coverPhotoUrl;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            mAccount.mDisplayName = displayName;
            return this;
        }

        public Builder setEmailAddress(String emailAddress) {
            mAccount.mEmailAddress = emailAddress;
            return this;
        }

        public Builder setProfilePhotoUrl(String profilePhotoUrl) {
            mAccount.mProfilePhotoUrl = profilePhotoUrl;
            return this;
        }

        public Builder setStatus(Status status) {
            mAccount.mStatus = status;
            return this;
        }

        public Builder setUserName(String userName) {
            mAccount.mUserName = userName;
            return this;
        }

        public Builder setUserPassword(String userPassword) {
            mAccount.mUserPassword = userPassword;
            return this;
        }

        public Account build() {
            return mAccount;
        }
    }
}