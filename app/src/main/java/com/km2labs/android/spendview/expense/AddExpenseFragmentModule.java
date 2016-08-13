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

package com.km2labs.android.spendview.expense;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 3/12/16.
 */
@Module
public class AddExpenseFragmentModule {

    @Provides
    @ActivityScope
    public ExpenseModel provideExpenseModel(ExpenseDataAdapter expenseDataAdapter, MemberExpenseDataAdapter memberExpenseDataAdapter,
                                            TransactionDataAdapter transactionDataAdapter, AccountDataAdapter accountDataAdapter,
                                            ExpenseBookDataAdapter expenseBookDataAdapter
            , MemberDataAdapter memberDataAdapter) {
        return new ExpenseModel(expenseDataAdapter, memberExpenseDataAdapter,
                transactionDataAdapter, accountDataAdapter, memberDataAdapter, expenseBookDataAdapter);
    }

    @Provides
    @ActivityScope
    public AddExpensePresenter provideAddExpensePresenter(ExpenseModel expenseModel) {
        return new AddExpensePresenter(expenseModel);
    }
}
