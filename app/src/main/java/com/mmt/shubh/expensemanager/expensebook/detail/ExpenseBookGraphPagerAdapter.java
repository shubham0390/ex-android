package com.mmt.shubh.expensemanager.expensebook.detail;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.expense.graph.ExpenseBarGraphView;

public class ExpenseBookGraphPagerAdapter extends PagerAdapter {

    private static final int PAGE_COUNT = 3;


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        switch (position) {
            case 0:
                break;
            case 1:
                view = new ExpenseBarGraphView(container.getContext());
                break;
            case 2:
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }
}
