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

package com.enfle.spendview.account;

import com.enfle.spendview.core.dagger.scope.ActivityScope;
import com.enfle.spendview.core.mvp.BasePresenter;
import com.enfle.spendview.core.mvp.MVPPresenter;
import com.enfle.spendview.core.mvp.lce.MVPLCEView;
import com.enfle.spendview.database.content.Account;

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
