package com.mmt.shubh.expensemanager.ui.component;

import com.mmt.shubh.expensemanager.dagger.ActivityScope;
import com.mmt.shubh.expensemanager.dagger.MainComponent;
import com.mmt.shubh.expensemanager.ui.fragment.MemberListFragment;
import com.mmt.shubh.expensemanager.ui.fragment.expensebook.ExpenseBookSettingFragment;
import com.mmt.shubh.expensemanager.ui.module.MemberListFragmentModule;
import com.mmt.shubh.expensemanager.ui.module.SettingFragmentModule;

import dagger.Component;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 6:07 PM
 * TODO:Add class comment.
 */
@ActivityScope
@Component(
        dependencies = {
                MainComponent.class
        },
        modules = {
                MemberListFragmentModule.class,
                SettingFragmentModule.class})

public interface ExpenseBookDetailComponent {

    //ExpenseBookDetailActivity inject(ExpenseBookDetailActivity bookDetailActivity);

    MemberListFragment inject(MemberListFragment memberListFragment);

    ExpenseBookSettingFragment inject(ExpenseBookSettingFragment expenseBookSettingFragment);

    /*ExpenseBookGraphFragment inject(ExpenseBookGraphFragment expenseBookGraphFragment);

    ExpenseDistributionFragment inject(ExpenseDistributionFragment expenseDistributionFragment);*/
}
