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

public class Member extends BaseContent implements Parcelable {

    private String mMemberName;

    private String mMemberEmail;

    private String mMemberPhoneNumber;

    private String mCoverPhotoUrl;

    private String mProfilePhotoUrl;


    public Member() {
    }

    protected Member(Parcel in) {
        mMemberName = in.readString();
        mMemberEmail = in.readString();
        mMemberPhoneNumber = in.readString();
        mCoverPhotoUrl = in.readString();
        mProfilePhotoUrl = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMemberName);
        parcel.writeString(mMemberEmail);
        parcel.writeString(mMemberPhoneNumber);
        parcel.writeString(mCoverPhotoUrl);
        parcel.writeString(mProfilePhotoUrl);
    }


    @Override
    public int hashCode() {
        int result = getMemberName().hashCode();
        result = 31 * result + getMemberEmail().hashCode();
        result = 31 * result + getMemberPhoneNumber().hashCode();
        result = 31 * result + getCoverPhotoUrl().hashCode();
        result = 31 * result + getProfilePhotoUrl().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        Member member = (Member) o;
        if (member.getMemberEmail().equalsIgnoreCase(getMemberEmail()) || member.getMemberPhoneNumber() == getMemberPhoneNumber()) {
            return true;
        }
        return false;
    }
}
