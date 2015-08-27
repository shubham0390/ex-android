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

import java.util.List;

public class ExpenseBook extends BaseContent {


    public String mName;
    public String mProfileImagePath;
    public String mDescription;
    public String mType;
    public List<Member> mMemberList;

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
}