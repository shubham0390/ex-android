package com.km2labs.android.spendview.account;

import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.core.mvp.lce.MVPLCEView;

import java.util.List;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public interface IAccountDetailView extends MVPLCEView {

    void showExpense(List<ExpenseListViewModel> listViewModels);
}
