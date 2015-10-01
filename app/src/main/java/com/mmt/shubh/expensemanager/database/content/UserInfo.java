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

import org.parceler.Parcel;

@Parcel(value = Parcel.Serialization.BEAN)
public class UserInfo {
    private long id;
    private String mUserPassword;
    private String mEmailAddress;
    private String mDisplayName;
    private String mCoverPhotoUrl;
    private String mProfilePhotoUrl;
    private Status mStatus;
    private String mPhoneNumber;
    private long mMemberKey;
    public UserInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoverPhotoUrl() {
        return mCoverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.mCoverPhotoUrl = coverPhotoUrl;

    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.mEmailAddress = emailAddress;
    }

    public String getProfilePhotoUrl() {
        return mProfilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.mProfilePhotoUrl = profilePhotoUrl;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String userPassword) {
        this.mUserPassword = userPassword;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
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

    public enum Status {
        ACTIVE,
        LOG_OUT
    }


}