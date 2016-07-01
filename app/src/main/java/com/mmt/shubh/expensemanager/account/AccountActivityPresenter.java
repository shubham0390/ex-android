package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.database.content.Account;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
public class AccountActivityPresenter extends BasePresenter<MVPLCEView<List<Account>>> {

    private AccountModel mAccountModel;

    @Inject
    public AccountActivityPresenter(AccountModel accountModel) {
        mAccountModel = accountModel;
    }

    public void loadAllAccounts() {
        mAccountModel.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getView()::setData, t -> getView().showError(t, false));
    }
}
