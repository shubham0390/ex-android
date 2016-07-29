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
