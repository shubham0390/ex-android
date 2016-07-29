package com.km2labs.android.spendview.expense.adapters;

import com.km2labs.android.spendview.core.recyclerview.adapter.section.BaseSection;

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
