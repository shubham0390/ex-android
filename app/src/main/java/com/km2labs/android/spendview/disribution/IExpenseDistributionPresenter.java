package com.km2labs.android.spendview.disribution;

import com.km2labs.android.spendview.core.mvp.MVPPresenter;
import com.km2labs.android.spendview.core.mvp.MVPView;

/**
 * Created by Subham Tyagi,
 * on 15/Sep/2015,
 * 4:59 PM
 * TODO:Add class comment.
 */
public interface IExpenseDistributionPresenter<V extends MVPView> extends MVPPresenter<V> {

    void loadDistributionData();
}
