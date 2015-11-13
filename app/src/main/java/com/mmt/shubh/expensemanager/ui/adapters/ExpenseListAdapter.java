package com.mmt.shubh.expensemanager.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.databinding.ListItemExpenseBinding;
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
        final ListItemExpenseBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_expense,
                parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mExpenseListViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mExpenseListViewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemExpenseBinding mBinding;

        public ViewHolder(View itemView, ListItemExpenseBinding binding) {
            super(itemView);
            mBinding = binding;
        }

        public void bindView(ExpenseListViewModel expenseListViewModel) {
            mBinding.setExpense(expenseListViewModel);
        }
    }

    public void addData(List<ExpenseListViewModel> expenseListViewModels) {
        mExpenseListViewModels.addAll(expenseListViewModels);
        notifyDataSetChanged();
    }
}
