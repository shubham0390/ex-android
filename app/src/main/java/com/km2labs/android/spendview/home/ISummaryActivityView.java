package com.km2labs.android.spendview.home;

import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.core.mvp.MVPView;

import java.util.List;

/**
 * Created by subhamtyagi on 4/7/16.
 */
public interface ISummaryActivityView extends MVPView {

    void showExpenseList(List<ExpenseListViewModel> expenses);
}
