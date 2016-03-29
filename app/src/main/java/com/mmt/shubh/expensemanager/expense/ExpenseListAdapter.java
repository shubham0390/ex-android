package com.mmt.shubh.expensemanager.expense;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mmt.shubh.recyclerviewlib.adapter.section.AbstractSectionIndexer;
import com.mmt.shubh.recyclerviewlib.adapter.section.SectionAdapter;
import com.mmt.shubh.recyclerviewlib.adapter.section.SectionViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 10/Nov/2015,
 * 9:18 AM
 * TODO:Add class comment.
 */
public class ExpenseListAdapter extends SectionAdapter<ExpenseListViewModel, ExpenseListAdapter.ViewHolder> {

    private List<ExpenseListViewModel> mExpenseListViewModels;

    private int mMode;

    public ExpenseListAdapter(RecyclerView recyclerView, int mode) {
        super(recyclerView, getSectionIndexer(mode));
        mExpenseListViewModels = new ArrayList<>();
        mMode = mode;
    }

    private static AbstractSectionIndexer<ExpenseListViewModel> getSectionIndexer(int mode) {
        switch (mode) {
            case ExpenseListView.MODE_ACCOUNT:
                return new ExpenseBookSectionIndexer();
            case ExpenseListView.MODE_EXPENSE_BOOK:
            case ExpenseListView.MODE_MEMBER:
            case ExpenseListView.MODE_SUMMARY:
                return new ExpenseTimeSectionIndexer();
        }
        return null;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        holder.bindView(mExpenseListViewModels.get(position));
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ListItemExpense(parent.getContext()));
    }


    @Override
    public int getItemCount() {
        return mExpenseListViewModels.size();
    }

    public void addData(List<ExpenseListViewModel> expenseListViewModels) {
        mExpenseListViewModels.addAll(expenseListViewModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends SectionViewHolder {
        ListItemExpense mListItemExpense;

        public ViewHolder(ListItemExpense itemView) {
            super(itemView);
            this.mListItemExpense = itemView;
        }

        public void bindView(ExpenseListViewModel expenseListViewModel) {
            mListItemExpense.setExpense(expenseListViewModel);
        }
    }
}
