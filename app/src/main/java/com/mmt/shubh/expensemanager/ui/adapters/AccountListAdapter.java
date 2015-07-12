package com.mmt.shubh.expensemanager.ui.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.ui.adapters.base.CursorRecyclerAdapter;

/**
 * Created by Subham Tyagi,
 * on 10/Jul/2015,
 * 1:15 PM
 * TODO:Add class comment.
 */
public class AccountListAdapter extends CursorRecyclerAdapter<AccountListAdapter.AccountListViewHolder> {


    public AccountListAdapter(Cursor c) {
        super(c);
    }

    @Override
    public AccountListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_account, parent, false);
        return new AccountListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountListViewHolder holder, Cursor cursor) {
        holder.bindView(cursor);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class AccountListViewHolder extends RecyclerView.ViewHolder {
        private TextView mAccountType;
        private TextView mAccountBalance;
        private TextView mBankName;
        private TextView mAccountNumber;
        private CheckBox mShowCardCheckbox;
        private LinearLayout mCardContainer;

        public AccountListViewHolder(View itemView) {
            super(itemView);
            mAccountType = (TextView) itemView.findViewById(R.id.account_type);
            mAccountBalance = (TextView) itemView.findViewById(R.id.account_balance);
            mBankName = (TextView) itemView.findViewById(R.id.account_name);
            mAccountNumber = (TextView) itemView.findViewById(R.id.account_number);
            mShowCardCheckbox = (CheckBox) itemView.findViewById(R.id.show_card_checkbox);
            mCardContainer = (LinearLayout) itemView.findViewById(R.id.card_container);
           /* mShowCardCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        mCardContainer.setVisibility(View.VISIBLE);
                    } else {
                        mCardContainer.setVisibility(View.GONE);
                    }
                }
            });*/
        }

        public void bindView(Cursor cursor) {
            mBankName.setText(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NAME)));
            mAccountType.setText(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_TYPE)));
            mAccountBalance.setText(cursor.getInt(cursor.getColumnIndex(AccountContract.ACCOUNT_BALANCE))+"");
            mAccountNumber.setText(cursor.getString(cursor.getColumnIndex(AccountContract.ACCOUNT_NUMBER)));
        }
    }
}
