package com.km2labs.android.spendview.expensebook.detail;

import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

import java.util.List;
import java.util.Map;


public interface ExpenseBookDetailView extends MVPLCEView<List<ExpenseListViewModel>> {
    void setGraphData(Map<Integer, Double> mapData);
}
