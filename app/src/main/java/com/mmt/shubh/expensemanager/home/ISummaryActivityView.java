package com.mmt.shubh.expensemanager.home;

import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.mvp.MVPView;

import java.util.List;

/**
 * Created by subhamtyagi on 4/7/16.
 */
public interface ISummaryActivityView extends MVPView {

    void showExpenseList(List<ExpenseListViewModel> expenses);
}
