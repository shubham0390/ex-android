package com.mmt.shubh.expensemanager.expense.adapters;

import com.mmt.shubh.core.recyclerview.adapter.section.BaseSection;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class ExpenseSection extends BaseSection<Double> {

    public double mAmount;

    @Override
    public void update(Double section) {
        mAmount += section;
    }
}
