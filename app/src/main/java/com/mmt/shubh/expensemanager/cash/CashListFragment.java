package com.mmt.shubh.expensemanager.cash;

import android.os.Bundle;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.core.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.core.dagger.module.FragmentModule;
import com.mmt.shubh.expensemanager.core.mvp.MVPFragment;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;


public class CashListFragment extends MVPFragment<CashListFragmentPresenter> implements MVPLCEView {

    public CashListFragment() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cash_list;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(false);
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
    public void setData(Object data) {

    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        mainComponent.fragmentComponent(new FragmentModule()).inject(this);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        //getLoaderManager().restartLoader(1234,null,mPresenter);
    }
}
