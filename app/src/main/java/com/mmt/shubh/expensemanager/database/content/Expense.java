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

import io.realm.ExpenseRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = { ExpenseRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Expense.class })
public class Expense extends RealmObject {

    @PrimaryKey
    private long id;
    private String expenseAmount;
    private long expenseDate;
    private String expenseName;
    private Member owner;
    private String expensePlace;
    private String expenseDescription;
    private ExpenseBook expenseBook;
    private ExpenseCategory expenseCategory;


    private RealmList<Member> memberList;

    public Expense() {


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public long getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(long expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpensePlace() {
        return expensePlace;
    }

    public void setExpensePlace(String expensePlace) {
        this.expensePlace = expensePlace;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public RealmList<Member> getMemberList() {
        return memberList;
    }

    @ParcelPropertyConverter(MemberParcelConverter.class)
    public void setMemberList(RealmList<Member> memberList) {
        this.memberList = memberList;
    }

    public ExpenseBook getExpenseBook() {
        return expenseBook;
    }

    public void setExpenseBook(ExpenseBook expenseBook) {
        this.expenseBook = expenseBook;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
}