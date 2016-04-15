package com.mmt.shubh.expensemanager.expense;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.mvp.MVPFragment;

/**
 * Created by subhamtyagi on 4/9/16.
 */
public class ViewExpenseFragment extends MVPFragment<IDetailExpenseView, DetailExpenseFragmentPresenter> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_expense_detail;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
