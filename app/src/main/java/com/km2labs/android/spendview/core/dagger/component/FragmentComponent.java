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

package com.km2labs.android.spendview.core.dagger.component;

import com.km2labs.android.spendview.account.AccountDetailsFragment;
import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.expensebook.add.AddMembersToExpenseBookFragment;
import com.km2labs.android.spendview.expensebook.add.AddUpdateExpenseBookFragment;
import com.km2labs.android.spendview.expensebook.detail.ExpenseBookDetailFragment;
import com.km2labs.android.spendview.expensebook.setting.ExpenseBookSettingFragment;
import com.km2labs.android.spendview.member.MemberListFragment;
import com.km2labs.android.spendview.core.dagger.module.FragmentModule;

import dagger.Subcomponent;

@ConfigPersistent
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(AccountDetailsFragment accountDetailsFragment);

    void inject(ExpenseBookDetailFragment expenseBookDetailFragment);

    void inject(ExpenseBookSettingFragment expenseBookSettingFragment);

    void inject(MemberListFragment memberListFragment);

    void inject(AddUpdateExpenseBookFragment addUpdateExpenseBookFragment);

    void inject(AddMembersToExpenseBookFragment addMembersToExpenseBookFragment);
}
