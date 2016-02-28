package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;

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
