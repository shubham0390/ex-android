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

package com.enfle.spendview.expense.adapters;

import com.enfle.spendview.core.recyclerview.adapter.section.BaseSection;
import com.enfle.spendview.core.recyclerview.adapter.section.BaseSectionIndexer;
import com.enfle.spendview.expense.ExpenseListViewModel;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class AccountSectionIndexer extends BaseSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection<Double> getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.mSectionName = data.getAccountName();
        section.update(data.getExpenseAmount());
        return section;
    }
}