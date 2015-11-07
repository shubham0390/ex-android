package com.mmt.shubh.expensemanager.ui.dagger.module;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.scope.ActivityScope;
import com.mmt.shubh.expensemanager.ui.presenters.CashListFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 07/Sep/2015,
 * 2:31 PM
 * TODO:Add class comment.
 */
@Module
public class CashActivityModule {

    @Provides
    @ActivityScope
    public CashListFragmentPresenter provideCashListFragmentPresenter(Context context) {
        return new CashListFragmentPresenter(context);
    }
}
