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

package com.km2labs.android.spendview.expense.graph;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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
import com.km2labs.android.spendview.utils.MyYAxisValueFormatter;
import com.km2labs.spendview.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static butterknife.ButterKnife.findById;

public class ExpenseBarGraphView extends LinearLayout {

    BarChart mBarChart;

    public ExpenseBarGraphView(Context context) {
        super(context);
        init(context);
    }

    public ExpenseBarGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpenseBarGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ExpenseBarGraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.expense_bar_graph_date, this, true);
        if (isInEditMode()) {
            return;
        }
        mBarChart = findById(this, R.id.expense_bar_graph);
        mBarChart.setDescription("");
        mBarChart.setMaxVisibleValueCount(12);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawBarShadow(true);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawValueAboveBar(true);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.animateY(2500);
        mBarChart.getLegend().setEnabled(false);
    }

    public void setData(Map<Integer, Double> mapData) {
        if (mapData.size() <= 0) {
            mBarChart.setVisibility(View.GONE);
            return;
        }

        ArrayList<String> xVals = new ArrayList<>(12);
        addMonth(xVals);

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

        mBarChart.setData(barData);
    }

    private void addMonth(List<String> strings) {
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
