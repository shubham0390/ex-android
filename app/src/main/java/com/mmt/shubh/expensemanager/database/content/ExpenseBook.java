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
import org.parceler.ParcelPropertyConverter;

import java.util.List;

import io.realm.ExpenseBookRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = {ExpenseBookRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {ExpenseBook.class})
public class ExpenseBook extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String profileImagePath;
    private String description;
    private String type;
    private long creationTime;


    private RealmList<Member> memberList;
    private Member owner;

    public ExpenseBook() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RealmList<Member> getMemberList() {
        return memberList;
    }

    @ParcelPropertyConverter(MemberParcelConverter.class)
    public void setMemberList(RealmList<Member> memberList) {
        this.memberList =  memberList;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member ownerId) {
        owner = ownerId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

}