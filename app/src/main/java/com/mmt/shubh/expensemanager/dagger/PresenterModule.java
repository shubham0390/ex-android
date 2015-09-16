package com.mmt.shubh.expensemanager.dagger;

import android.content.Context;

import com.mmt.shubh.expensemanager.dagger.api.IPresenterModule;
import com.mmt.shubh.expensemanager.ui.presenters.CashListFragmentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 11:12 PM
 * TODO:Add class comment.
 */
@Module
public class PresenterModule implements IPresenterModule {

    @Override
    @Provides
    public CashListFragmentPresenter provideCashListFragmentPresenter(Context context) {
        return new CashListFragmentPresenter(context);
    }
}
