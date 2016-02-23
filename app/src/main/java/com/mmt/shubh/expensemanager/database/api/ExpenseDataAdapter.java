package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.ui.fragment.ExpenseFilter;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseDataAdapter extends DataAdapter<Expense> {
    List<ExpenseListViewModel> getExpenseByMemberId(long memberId);

    List<ExpenseListViewModel> getExpenseByExpenseBookId(long expenseBookId);

    List<ExpenseListViewModel> getExpenseByMemberId(ExpenseFilter filter);

    Observable<List<ExpenseListViewModel>> getExpenseByAccountId(long id);
}
