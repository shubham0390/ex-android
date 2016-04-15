package com.mmt.shubh.expensemanager.expense.adapters;

import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.recyclerviewlib.adapter.section.AbstractSectionIndexer;
import com.mmt.shubh.recyclerviewlib.adapter.section.BaseSection;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class ExpenseBookSectionIndexer extends AbstractSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.mSectionName = data.getExpenseBookName();
        section.update(data.getExpenseAmount());
        return section;
    }
}
