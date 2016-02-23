package com.mmt.shubh.expensemanager.ui.views;

import com.mmt.shubh.expensemanager.ui.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;

import java.util.List;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public interface IAccountDetailView extends MVPLCEView {

    void showExpense(List<ExpenseListViewModel> listViewModels);
}
