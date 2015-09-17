package com.mmt.shubh.expensemanager.dagger.api;

import android.content.Context;

import com.mmt.shubh.expensemanager.ui.presenters.CashListFragmentPresenter;

/**
 * Created by Subham Tyagi,
 * on 06/Sep/2015,
 * 11:10 PM
 * TODO:Add class comment.
 */
public interface IPresenterModule {

    CashListFragmentPresenter provideCashListFragmentPresenter(Context context);
}
