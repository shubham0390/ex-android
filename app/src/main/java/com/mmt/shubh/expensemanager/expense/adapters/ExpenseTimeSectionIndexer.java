package com.mmt.shubh.expensemanager.expense.adapters;

import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.utils.DateUtil;
import com.mmt.shubh.recyclerviewlib.adapter.section.AbstractSectionIndexer;
import com.mmt.shubh.recyclerviewlib.adapter.section.BaseSection;

import org.joda.time.DateTime;


public class ExpenseTimeSectionIndexer extends AbstractSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.update(data.getExpenseAmount());
        long time = data.getExpenseDateInMill();
        DateTime dateTime = new DateTime(time);
        String mnth = DateUtil.getMonthName(dateTime.monthOfYear().get());
        int year = dateTime.year().get();
        section.mSectionName = mnth + ", " + year;
        return section;
    }
}
