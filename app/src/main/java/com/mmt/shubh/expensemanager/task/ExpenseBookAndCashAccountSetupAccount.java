package com.mmt.shubh.expensemanager.task;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.UserSettings;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.AccountRealmDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.ExpenseBookRealmDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberRealmDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoRealmDataAdapter;

import java.util.List;

import io.realm.RealmList;

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
        UserInfoRealmDataAdapter sqlDataAdapter = new UserInfoRealmDataAdapter(mContext);
        List<UserInfo> userInfos = sqlDataAdapter.getAll();
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

        RealmList<Member> members = new RealmList<>();
        Member member = loadMember();
        members.add(member);

        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setType("Private");
        expenseBook.setDescription("This is personal expense book of" + userInfo.getEmailAddress());
        expenseBook.setName(userInfo.getDisplayName());
        expenseBook.setProfileImagePath(userInfo.getProfilePhotoUrl());
        expenseBook.setMemberList(members);
        expenseBook.setOwner(member);
        expenseBook.setCreationTime(System.currentTimeMillis());
        ExpenseBookRealmDataAdapter expenseBookSQLDataAdapter = new ExpenseBookRealmDataAdapter(mContext);
        long id = expenseBookSQLDataAdapter.create(expenseBook);
        expenseBookSQLDataAdapter.addMembers(members, expenseBook);

        if (id > 0) {
            Log.d(Constants.LOG_TAG, "Created private expense book  successfully");
            return true;
        } else {
            Log.d(Constants.LOG_TAG, "Expense book creating failed");
        }
        return false;
    }

    public boolean createAccount() {
        AccountRealmDataAdapter dataAdapter = new AccountRealmDataAdapter(mContext);

        Account account = new Account();
        account.setAccountName("Cash");
        account.setAccountBalance(10000);
        account.setAccountType(Account.TYPE_CASH);
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

    public Member loadMember() {
        Member member = new Member();
        UserInfo userInfo = UserSettings.getInstance().getUserInfo();
        if (userInfo != null) {
            MemberRealmDataAdapter sqlMemberDataAdapter = new MemberRealmDataAdapter(mContext);
            return sqlMemberDataAdapter.get(userInfo.getMemberKey());
        }
        return member;
    }

    @Override
    public String getTaskAction() {
        return ACTION_CREATE_ACCOUNT_EXPENSE_BOOK;
    }
}
