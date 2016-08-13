/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.database.content;

import org.parceler.Parcel;

import java.util.Map;

@Parcel(value = Parcel.Serialization.BEAN)
public class Expense {

    public static final int DISTRIBUTION_PERCENTAGE = 0;
    public static final int DISTRIBUTION_EQUALLY = 1;
    public static final int DISTRIBUTION_UNEQUALY = 2;

    private long id;
    private long mExpenseDate;
    private long mOwnerId;
    private long mExpenseBookId;
    private long mExpenseCategoryId;
    private long mTransactionKey;
    private long mAccountKey;
    private double mExpenseAmount;
    private String mExpenseName;
    private String mExpensePlace;
    private String mExpenseDescription;
    private Map<Long, Double> mMemberMap;

    private int mDistrubtionType;

    public Expense() {

    }

    public long getId() {
        return id;
    }

    public Expense setId(long id) {
        this.id = id;
        return this;
    }

    public double getExpenseAmount() {
        return mExpenseAmount;
    }

    public Expense setExpenseAmount(double expenseAmount) {
        mExpenseAmount = expenseAmount;
        return this;
    }

    public long getExpenseBookId() {
        return mExpenseBookId;
    }

    public Expense setExpenseBookId(long expenseBookId) {
        mExpenseBookId = expenseBookId;
        return this;
    }

    public long getExpenseCategoryId() {
        return mExpenseCategoryId;
    }

    public Expense setExpenseCategoryId(long expenseCategoryId) {
        mExpenseCategoryId = expenseCategoryId;
        return this;
    }

    public long getExpenseDate() {
        return mExpenseDate;
    }

    public Expense setExpenseDate(long expenseDate) {
        mExpenseDate = expenseDate;
        return this;
    }

    public String getExpenseDescription() {
        return mExpenseDescription;
    }

    public Expense setExpenseDescription(String expenseDescription) {
        mExpenseDescription = expenseDescription;
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

    public Map<Long, Double> getMemberMap() {
        return mMemberMap;
    }

    public Expense setMemberMap(Map<Long, Double> memberMap) {
        mMemberMap = memberMap;
        return this;
    }

    public long getOwnerId() {
        return mOwnerId;
    }

    public Expense setOwnerId(long ownerId) {
        mOwnerId = ownerId;
        return this;
    }

    public long getTransactionKey() {
        return mTransactionKey;
    }

    public Expense setTransactionKey(long transactionKey) {
        mTransactionKey = transactionKey;
        return this;
    }

    public int getDistrubtionType() {
        return mDistrubtionType;
    }

    public Expense setDistrubtionType(int distrubtionType) {
        mDistrubtionType = distrubtionType;
        return this;
    }

    public long getAccountKey() {
        return mAccountKey;
    }

    public Expense setAccountKey(long accountKey) {
        mAccountKey = accountKey;
        return this;
    }
}