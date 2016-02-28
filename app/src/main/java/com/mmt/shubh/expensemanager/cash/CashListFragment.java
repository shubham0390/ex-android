package com.mmt.shubh.expensemanager.cash;

import android.os.Bundle;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.mvp.MVPFragment;


public class CashListFragment extends MVPFragment<ICashListFragmentView, CashListFragmentPresenter> implements ICashListFragmentView {

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public String getTitle() {
        return null;
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
        CashActivityComponent component = DaggerCashActivityComponent.builder()
                .cashActivityModule(new CashActivityModule())
                .mainComponent(mainComponent).build();
        component.inject(this);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        //getLoaderManager().restartLoader(1234,null,mPresenter);
    }
}
