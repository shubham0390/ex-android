package com.mmt.shubh.expensemanager.ui.dagger.component;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.ui.dagger.module.ModuleAccountListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.account.AccountListFragment;

import dagger.Component;

/**
 * Created by Subham Tyagi,
 * on 04/Nov/2015,
 * 4:40 PM
 * TODO:Add class comment.
 */
@ActivityScope
@Component(
        dependencies = {
                MainComponent.class
        },
        modules = {
                ModuleAccountListFragment.class
        })

public interface AccountActivityComponent {

        void inject(AccountListFragment accountListFragment);
}
