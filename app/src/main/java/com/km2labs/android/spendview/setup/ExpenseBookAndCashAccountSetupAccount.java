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

package com.km2labs.android.spendview.setup;


import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.database.content.Account;
import com.km2labs.android.spendview.database.content.ExpenseBook;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.expense.ExpenseModel;
import com.km2labs.android.spendview.settings.UserSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 03/Jul/2015,
 * 12:04 AM
 */

public class ExpenseBookAndCashAccountSetupAccount {

    public static final String ACTION_CREATE_ACCOUNT_EXPENSE_BOOK = "com.km2labs.spendview.android.ACTION_CREATE_ACCOUNT_EXPENSE_BOOK";

    private ExpenseModel mExpenseModel;
    private ExpenseBookDataAdapter mExpenseBookDataAdapter;

    public ExpenseBookAndCashAccountSetupAccount(ExpenseModel expenseModel,
                                                 ExpenseBookDataAdapter expenseBookDataAdapter) {
        mExpenseModel = expenseModel;
        mExpenseBookDataAdapter = expenseBookDataAdapter;
    }

    public void execute() {
        UserSettings userSettings = UserSettings.getInstance();
        User user = userSettings.getUser();
        createPrivateExpenseBook(user);

    }

    public boolean createPrivateExpenseBook(User user) {

        List<Long> members = new ArrayList<>();
        members.add(UserSettings.getInstance().getUserId());

        ExpenseBook expenseBook = new ExpenseBook();
        expenseBook.setType(ExpenseBook.TYPE_PERSONAL);
        expenseBook.setDescription("This is personal expense book of" + user.getEmail());
        expenseBook.setName(user.getName());
        expenseBook.setProfileImagePath(user.getProfileImageUrl());
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
