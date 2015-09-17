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

import java.util.List;

public class ExpenseBook extends BaseContent implements Parcelable {

    public String mName;
    public String mProfileImagePath;
    public String mDescription;
    public String mType;
    public long mCreationTime;
    private Member mOwner;

    public List<Member> mMemberList;

    public ExpenseBook() {
    }

    protected ExpenseBook(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mProfileImagePath = in.readString();
        mDescription = in.readString();
        mType = in.readString();
        mCreationTime = in.readLong();
        mOwner = in.readParcelable(Member.class.getClassLoader());
        mMemberList = in.createTypedArrayList(Member.CREATOR);
    }

    public static final Creator<ExpenseBook> CREATOR = new Creator<ExpenseBook>() {
        @Override
        public ExpenseBook createFromParcel(Parcel in) {
            return new ExpenseBook(in);
        }

        @Override
        public ExpenseBook[] newArray(int size) {
            return new ExpenseBook[size];
        }
    };

    public String getName() {
        return mName;
    }

    public ExpenseBook setName(String name) {
        mName = name;
        return this;
    }

    public String getProfileImagePath() {
        return mProfileImagePath;
    }

    public ExpenseBook setProfileImagePath(String profileImagePath) {
        mProfileImagePath = profileImagePath;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public ExpenseBook setDescription(String description) {
        mDescription = description;
        return this;
    }

    public String getType() {
        return mType;
    }

    public ExpenseBook setType(String type) {
        mType = type;
        return this;
    }

    public List<Member> getMemberList() {
        return mMemberList;
    }

    public ExpenseBook setMemberList(List<Member> memberList) {
        mMemberList = memberList;
        return this;
    }

    public Member getOwner() {
        return mOwner;
    }

    public void setOwner(Member ownerId) {
        mOwner = ownerId;
    }

    public long getCreationTime() {
        return mCreationTime;
    }

    public void setCreationTime(long creationTime) {
        mCreationTime = creationTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mProfileImagePath);
        parcel.writeString(mDescription);
        parcel.writeString(mType);
        parcel.writeLong(mCreationTime);
        parcel.writeParcelable(mOwner, i);
        parcel.writeTypedList(mMemberList);
    }


}