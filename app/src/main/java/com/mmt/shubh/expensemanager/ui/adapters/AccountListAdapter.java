package com.mmt.shubh.expensemanager.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.viewmodel.AccountListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 1:15 PM
 * TODO:Add class comment.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountListViewHolder> {


    private List<AccountListViewModel> mAccountListViewModels;

    public AccountListAdapter() {
        mAccountListViewModels = new ArrayList<>();
    }

    @Override
    public AccountListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_account, parent, false);
        return new AccountListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountListViewHolder holder, int position) {
        holder.bindView(mAccountListViewModels.get(position));
    }


    @Override
    public int getItemCount() {
        return mAccountListViewModels.size();
    }

    public void setData(List<AccountListViewModel> data) {
        mAccountListViewModels.addAll(data);
    }

    public static class AccountListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.account_type)
        TextView mAccountType;
        @Bind(R.id.account_balance)
        TextView mAccountBalance;
        @Bind(R.id.account_name)
        TextView mBankName;
        @Bind(R.id.account_number)
        TextView mAccountNumber;

        public AccountListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView(AccountListViewModel accountListViewModel) {
            mBankName.setText(accountListViewModel.getAccountName());
            // mAccountBalance.setText(StringsUtils.getLocalisedAmountString(accountListViewModel.getAccountBalance()));
            mAccountNumber.setText(accountListViewModel.getAccountNumber());
        }
    }
}
