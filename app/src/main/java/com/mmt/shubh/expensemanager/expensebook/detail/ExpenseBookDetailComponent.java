package com.mmt.shubh.expensemanager.expensebook.detail;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.expensebook.setting.ExpenseBookSettingFragment;
import com.mmt.shubh.expensemanager.member.MemberListFragment;
import com.mmt.shubh.expensemanager.member.MemberListFragmentModule;
import com.mmt.shubh.expensemanager.settings.SettingFragmentModule;

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

    //ExpenseBookActivity inject(ExpenseBookActivity bookDetailActivity);

    MemberListFragment inject(MemberListFragment memberListFragment);

    ExpenseBookSettingFragment inject(ExpenseBookSettingFragment expenseBookSettingFragment);

    ExpenseBookDetailFragment inject(ExpenseBookDetailFragment expenseBookGraphFragment);

    /*ExpenseDistributionFragment inject(ExpenseDistributionFragment expenseDistributionFragment);*/
}
