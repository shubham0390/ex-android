package com.mmt.shubh.expensemanager.expense.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.expense.ExpenseListView;
import com.mmt.shubh.expensemanager.expense.ExpenseListViewModel;
import com.mmt.shubh.expensemanager.expense.ListItemExpense;
import com.mmt.shubh.recyclerviewlib.adapter.section.AbstractSectionIndexer;
import com.mmt.shubh.recyclerviewlib.adapter.section.BaseSection;
import com.mmt.shubh.recyclerviewlib.adapter.section.SectionAdapter;
import com.mmt.shubh.recyclerviewlib.adapter.section.SectionViewHolder;

import java.util.List;


public class ExpenseListAdapter extends SectionAdapter<ExpenseListViewModel, ExpenseListAdapter.ViewHolder> {

    private int mMode;

    public ExpenseListAdapter(RecyclerView recyclerView, int mode) {
        super(recyclerView, getSectionIndexer(mode));
        mMode = mode;
    }

    private static AbstractSectionIndexer<ExpenseListViewModel> getSectionIndexer(int mode) {

        switch (mode) {
            case ExpenseListView.MODE_ACCOUNT:
                return new AccountSectionIndexer();
            case ExpenseListView.MODE_EXPENSE_BOOK:
                return new ExpenseBookSectionIndexer();
            case ExpenseListView.MODE_MEMBER:
            case ExpenseListView.MODE_SUMMARY:
                return new ExpenseTimeSectionIndexer();
        }
        return null;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        holder.bindView(mDataList.get(position));
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ListItemExpense(parent.getContext()));
    }

    @Override
    protected SectionViewHolder onCreateSectionViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_expense_list, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    protected void onBindSectionViewHolder(SectionViewHolder holder, BaseSection baseSection) {
        holder.onBindSectionViewHolder(baseSection);
    }

    public void addData(List<ExpenseListViewModel> expenseListViewModels) {
        setData(expenseListViewModels);
    }

    public void setMode(int mode) {
        mMode = mode;
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
