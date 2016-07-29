package com.km2labs.android.spendview.expense;


import com.km2labs.android.spendview.utils.DateUtil;

import org.threeten.bp.LocalDateTime;

/**
 * Created by Subham Tyagi,
 * on 11/Nov/2015,
 * 4:16 PM
 * TODO:Add class comment.
 */
public class ExpenseFilter {

    public static final int TIME_FILTER_ALL = 1;
    public static final int TIME_FILTER_DAY = 2;
    public static final int TIME_FILTER_WEEK = 3;
    public static final int TIME_FILTER_MONTH = 4;
    public static final int TIME_FILTER_YEAR = 5;
    public static final int TIME_FILTER_CUSTOM = 6;

    private long mExpenseBookId;
    private long mMemberId;
    private int mTimeFilter;
    private long mCategoryId;
    private long mAccountId;

    private long mStartDate;

    private long mEndDate;

    private boolean isLatest;

    public long getAccountId() {
        return mAccountId;
    }

    public ExpenseFilter setAccountId(long accountId) {
        mAccountId = accountId;
        return this;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public ExpenseFilter setCategoryId(long categoryId) {
        mCategoryId = categoryId;
        return this;
    }

    public long getExpenseBookId() {
        return mExpenseBookId;
    }

    public ExpenseFilter setExpenseBookId(long expenseBookId) {
        mExpenseBookId = expenseBookId;
        return this;
    }

    public long getMemberId() {
        return mMemberId;
    }

    public ExpenseFilter setMemberId(long memberId) {
        mMemberId = memberId;
        return this;
    }

    public int getTimeFilter() {
        return mTimeFilter;
    }

    public void setTimeFilter(int timeFilter) {
        //if time filter is set then isLatest has to be false
        isLatest = false;
        mTimeFilter = timeFilter;
        LocalDateTime today = LocalDateTime.now();
        mStartDate = DateUtil.getCurrentTimeInMilli();
        switch (timeFilter) {
            case TIME_FILTER_ALL:
                timeFilter = 0;
                break;
            case TIME_FILTER_DAY:
                int hour = today.getHour();
                mEndDate = DateUtil.toMilliSeconds(today.minusHours(hour));
                break;
            case TIME_FILTER_WEEK:
                mEndDate = DateUtil.toMilliSeconds(today.minusWeeks(1));
                break;
            case TIME_FILTER_MONTH:
                mEndDate = DateUtil.toMilliSeconds(today.minusMonths(1));
                break;
            case TIME_FILTER_YEAR:
                break;
        }
    }

    public long getEndDate() {
        return mEndDate;
    }

    public long getStartDate() {
        return mStartDate;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public ExpenseFilter setIsLatest(boolean isLatest) {
        this.isLatest = isLatest;
        return this;
    }
}
