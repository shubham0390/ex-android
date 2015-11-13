package com.mmt.shubh.expensemanager.ui.viewmodel;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 4:35 PM
 * TODO:Add class comment.
 */
public class ExpenseListViewModel {

    long mExpenseId;

    String mCategoryName;

    String mCategoryImage;

    String mExpenseTitle;

    String mExpenseAmount;

    String mMemberName;

    String mExpenseDate;

    String mAccountName;

    String mAccountType;

    public String getCategoryImage() {
        return mCategoryImage;
    }

    public ExpenseListViewModel setCategoryImage(String categoryImage) {
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

    public String getExpenseAmount() {
        return mExpenseAmount;
    }

    public ExpenseListViewModel setExpenseAmount(String expenseAmount) {
        mExpenseAmount = expenseAmount;
        return this;
    }

    public String getExpenseDate() {
        return mExpenseDate;
    }

    public ExpenseListViewModel setExpenseDate(String expenseDate) {
        mExpenseDate = expenseDate;
        return this;
    }

    public String getExpenseTitle() {
        return mExpenseTitle;
    }

    public ExpenseListViewModel setExpenseTitle(String expenseTitle) {
        mExpenseTitle = expenseTitle;
        return this;
    }

    public String getMemberName() {
        return mMemberName;
    }

    public ExpenseListViewModel setMemberName(String memberName) {
        mMemberName = memberName;
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
}
