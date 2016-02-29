package com.mmt.shubh.expensemanager.task;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.UserInfo;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.database.dataadapters.UserInfoSQLDataAdapter;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;
import com.mmt.shubh.expensemanager.settings.UserSettings;

import java.util.ArrayList;
import java.util.List;

import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 12:04 AM
 */

public class ExpenseBookAndCashAccountSetupAccount extends AbstractTask {

    public static final String ACTION_CREATE_ACCOUNT_EXPENSE_BOOK = "com.mmt.shubh.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK";

    ExpenseModel mExpenseModel;
    ExpenseBookDataAdapter mExpenseBookDataAdapter;

    public ExpenseBookAndCashAccountSetupAccount(Context context, ExpenseModel expenseModel,
                                                 ExpenseBookDataAdapter expenseBookDataAdapter) {
        super(context);
        mExpenseModel = expenseModel;
        mExpenseBookDataAdapter = expenseBookDataAdapter;
    }

    @Override
    public TaskResult execute() {
        UserInfoSQLDataAdapter sqlDataAdapter = new UserInfoSQLDataAdapter(mContext);
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

        List<Member> members = new ArrayList<>();
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
        mExpenseBookDataAdapter.create(expenseBook)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(d -> {
                    mExpenseBookDataAdapter.addMembers(members, expenseBook);

                });

        return false;
    }

    public boolean createAccount() {
        AccountDataAdapter dataAdapter = mExpenseModel.getAccountDataAdapter();

        Account account = new Account();
        account.setAccountName("Cash");
        account.setAccountBalance(10000);
        account.setType(Account.TYPE_CASH);
        account.setAccountNumber("Cash");
        dataAdapter.create(account);
        return false;
    }

    public Member loadMember() {
        Member member = new Member();
        UserInfo userInfo = UserSettings.getInstance().getUserInfo();
        if (userInfo != null) {
            MemberSQLDataAdapter sqlMemberDataAdapter = new MemberSQLDataAdapter(mContext);
            return sqlMemberDataAdapter.get(userInfo.getMemberKey());
        }
        return member;
    }

    @Override
    public String getTaskAction() {
        return ACTION_CREATE_ACCOUNT_EXPENSE_BOOK;
    }
}
