package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.mvp.MVPAbstractPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AccountActivityPresenter extends MVPAbstractPresenter<IAccountActivityView<List<Account>>> {

    @Inject
    AccountDataAdapter mAccountDataAdapter;

    @Inject
    public AccountActivityPresenter(AccountDataAdapter accountDataAdapter) {
        mAccountDataAdapter = accountDataAdapter;
    }

    public void loadAllAccounts() {
        mAccountDataAdapter.loadAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> getView().setData(d), t -> getView().showError(t, false));
    }
}
