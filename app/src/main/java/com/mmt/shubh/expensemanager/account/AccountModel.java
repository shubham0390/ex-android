package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@ConfigPersistent
public class AccountModel {

    private AccountDataAdapter mAccountDataAdapter;

    @Inject
    public AccountModel(AccountDataAdapter mAccountDataAdapter) {
        this.mAccountDataAdapter = mAccountDataAdapter;
    }

    public Observable<List<Account>> getAll() {
        return mAccountDataAdapter.getAll();
    }

    public Observable<List<ExpenseListViewModel>> getExpenses(String query) {
        return null;
    }
}
