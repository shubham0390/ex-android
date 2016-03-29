package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.utils.DateUtil;
import com.mmt.shubh.recyclerviewlib.adapter.section.AbstractSectionIndexer;
import com.mmt.shubh.recyclerviewlib.adapter.section.BaseSection;

import org.joda.time.DateTime;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class ExpenseTimeSectionIndexer extends AbstractSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.mAmount = data.getExpenseAmount();
        long time = data.getExpenseDateInMill();
        DateTime dateTime = new DateTime(time);
        String mnth = DateUtil.getMonthName(dateTime.monthOfYear().get());
        int year = dateTime.year().get();
        section.mSectionName = mnth + ", " + year;
        return section;
    }
}
