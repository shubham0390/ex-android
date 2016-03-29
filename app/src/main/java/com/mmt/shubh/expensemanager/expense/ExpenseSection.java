package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.recyclerviewlib.adapter.section.BaseSection;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class ExpenseSection extends BaseSection {

    public double mAmount;

    @Override
    public void update(BaseSection section) {
        mAmount += ((ExpenseSection) section).mAmount;
    }
}
