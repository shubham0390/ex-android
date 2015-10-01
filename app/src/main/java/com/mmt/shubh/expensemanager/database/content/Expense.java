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

import java.util.List;

@Parcel(value = Parcel.Serialization.BEAN)
public class Expense {
    private long id;
    private String mExpenseAmount;
    private long mExpenseDate;
    private String mExpenseName;
    private long mOwnerId;
    private String mExpensePlace;
    private String mExpenseDescription;
    private ExpenseBook mExpenseBook;
    private ExpenseCategory mExpenseCategory;
    private List<Member> mMemberList;
    public Expense() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(long ownerId) {
        mOwnerId = ownerId;
    }

    public String getExpenseAmount() {
        return mExpenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        mExpenseAmount = expenseAmount;
    }

    public long getExpenseDate() {
        return mExpenseDate;
    }

    public void setExpenseDate(long expenseDate) {
        mExpenseDate = expenseDate;
    }

    public String getExpenseName() {
        return mExpenseName;
    }

    public void setExpenseName(String expenseName) {
        mExpenseName = expenseName;
    }

    public String getExpensePlace() {
        return mExpensePlace;
    }

    public void setExpensePlace(String expensePlace) {
        mExpensePlace = expensePlace;
    }

    public String getExpenseDescription() {
        return mExpenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        mExpenseDescription = expenseDescription;
    }

    public List<Member> getMemberList() {
        return mMemberList;
    }

    public void setMemberList(List<Member> memberList) {
        mMemberList = memberList;
    }

    public ExpenseBook getExpenseBook() {
        return mExpenseBook;
    }

    public void setExpenseBook(ExpenseBook expenseBook) {
        mExpenseBook = expenseBook;
    }

    public ExpenseCategory getExpenseCategory() {
        return mExpenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        mExpenseCategory = expenseCategory;
    }
}