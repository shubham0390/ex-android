package com.mmt.shubh.expensemanager.expense.adapters;

import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.utils.DateUtil;
import com.mmt.shubh.core.recyclerview.adapter.section.BaseSectionIndexer;
import com.mmt.shubh.core.recyclerview.adapter.section.BaseSection;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;


public class ExpenseTimeSectionIndexer extends BaseSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.update(data.getExpenseAmount());
        long time = data.getExpenseDateInMill();
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        String mnth = DateUtil.getMonthName(localDateTime.getMonthValue());
        int year = localDateTime.getYear();
        section.mSectionName = mnth + ", " + year;
        return section;
    }
}
