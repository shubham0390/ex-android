package com.mmt.shubh.expensemanager.expensebook.detail;

import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.core.mvp.lce.MVPLCEView;

import java.util.List;
import java.util.Map;


public interface ExpenseBookDetailView extends MVPLCEView<List<ExpenseListViewModel>> {
    void setGraphData(Map<Integer, Double> mapData);
}
