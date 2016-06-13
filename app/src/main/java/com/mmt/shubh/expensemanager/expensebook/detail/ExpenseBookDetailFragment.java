package com.mmt.shubh.expensemanager.expensebook.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.expense.graph.ExpenseBarGraphView;
import com.mmt.shubh.expensemanager.mvp.SupportMVPFragment;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ExpenseBookDetailFragment extends SupportMVPFragment<ExpenseBookDetailView, ExpenseBookDetailPresenter>
        implements ExpenseBookDetailView {

    long mExpenseBookId;

    @Bind(R.id.expense_list)
    ExpenseListView expenseList;

    @Bind(R.id.barGraph)
    ExpenseBarGraphView mExpenseBarGraphView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExpenseBookId = getArguments().getLong(Constants.EXTRA_EXPENSE_BOOK_ID);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.loadExpenseByExpenseBookId(mExpenseBookId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_detail_expense_book;
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
    public void setData(List<ExpenseListViewModel> data) {
        expenseList.addData(data);
    }

    @Override
    public void setGraphData(Map<Integer, Double> mapData) {
        mExpenseBarGraphView.setData(mapData);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.loadExpenseByExpenseBookId(mExpenseBookId);
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        ExpenseBookActivityComponent component = DaggerExpenseBookActivityComponent.builder()
                .mainComponent(mainComponent)
                .expenseBookActivityModule(new ExpenseBookActivityModule())
                .build();
        component.inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
