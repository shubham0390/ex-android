package com.mmt.shubh.expensemanager.ui.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.contract.CategoryContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.ui.adapters.base.CursorRecyclerAdapter;

import java.text.SimpleDateFormat;

/**
 * Created by styagi on 5/27/2015.
 */
public class ExpenseListAdapter extends CursorRecyclerAdapter<ExpenseListAdapter.ViewHolder> {

    private ListItemClickListener mListItemClickListener;

    public ExpenseListAdapter(Cursor c, ListItemClickListener itemClickListener) {
        super(c);
        mListItemClickListener = itemClickListener;
    }

    public interface ListItemClickListener {

        void onItemClickListener(Cursor cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expense, parent, false);
        return ViewHolder.newInstance(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final Cursor cursor) {
        holder.bind(cursor);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListItemClickListener.onItemClickListener(cursor);
            }
        });
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView expenseTitle;
        private final TextView expenseAmount;
        private final TextView memberName;
        private final TextView expenseDate;
        private final View parent;
        private final TextView categoryName;

        public static ViewHolder newInstance(View itemView) {
            return new ViewHolder(itemView);
        }

        private ViewHolder(View view) {
            super(view);
            parent = view;
            expenseTitle = (TextView) view.findViewById(R.id.expense_title);
            expenseAmount = (TextView) view.findViewById(R.id.expense_amount);
            memberName = (TextView) view.findViewById(R.id.member_name);
            expenseDate = (TextView) view.findViewById(R.id.expense_date);
            categoryName = (TextView) view.findViewById(R.id.category_name);
        }

        public void bind(Cursor cursor) {
            expenseTitle.setText(cursor.getString(cursor.getColumnIndex(ExpenseContract.EXPENSE_NAME)));
            expenseAmount.setText("Rs " + cursor.getInt(cursor.getColumnIndex(ExpenseContract.EXPENSE_AMOUNT)) + "");
            memberName.setText(cursor.getString(cursor.getColumnIndex(MemberContract.MEMBER_NAME)));
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            expenseDate.setText(dateFormat.format(cursor.getLong(cursor.getColumnIndex(ExpenseContract.EXPENSE_DATE))));
            categoryName.setText(cursor.getString(cursor.getColumnIndex(CategoryContract.CATEGORY_NAME)));
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
