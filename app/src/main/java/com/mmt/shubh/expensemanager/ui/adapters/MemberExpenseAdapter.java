package com.mmt.shubh.expensemanager.ui.adapters;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mmt.shubh.expensemanager.Constants;
import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;

/**
 * Created by Subham Tyagi,
 * on 24/Jun/2015,
 * 11:40 AM
 * TODO:Add class comment.
 */
public class MemberExpenseAdapter extends CursorRecyclerAdapter<MemberExpenseAdapter.MemberExpenseViewHolder> {

    private OnListItemClickListener mListItemClickListener;
    private long mMemberId;

    public MemberExpenseAdapter(Cursor c, OnListItemClickListener itemClickListener,long memberId) {
        super(c);
        mListItemClickListener = itemClickListener;
        mMemberId=memberId;
    }

    @Override
    public void onBindViewHolder(MemberExpenseViewHolder holder, final Cursor cursor) {
        holder.bindView(cursor);
        final long expenseBookId = cursor.getLong(cursor.getColumnIndex(ExpenseBookContract._ID));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListItemClickListener.onItemClick(expenseBookId);
            }
        });
    }

    @Override
    public MemberExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member_expensebook, parent, false);
        return new MemberExpenseViewHolder(view);
    }


    public static class MemberExpenseViewHolder extends RecyclerView.ViewHolder {

        private View parent;
        private FrameLayout mExpenseBarFrameLayout;

        public MemberExpenseViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            mExpenseBarFrameLayout = (FrameLayout) parent.findViewById(R.id.expense_graph_bar);
        }

        public void bindView(Cursor cursor) {
            long expeoseBookId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.EXPENSE_BOOK_KEY));
            //long memberId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.MEMBER_KEY));
            Bundle bundle =  new Bundle();
            bundle.putLong(Constants.EXTRA_EXPENSE_BOOK_ID,expeoseBookId);
            //bundle.putLong(Constants.EXTRA_MEMBER_ID,memberId);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    public interface OnListItemClickListener {
        void onItemClick(long expenseBookId);
    }
}
