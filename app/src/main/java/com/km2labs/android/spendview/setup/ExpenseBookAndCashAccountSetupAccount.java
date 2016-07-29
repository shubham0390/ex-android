package com.km2labs.android.spendview.setup;


import com.km2labs.android.spendview.database.content.UserInfo;
import com.km2labs.android.spendview.expense.ExpenseModel;
import com.km2labs.android.spendview.settings.UserSettings;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.database.content.Account;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 12:04 AM
 */

public class ExpenseBookAndCashAccountSetupAccount {

    public static final String ACTION_CREATE_ACCOUNT_EXPENSE_BOOK = "com.mmt.shubh.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK";

    ExpenseModel mExpenseModel;
    ExpenseBookDataAdapter mExpenseBookDataAdapter;

    public ExpenseBookAndCashAccountSetupAccount(ExpenseModel expenseModel,
                                                 ExpenseBookDataAdapter expenseBookDataAdapter) {
        mExpenseModel = expenseModel;
        mExpenseBookDataAdapter = expenseBookDataAdapter;
    }

    public void execute() {
        UserSettings userSettings = UserSettings.getInstance();
        UserInfo userInfo = userSettings.getUserInfo();
        createPrivateExpenseBook(userInfo);

    }

    public boolean createPrivateExpenseBook(UserInfo userInfo) {

        List<Long> members = new ArrayList<>();
        members.add(UserSettings.getInstance().getUserId());

        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setType(ExpenseBook.TYPE_PERSONAL);
        expenseBook.setDescription("This is personal expense book of" + userInfo.getEmailAddress());
        expenseBook.setName(userInfo.getDisplayName());
        expenseBook.setProfileImagePath(userInfo.getProfilePhotoUrl());
        expenseBook.setOwner(UserSettings.getInstance().getUserId());
        expenseBook.setCreationTime(System.currentTimeMillis());

        mExpenseBookDataAdapter.create(expenseBook);
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
}
