package com.mmt.shubh.expensemanager.ui.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.ui.viewmodel.AccountListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
        notifyDataSetChanged();
    }

    public static class AccountListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.account_image_icon)
        CircleImageView mAccountImageIcon;
        @Bind(R.id.account_name)
        AppCompatTextView mAccountName;
        @Bind(R.id.account_number)
        AppCompatTextView mAccountNumber;
        @Bind(R.id.account_balance_inner)
        AppCompatTextView mAccountBalanceInner;

        public AccountListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView(AccountListViewModel accountListViewModel) {

            mAccountName.setText(accountListViewModel.getAccountName());
            // mAccountBalance.setText(StringsUtils.getLocalisedAmountString(accountListViewModel.getAccountBalance()));
            mAccountNumber.setText(accountListViewModel.getAccountNumber());
        }
    }
}
