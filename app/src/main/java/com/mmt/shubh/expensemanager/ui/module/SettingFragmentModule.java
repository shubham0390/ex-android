package com.mmt.shubh.expensemanager.ui.module;

import com.mmt.shubh.expensemanager.dagger.ActivityScope;
import com.mmt.shubh.expensemanager.ui.presenters.ExpenseBookSettingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 02/Oct/2015,
 * 10:25 PM
 * TODO:Add class comment.
 */
@Module
public class SettingFragmentModule {


    @ActivityScope
    @Provides
    ExpenseBookSettingPresenter provideExpenseBookSettingPresenter() {
        return new ExpenseBookSettingPresenter();
    }
}
