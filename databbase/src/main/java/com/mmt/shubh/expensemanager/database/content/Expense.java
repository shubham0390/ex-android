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

public class Expense extends BaseContent{


    private String mExpenseAmount;
    private long mExpenseDate;
    private String mExpenseName;
    private String mExpensePlace;
    private String mExpenseDescription;
    private ExpenseBook mExpenseBook;
    private Category mCategory;
    private List<Member> mMemberList;

    public Expense() {

    }


    public String getExpenseAmount() {
        return mExpenseAmount;
    }

    public Expense setExpenseAmount(String expenseAmount) {
        mExpenseAmount = expenseAmount;
        return this;
    }

    public long getExpenseDate() {
        return mExpenseDate;
    }

    public Expense setExpenseDate(long expenseDate) {
        mExpenseDate = expenseDate;
        return this;
    }

    public String getExpenseName() {
        return mExpenseName;
    }

    public Expense setExpenseName(String expenseName) {
        mExpenseName = expenseName;
        return this;
    }

    public String getExpensePlace() {
        return mExpensePlace;
    }

    public Expense setExpensePlace(String expensePlace) {
        mExpensePlace = expensePlace;
        return this;
    }

    public String getExpenseDescription() {
        return mExpenseDescription;
    }

    public Expense setExpenseDescription(String expenseDescription) {
        mExpenseDescription = expenseDescription;
        return this;
    }

    public List<Member> getMemberList() {
        return mMemberList;
    }

    public Expense setMemberList(List<Member> memberList) {
        mMemberList = memberList;
        return this;
    }

    public ExpenseBook getExpenseBook() {
        return mExpenseBook;
    }

    public Expense setExpenseBook(ExpenseBook expenseBook) {
        mExpenseBook = expenseBook;
        return this;
    }

    public Category getCategory() {
        return mCategory;
    }

    public Expense setCategory(Category category) {
        mCategory = category;
        return this;
    }
}