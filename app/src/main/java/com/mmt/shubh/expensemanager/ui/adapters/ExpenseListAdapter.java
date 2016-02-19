package com.mmt.shubh.expensemanager.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;
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
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_expense, parent, false);
        return new ViewHolder(view);
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

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(ExpenseListViewModel expenseListViewModel) {
        }
    }
}
