package com.km2labs.android.spendview.expense.adapters;

import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSection;
import com.km2labs.android.spendview.expense.ExpenseListViewModel;
import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSectionIndexer;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class AccountSectionIndexer extends BaseSectionIndexer<ExpenseListViewModel> {

    @Override
    protected BaseSection<Double> getSectionForData(ExpenseListViewModel data) {
        ExpenseSection section = new ExpenseSection();
        section.mSectionName = data.getAccountName();
        section.update(data.getExpenseAmount());
        return section;
    }
}
