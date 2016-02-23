package com.mmt.shubh.expensemanager.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.ui.view.ListItemExpense;
import com.mmt.shubh.expensemanager.ui.viewmodel.ExpenseListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subham Tyagi,
 * on 10/Nov/2015,
 * 9:18 AM
 * TODO:Add class comment.
 */
public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ViewHolder> {

    List<ExpenseListViewModel> mExpenseListViewModels;

    public ExpenseListAdapter() {
        mExpenseListViewModels = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new ListItemExpense(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mExpenseListViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mExpenseListViewModels.size();
    }

    public void addData(List<ExpenseListViewModel> expenseListViewModels) {
        mExpenseListViewModels.addAll(expenseListViewModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
