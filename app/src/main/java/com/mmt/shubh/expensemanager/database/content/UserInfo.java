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

public class UserInfo extends BaseContent implements Parcelable {


    public enum Status {
        ACTIVE,
        LOG_OUT
    }


    public UserInfo() {
    }

    private String mUserPassword;

    private String mEmailAddress;

    private String mDisplayName;

    private String mCoverPhotoUrl;

    private String mProfilePhotoUrl;

    private Status mStatus;

    private String mPhoneNumber;

    private long mMemberKey;

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

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.mCoverPhotoUrl = coverPhotoUrl;

    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public void setEmailAddress(String emailAddress) {
        this.mEmailAddress = emailAddress;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.mProfilePhotoUrl = profilePhotoUrl;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
    }

    public void setUserPassword(String userPassword) {
        this.mUserPassword = userPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public void setMobileNo(String mobileNo) {
        mPhoneNumber = mobileNo;
    }

    public long getMemberKey() {
        return mMemberKey;
    }

    public void setMemberKey(long memberKey) {
        mMemberKey = memberKey;
    }
    protected UserInfo(Parcel in) {
        mId = in.readLong();
        mUserPassword = in.readString();
        mEmailAddress = in.readString();
        mDisplayName = in.readString();
        mCoverPhotoUrl = in.readString();
        mProfilePhotoUrl = in.readString();
        mPhoneNumber = in.readString();
        mMemberKey = in.readLong();
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mUserPassword);
        parcel.writeString(mEmailAddress);
        parcel.writeString(mDisplayName);
        parcel.writeString(mCoverPhotoUrl);
        parcel.writeString(mProfilePhotoUrl);
        parcel.writeString(mPhoneNumber);
        parcel.writeLong(mMemberKey);
    }

}