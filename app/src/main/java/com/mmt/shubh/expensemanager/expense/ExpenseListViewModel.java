package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.utils.DateUtil;
import com.mmt.shubh.expensemanager.utils.UnitUtil;

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
    private String mMemberName;
    private String mMemberProfileImage;
    private long mExpenseDate;
    private String mAccountName;
    private String mAccountType;
    private String mExpenseBookName;

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
        return DateUtil.getLocalizedDate(mExpenseDate);
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

    public String getMemberProfileImage() {
        return mMemberProfileImage;
    }

    public void setMemberProfileImage(String memberProfileImage) {
        mMemberProfileImage = memberProfileImage;
    }

    public String getExpenseBookName() {
        return mExpenseBookName;
    }

    public void setExpenseBookName(String expenseBookName) {
        mExpenseBookName = expenseBookName;
    }
}
