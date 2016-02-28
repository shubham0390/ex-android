package com.mmt.shubh.expensemanager.account;

import com.mmt.shubh.expensemanager.mvp.lce.MVPLCEView;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;

import java.util.List;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public interface IAccountDetailView extends MVPLCEView {

    void showExpense(List<ExpenseListViewModel> listViewModels);
}
