package com.mmt.shubh.expensemanager.disribution;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.core.mvp.MVPFragment;

/**
 * Created by Subham Tyagi,
 * on 15/Sep/2015,
 * 4:53 PM
 * TODO:Add class comment.
 */
public class ExpenseDistributionFragment extends MVPFragment<DistributionPresenter> implements MVPLCEView<DistributionModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_distribution;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(DistributionModel data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

}
