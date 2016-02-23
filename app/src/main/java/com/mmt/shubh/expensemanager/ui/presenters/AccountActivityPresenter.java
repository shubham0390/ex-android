package com.mmt.shubh.expensemanager.ui.presenters;

import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.ui.mvp.MVPAbstractPresenter;
import com.mmt.shubh.expensemanager.ui.views.IAccountActivityView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public class AccountActivityPresenter extends MVPAbstractPresenter<IAccountActivityView<List<Account>>> {

    AccountDataAdapter mAccountDataAdapter;

    @Inject
    public AccountActivityPresenter(AccountDataAdapter accountDataAdapter) {
        mAccountDataAdapter = accountDataAdapter;
    }

    public void loadAllAccounts() {
        mAccountDataAdapter.loadAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Account>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Account> accounts) {
                        getView().setData(accounts);
                    }
                });


    }
}
