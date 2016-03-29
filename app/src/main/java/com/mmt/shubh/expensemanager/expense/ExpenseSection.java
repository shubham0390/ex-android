package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.recyclerviewlib.adapter.section.BaseSection;

/**
 * Created by subhamtyagi on 3/21/16.
 */
public class ExpenseSection extends BaseSection {

    double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
