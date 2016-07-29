package com.km2labs.android.spendview.account;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;
import com.km2labs.android.spendview.database.content.Account;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ActivityScope
public class AccountActivityPresenter extends BasePresenter<MVPLCEView<List<Account>>> implements MVPPresenter<MVPLCEView<List<Account>>> {

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
