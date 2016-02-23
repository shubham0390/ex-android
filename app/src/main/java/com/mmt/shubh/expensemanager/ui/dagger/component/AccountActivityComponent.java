package com.mmt.shubh.expensemanager.ui.dagger.component;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.ui.activity.AccountActivity;
import com.mmt.shubh.expensemanager.ui.dagger.module.ModuleAccountActivity;
import com.mmt.shubh.expensemanager.ui.dagger.module.ModuleAccountDetailFragment;
import com.mmt.shubh.expensemanager.ui.fragment.account.AccountDetailsFragment;

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
                ModuleAccountDetailFragment.class,
                ModuleAccountActivity.class
        })

public interface AccountActivityComponent {

    void inject(AccountDetailsFragment accountListFragment);

    void inject(AccountActivity accountActivity);
}
