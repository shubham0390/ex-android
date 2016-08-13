/*
 * Copyright (c) 2016. . The Km2Labs Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.km2labs.android.spendview.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.utils.Constants;
import com.km2labs.android.spendview.utils.MyYAxisValueFormatter;
import com.km2labs.android.spendview.core.dagger.module.FragmentModule;
import com.km2labs.android.spendview.core.mvp.MVPFragment;
import com.km2labs.spendview.android.R;
import com.km2labs.android.spendview.core.dagger.component.MainComponent;
import com.km2labs.android.spendview.database.content.Account;
import com.km2labs.android.spendview.expense.ExpenseListView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Subham Tyagi,
 * on 12/Jul/2015,
 * 10:38 PM
 * TODO:Add class comment.
 */
public class AccountDetailsFragment extends MVPFragment<AccountDetailPresenter> implements IAccountDetailView {

    @BindView(R.id.expense_list)
    ExpenseListView mExpenseListView;

    @BindView(R.id.accountSummaryChart)
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(12);
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(true);
        mChart.setDrawGridBackground(false);
        mChart.setDrawValueAboveBar(true);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.animateY(2500);
        mChart.getLegend().setEnabled(false);
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
        Map<Integer, Double> mapData = (Map<Integer, Double>) data;
        if (mapData.size() <= 0) {
            mChart.setVisibility(View.GONE);
            return;
        }

        ArrayList<String> xVals = new ArrayList<>(12);
        addMnth(xVals);

        ArrayList<BarEntry> yVals1 = new ArrayList<>(12);

        Set<Integer> keySet = mapData.keySet();
        for (Integer key : keySet) {
            BarEntry barEntry = new BarEntry((float) mapData.get(key).doubleValue(), key - 1);
            yVals1.add(barEntry);
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
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
        mainComponent.fragmentComponent(new FragmentModule()).inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.account_detail_view_fragment;
    }

    @Override
    public void showExpense(List<ExpenseListViewModel> listViewModels) {
        mExpenseListView.addData(listViewModels);
    }


    private void addMnth(List<String> strings) {
        strings.add("Jan");
        strings.add("Feb");
        strings.add("Mar");
        strings.add("Apr");
        strings.add("May");
        strings.add("Jun");
        strings.add("Jul");
        strings.add("Aug");
        strings.add("Sep");
        strings.add("Oct");
        strings.add("Nov");
        strings.add("Dec");
    }
}
