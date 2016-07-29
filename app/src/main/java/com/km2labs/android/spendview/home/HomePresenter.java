package com.km2labs.android.spendview.home;

import com.km2labs.android.spendview.core.dagger.scope.ConfigPersistent;
import com.km2labs.android.spendview.core.mvp.BasePresenter;
import com.km2labs.android.spendview.core.mvp.MVPPresenter;

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
