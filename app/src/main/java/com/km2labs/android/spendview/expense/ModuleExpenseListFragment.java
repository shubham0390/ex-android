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

import android.content.Context;

import com.km2labs.android.spendview.core.dagger.scope.ActivityScope;
import com.km2labs.android.spendview.database.api.ExpenseBookDataAdapter;
import com.km2labs.android.spendview.database.api.ExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.MemberDataAdapter;
import com.km2labs.android.spendview.database.api.MemberExpenseDataAdapter;
import com.km2labs.android.spendview.database.api.TransactionDataAdapter;
import com.km2labs.android.spendview.database.api.exceptions.AccountDataAdapter;
import com.km2labs.android.spendview.core.mvp.lce.LCEViewState;
import com.km2labs.android.spendview.core.mvp.lce.LCEViewStateImpl;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by subhamtyagi on 2/27/16.
 */
@Module
public class ModuleExpenseListFragment {

    @Provides
    @ActivityScope
    public ExpenseListPresenter provideExpenseListPresenter(Context context, ExpenseModel expenseModel) {
        return new ExpenseListPresenter(expenseModel, context);
    }

    @Provides
    @ActivityScope
    LCEViewState<List<ExpenseListViewModel>, MVPLCEView<List<ExpenseListViewModel>>> provideViewLCEViewState() {
        return new LCEViewStateImpl<>();
    }

    @Provides
    @ActivityScope
    public ExpenseModel provideExpenseModel(ExpenseDataAdapter expenseDataAdapter,
                                            MemberExpenseDataAdapter memberDataAdapter,
                                            TransactionDataAdapter transactionDataAdapter,
                                            AccountDataAdapter accountDataAdapter,
                                            MemberDataAdapter memberData, ExpenseBookDataAdapter expenseBookDataAdapter) {
        return new ExpenseModel(expenseDataAdapter, memberDataAdapter, transactionDataAdapter,
                accountDataAdapter, memberData,expenseBookDataAdapter);
    }
}
