package com.km2labs.android.spendview.expense.adapters;

import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSection;
import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSectionIndexer;
import com.km2labs.android.spendview.utils.DateUtil;

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
