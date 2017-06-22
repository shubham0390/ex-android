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

import com.enfle.spendview.core.dagger.scope.ConfigPersistent;
import com.enfle.spendview.database.api.exceptions.AccountDataAdapter;
import com.enfle.spendview.database.content.Account;
import com.enfle.spendview.expense.ExpenseListViewModel;

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
