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

public class Member extends BaseContent {

    private String mMemberName;

    private String mMemberEmail;

    private String mMemberPhoneNumber;

    private String mCoverPhotoUrl;

    private String mProfilePhotoUrl;

    public String getCoverPhotoUrl() {
        return mCoverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        mCoverPhotoUrl = coverPhotoUrl;
    }

    public String getMemberEmail() {
        return mMemberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        mMemberEmail = memberEmail;
    }

    public String getMemberName() {
        return mMemberName;
    }

    public void setMemberName(String memberName) {
        mMemberName = memberName;
    }

    public String getProfilePhotoUrl() {
        return mProfilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        mProfilePhotoUrl = profilePhotoUrl;
    }

    public String getMemberPhoneNumber() {
        return mMemberPhoneNumber;
    }

    public void setMemberPhoneNumber(String memberPhoneNumber) {
        mMemberPhoneNumber = memberPhoneNumber;
    }
}
