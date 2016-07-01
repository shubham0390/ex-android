package com.mmt.shubh.expensemanager.settings;

import android.content.Context;

import com.mmt.shubh.expensemanager.core.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.expensebook.ExpenseBookModel;
import com.mmt.shubh.expensemanager.expensebook.setting.ExpenseBookSettingPresenter;

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

    @Provides
    @ActivityScope
    ExpenseBookModel provideExpenseBookModel(Context context, ExpenseBookDataAdapter expenseBookDataAdapter,
                                             MemberDataAdapter memberDataAdapter) {
        return new ExpenseBookModel(context, expenseBookDataAdapter, memberDataAdapter);
    }

    @ActivityScope
    @Provides
    ExpenseBookSettingPresenter provideExpenseBookSettingPresenter(ExpenseBookModel expenseBookModel) {
        return new ExpenseBookSettingPresenter(expenseBookModel);
    }
}
