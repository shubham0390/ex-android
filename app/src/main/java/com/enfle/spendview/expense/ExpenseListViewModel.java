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

package com.enfle.spendview.expense;

import com.enfle.spendview.utils.DateUtil;
import com.enfle.spendview.utils.UnitUtil;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 4:35 PM
 * TODO:Add class comment.
 */
public class ExpenseListViewModel {

    private long mExpenseId;
    private String mCategoryName;
    private int mCategoryImage;
    private String mExpenseTitle;
    private double mExpenseAmount;
    private String mOwnerName;
    private String mOwnerProfileImage;
    private long mExpenseDate;
    private String mAccountName;
    private String mAccountType;
    private String mExpenseBookName;
    private long mTransactionId;
    private long mExpenseBookId;
    private long mOwnerId;

    public int getCategoryImage() {
        return mCategoryImage;
    }

    public ExpenseListViewModel setCategoryImage(int categoryImage) {
        mCategoryImage = categoryImage;
        return this;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public ExpenseListViewModel setCategoryName(String categoryName) {
        mCategoryName = categoryName;
        return this;
    }

    public String getFormatedExpenseAmount() {
        return UnitUtil.getLocalizedUnit(mExpenseAmount);
    }

    public double getExpenseAmount() {
        return mExpenseAmount;
    }

    public ExpenseListViewModel setExpenseAmount(double expenseAmount) {
        mExpenseAmount = expenseAmount;
        return this;
    }

    public String getExpenseDate() {
        return DateUtil.getLocalizedDateTime(mExpenseDate);
    }

    public ExpenseListViewModel setExpenseDate(long expenseDate) {
        mExpenseDate = expenseDate;
        return this;
    }

    public long getExpenseDateInMill() {
        return mExpenseDate;
    }

    public String getExpenseTitle() {
        return mExpenseTitle;
    }

    public ExpenseListViewModel setExpenseTitle(String expenseTitle) {
        mExpenseTitle = expenseTitle;
        return this;
    }

    public String getMemberName() {
        return mOwnerName;
    }

    public ExpenseListViewModel setMemberName(String memberName) {
        mOwnerName = memberName;
        return this;
    }

    public long getExpenseId() {
        return mExpenseId;
    }

    public ExpenseListViewModel setExpenseId(long expenseId) {
        mExpenseId = expenseId;
        return this;
    }

    public String getAccountName() {
        return mAccountName;
    }

    public ExpenseListViewModel setAccountName(String accountName) {
        mAccountName = accountName;
        return this;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public ExpenseListViewModel setAccountType(String accountType) {
        mAccountType = accountType;
        return this;
    }

    public String getMemberProfileImage() {
        return mOwnerProfileImage;
    }

    public void setMemberProfileImage(String memberProfileImage) {
        mOwnerProfileImage = memberProfileImage;
    }

    public String getExpenseBookName() {
        return mExpenseBookName;
    }

    public void setExpenseBookName(String expenseBookName) {
        mExpenseBookName = expenseBookName;
    }

    public long getTransactionId() {
        return mTransactionId;
    }

    public long getExpenseBookId() {
        return mExpenseBookId;
    }

    public void setTransactionId(long mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    public void setExpenseBookId(long mExpenseBookId) {
        this.mExpenseBookId = mExpenseBookId;
    }

    public long getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(long mOwnerId) {
        this.mOwnerId = mOwnerId;
    }
}
