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

package com.km2labs.android.spendview.onboarding;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.database.content.User;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.UserInfoDataAdapter;
import com.km2labs.android.spendview.database.content.ExpenseBook;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@ConfigPersistent
public class SplashModel {

    private UserInfoDataAdapter userInfoDataAdapter;
    private ExpenseBookDataAdapter expenseBookDataAdapter;

    @Inject
    public SplashModel(UserInfoDataAdapter userInfoDataAdapter, ExpenseBookDataAdapter expenseBookDataAdapter) {
        this.userInfoDataAdapter = userInfoDataAdapter;
        this.expenseBookDataAdapter = expenseBookDataAdapter;

    }

    public Observable<List<User>> getUserInfo() {
        //Assuming first row of the table
        return userInfoDataAdapter.getAll();
    }

    public Observable<List<ExpenseBook>> getPrivateExpenseBook() {
        return expenseBookDataAdapter.getPrivateExpenseBook();
    }
}
