package com.mmt.shubh.expensemanager.task;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 12:04 AM
 */

public class ExpenseBookAndCashAccountSetupAccount extends AbstractTask {

    public static final String ACTION_CREATE_ACCOUNT_EXPENSE_BOOK = "com.mmt.shubh.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK";

    public ExpenseBookAndCashAccountSetupAccount(Context context) {
        super(context);
    }

    @Override
    public TaskResult execute() {
        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(mContext);
        List<UserInfo> userInfos = sqlDataAdapter.getAll();
        TaskResult result;
        if (userInfos != null && !userInfos.isEmpty()) {
            UserInfo userInfo = userInfos.get(0);
            createPrivateExpenseBook(userInfo);
            createAccount();
            return new TaskResult(true, "", 4);
        }

        return new TaskResult(false, "", 4);
    }

    @VisibleForTesting
    public boolean createPrivateExpenseBook(UserInfo userInfo) {
        ExpenseBookSQLDataAdapter expenseBookSQLDataAdapter = new ExpenseBookSQLDataAdapter(mContext);
        List<Member> members = new ArrayList<>();
        members.add(createMember());

        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setType("Private");
        expenseBook.setDescription("This is personal expense book of" + userInfo.getEmailAddress());
        expenseBook.setName(userInfo.getDisplayName());
        expenseBook.setProfileImagePath(userInfo.getProfilePhotoUrl());
        expenseBook.setMemberList(members);
        long id = expenseBookSQLDataAdapter.create(expenseBook);
        expenseBookSQLDataAdapter.addMembers(members);
        if (id > 0) {
            Log.d(Constants.LOG_TAG, "Created private expense book  successfully");
            return true;
        } else {
            Log.d(Constants.LOG_TAG, "Expense book creating failed");
        }
        return false;
    }

    @VisibleForTesting
    public boolean createAccount() {
        AccountSQLDataAdapter dataAdapter = new AccountSQLDataAdapter(mContext);

        Account account = new Account();
        account.setAccountName("Cash");
        account.setAccountBalance(10000);
        account.setType(Account.TYPE_CASH);
        account.setAccountNumber("Cash");
        long id = dataAdapter.create(account);
        if (id > 0) {
            Log.d(Constants.LOG_TAG, "Created Account successfully");
            return true;
        } else {
            Log.d(Constants.LOG_TAG, "Account creating failed");
        }
        return false;
    }

    @VisibleForTesting
    public Member createMember() {
        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(mContext);
        Member member = new Member();
        UserInfo userInfo = sqlDataAdapter.getAll().get(0);
        if (userInfo != null) {
            member.setCoverPhotoUrl(userInfo.getCoverPhotoUrl());
            member.setMemberEmail(userInfo.getEmailAddress());
            member.setMemberName(userInfo.getDisplayName());
            member.setProfilePhotoUrl(userInfo.getProfilePhotoUrl());
        }
        return member;
    }

    @Override
    public String getTaskAction() {
        return ACTION_CREATE_ACCOUNT_EXPENSE_BOOK;
    }
}
