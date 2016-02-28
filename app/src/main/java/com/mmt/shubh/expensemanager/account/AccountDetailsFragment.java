package com.mmt.shubh.expensemanager.account;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.dagger.component.MainComponent;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.mvp.SupportMVPFragment;
import com.mmt.shubh.expensemanager.utils.DateUtil;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import timber.log.Timber;

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
    BarChart mChart;

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
        Timber.e("Unable to fetch data %s", e.getMessage());
    }

    @Override
    public void setData(Object data) {
        Map<Long, Double> mapData = (Map<Long, Double>) data;

        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(mapData.size());
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(true);
        mChart.setDrawGridBackground(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.animateY(2500);
        mChart.getLegend().setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        Set<Long> keySet = mapData.keySet();
        int i = 0;
        for (Long key : keySet) {
            String monthName = DateUtil.getMonthName(key);
            xVals.add(monthName);
            BarEntry barEntry = new BarEntry((float) mapData.get(key).doubleValue(), i);
            yVals1.add(barEntry);
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setDrawValues(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData barData = new BarData(xVals, dataSets);
        barData.setDrawValues(true);

        mChart.setData(barData);
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
