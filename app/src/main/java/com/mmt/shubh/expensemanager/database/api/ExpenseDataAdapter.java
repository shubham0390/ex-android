package com.mmt.shubh.expensemanager.database.api;

import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.expense.ExpenseFilter;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;

import java.util.List;

import rx.Observable;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseDataAdapter extends IDataAdapter<Expense> {
    Observable<List<ExpenseListViewModel>> getExpenseByMemberId(long memberId);

    Observable<List<ExpenseListViewModel>> getExpenseByExpenseBookId(long expenseBookId);

    Observable<List<ExpenseListViewModel>> getExpenseByMemberId(ExpenseFilter filter);

    Observable<List<ExpenseListViewModel>> getExpenseByAccountId(long id);

    Observable<List<ExpenseListViewModel>> getAllSharedAmount(long id, long id2);
}
