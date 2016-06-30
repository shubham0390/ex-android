package com.mmt.shubh.expensemanager.core.dagger.component;

import com.mmt.shubh.expensemanager.account.AccountDetailsFragment;
import com.mmt.shubh.expensemanager.cash.CashListFragment;
import com.mmt.shubh.expensemanager.core.dagger.module.FragmentModule;
import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(AccountDetailsFragment accountDetailsFragment);

    void inject(CashListFragment cashListFragment);
}
