package com.mmt.shubh.expensemanager.home;

import com.mmt.shubh.expensemanager.core.dagger.scope.ConfigPersistent;
import com.mmt.shubh.expensemanager.core.mvp.BasePresenter;
import com.mmt.shubh.expensemanager.core.mvp.MVPPresenter;

import javax.inject.Inject;

/**
 * Created by Subham on 30/06/16.
 */
@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeView> implements MVPPresenter<HomeView> {

    @Inject
    public HomePresenter() {
    }
}
