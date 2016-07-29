package com.km2labs.android.spendview.account;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.database.content.Account;
import com.km2labs.android.spendview.expense.ExpenseListViewModel;

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
