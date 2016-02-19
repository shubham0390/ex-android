package com.mmt.shubh.expensemanager.ui.fragment.expensebook;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.models.DistributionModel;
import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.DistributionPresenter;

/**
 * Created by Subham Tyagi,
 * on 15/Sep/2015,
 * 4:53 PM
 * TODO:Add class comment.
 */
public class ExpenseDistributionFragment extends SupportMVPFragment<MVPLCEView<DistributionModel>, DistributionPresenter> implements MVPLCEView<DistributionModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_distribution;
    }

    @Override
    protected DistributionPresenter getPresenter() {
        return new DistributionPresenter();
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
