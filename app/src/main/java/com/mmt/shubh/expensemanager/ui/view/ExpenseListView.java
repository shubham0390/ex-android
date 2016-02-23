package com.mmt.shubh.expensemanager.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.mmt.shubh.expensemanager.ui.adapters.ExpenseListAdapter;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;
import com.mmt.shubh.recyclerviewlib.ListRecyclerView;

import java.util.List;

/**
 * Created by subhamtyagi on 2/20/16.
 */
public class ExpenseListView extends FrameLayout {

    ExpenseListAdapter mExpenseListAdapter;
    private ListRecyclerView.OnItemClickListener mItemClickListener = new ListRecyclerView.OnItemClickListener() {
        @Override
        public boolean onItemClick(RecyclerView parent, View view, int position, long id) {
            // TODO: 2/20/16 Open expense detail view from here
            return false;
        }
    };

    public ExpenseListView(Context context) {
        super(context);
        init(context);
    }

    public ExpenseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpenseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpenseListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        ListRecyclerView listRecyclerView = new ListRecyclerView(context);
        addView(listRecyclerView);
        mExpenseListAdapter = new ExpenseListAdapter();
        listRecyclerView.setAdapter(mExpenseListAdapter);
        listRecyclerView.setOnItemClickListener(mItemClickListener);
    }

    public void addData(List<ExpenseListViewModel> listViewModels) {
        mExpenseListAdapter.addData(listViewModels);
    }
}
