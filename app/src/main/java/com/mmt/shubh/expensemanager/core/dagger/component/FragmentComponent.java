package com.mmt.shubh.expensemanager.core.dagger.component;

import com.mmt.shubh.expensemanager.account.AccountDetailsFragment;
import com.mmt.shubh.expensemanager.cash.CashListFragment;
import com.mmt.shubh.expensemanager.core.dagger.module.FragmentModule;
import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.expensebook.add.AddMembersToExpenseBookFragment;
import com.mmt.shubh.expensemanager.expensebook.add.AddUpdateExpenseBookFragment;
import com.mmt.shubh.expensemanager.expensebook.detail.ExpenseBookDetailFragment;
import com.mmt.shubh.expensemanager.expensebook.setting.ExpenseBookSettingFragment;
import com.mmt.shubh.expensemanager.member.MemberListFragment;

import dagger.Subcomponent;

@ConfigPersistent
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(AccountDetailsFragment accountDetailsFragment);

    void inject(CashListFragment cashListFragment);

    void inject(ExpenseBookDetailFragment expenseBookDetailFragment);

    void inject(ExpenseBookSettingFragment expenseBookSettingFragment);

    void inject(MemberListFragment memberListFragment);

    void inject(AddUpdateExpenseBookFragment addUpdateExpenseBookFragment);

    void inject(AddMembersToExpenseBookFragment addMembersToExpenseBookFragment);
}
