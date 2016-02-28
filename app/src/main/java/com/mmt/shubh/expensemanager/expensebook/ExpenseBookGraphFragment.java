package com.mmt.shubh.expensemanager.expensebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mmt.shubh.expensemanager.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseBookGraphFragment extends Fragment {

    @Bind(R.id.chart1)
    BarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_book_graph, container, false);
        ButterKnife.bind(this, view);
        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(12);
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);

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

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 12 + 1; i++) {
            float mult = (200 + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            BarEntry barEntry = new BarEntry(val1, i);
            yVals1.add(barEntry);
        }

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < 12 + 1; i++) {
            xVals.add("jan" + i);
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setDrawValues(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setDrawValues(true);

        mChart.setData(data);
        return view;
    }

}
