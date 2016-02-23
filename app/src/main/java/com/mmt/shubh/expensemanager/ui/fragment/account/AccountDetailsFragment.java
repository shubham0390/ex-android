package com.mmt.shubh.expensemanager.ui.fragment.account;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.ui.dagger.component.DaggerAccountActivityComponent;
import com.mmt.shubh.expensemanager.ui.dagger.module.ModuleAccountDetailFragment;
import com.mmt.shubh.expensemanager.ui.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.ui.presenters.AccountDetailPresenter;
import com.mmt.shubh.expensemanager.ui.view.ExpenseListView;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.ui.views.IAccountDetailView;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Subham Tyagi,
 * on 12/Jul/2015,
 * 10:38 PM
 * TODO:Add class comment.
 */
public class AccountDetailsFragment extends SupportMVPFragment<IAccountDetailView, AccountDetailPresenter>
        implements IAccountDetailView {

    @Bind(R.id.expenseList)
    ExpenseListView mExpenseListView;

    @Bind(R.id.accountSummaryChart)
    BarChart mBarChart;

    Account mAccount;

    @Inject
    AccountDetailPresenter mAccountDetailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccount = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_ACCOUNT));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountDetailPresenter.loadExpenseByAccountId(mAccount.getId());
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
    public void loadData(boolean pullToRefresh) {
        mAccountDetailPresenter.loadExpenseByAccountId(mAccount.getId());
    }

    @Override
    protected void injectDependencies(MainComponent mainComponent) {
        DaggerAccountActivityComponent.builder()
                .mainComponent(mainComponent)
                .moduleAccountDetailFragment(new ModuleAccountDetailFragment())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account_view;
    }

    @Override
    public void showExpense(List<ExpenseListViewModel> listViewModels) {
        mExpenseListView.addData(listViewModels);
    }
}
