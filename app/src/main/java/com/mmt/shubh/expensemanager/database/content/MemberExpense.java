package com.mmt.shubh.expensemanager.database.content;

/**
 * Created by Subham Tyagi,
 * on 07/Nov/2015,
 * 7:32 PM
 * TODO:Add class comment.
 */
public class MemberExpense {

    private long mExpenseKey;

    private long mMemberKey;

    private double mShareAmount;

    private double mDebitAmount;

    private double mBalanceAmount;

    public double getBalanceAmount() {
        return mBalanceAmount;
    }

    public MemberExpense setBalanceAmount(double balanceAmount) {
        mBalanceAmount = balanceAmount;
        return this;
    }

    public double getDebitAmount() {
        return mDebitAmount;
    }

    public MemberExpense setDebitAmount(double debitAmount) {
        mDebitAmount = debitAmount;
        return this;
    }

    public long getExpenseKey() {
        return mExpenseKey;
    }

    public MemberExpense setExpenseKey(long expenseKey) {
        mExpenseKey = expenseKey;
        return this;
    }

    public long getMemberKey() {
        return mMemberKey;
    }

    public MemberExpense setMemberKey(long memberKey) {
        mMemberKey = memberKey;
        return this;
    }

    public double getShareAmount() {
        return mShareAmount;
    }

    public MemberExpense setShareAmount(double shareAmount) {
        mShareAmount = shareAmount;
        return this;
    }
}
