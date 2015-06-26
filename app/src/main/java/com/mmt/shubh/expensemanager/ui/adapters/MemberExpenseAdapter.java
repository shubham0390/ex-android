package com.mmt.shubh.expensemanager.ui.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.R;

/**
 * Created by Subham Tyagi,
 * on 24/Jun/2015,
 * 11:40 AM
 * TODO:Add class comment.
 */
public class MemberExpenseAdapter extends CursorRecyclerAdapter<MemberExpenseAdapter.MemberExpenseViewHolder> {
    public MemberExpenseAdapter(Cursor c) {
        super(c);
    }

    @Override
    public void onBindViewHolder(MemberExpenseViewHolder holder, final Cursor cursor) {


    }

    @Override
    public MemberExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member_expensebook, parent, false);
        return new MemberExpenseViewHolder(view);
    }


    public static class MemberExpenseViewHolder extends RecyclerView.ViewHolder {

        private View parent;

        public MemberExpenseViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
        }

        public void bindView(Cursor cursor) {

        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    public interface OnListItemClickListener {
        void onItemClick(Cursor cursor);
    }
}
